/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.wave.server.search;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewHandler;
import org.waveprotocol.box.server.waveserver.ReadableWaveletDataProvider;
import org.waveprotocol.box.server.waveserver.WaveServerException;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.wave.data.ReadableWaveletData;

import cc.kune.core.server.manager.ParticipantEntityManager;
import cc.kune.core.server.manager.WaveEntityManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.domain.ParticipantEntity;
import cc.kune.domain.WaveEntity;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomPerUserWaveViewHandlerImpl implements PerUserWaveViewHandler, Closeable {
  // TODO Inject executor.
  private static final Executor executor = Executors.newSingleThreadExecutor();

  public static final Log LOG = LogFactory.getLog(CustomPerUserWaveViewHandlerImpl.class);

  @Inject
  private ParticipantEntityManager participantEntityManager;
  @Inject
  private WaveEntityManager waveEntityManager;
  @Inject
  private ReadableWaveletDataProvider waveletProvider;

  @KuneTransactional
  private void addWaveToUser(final WaveEntity waveEntity, final ParticipantId participantId) {
    Preconditions.checkNotNull(waveEntity);
    LOG.debug("Added wave to participant " + participantId.getAddress());
    final ParticipantEntity participant = participantEntityManager.createIfNotExist(participantId.getAddress());
    waveEntityManager.add(waveEntity, participant);
  }

  @Override
  public void close() throws IOException {
    // Probably nothing to do here...
  }

  private WaveEntity getWaveEntity(final ReadableWaveletData waveletData) {
    WaveEntity waveEntity;
    final String waveId = waveletData.getWaveId().serialise();
    final String waveletId = waveletData.getWaveletId().serialise();
    final String domain = waveletData.getWaveId().getDomain();
    final ParticipantId creator = waveletData.getCreator();
    final Long creationTime = waveletData.getCreationTime();
    try {
      waveEntity = waveEntityManager.find(domain, waveId, waveletId);
    } catch (final javax.persistence.NoResultException e) {
      waveEntity = waveEntityManager.add(domain, waveId, waveletId, waveletData.getLastModifiedTime(),
          participantEntityManager.createIfNotExist(creator.getAddress()), creationTime);
    }
    Preconditions.checkNotNull(waveEntity);
    return waveEntity;
  }

  private void logNotFound(final ParticipantId participantId) {
    LOG.info("Failed to find and retrieve participant " + participantId.getAddress());
  }

  @Override
  public ListenableFuture<Void> onParticipantAdded(final WaveletName waveletName,
      final ParticipantId participantId) {
    Preconditions.checkNotNull(waveletName);
    Preconditions.checkNotNull(participantId);

    final ListenableFutureTask<Void> task = ListenableFutureTask.<Void> create(new Callable<Void>() {

      @Override
      @KuneTransactional
      public Void call() throws Exception {
        ReadableWaveletData waveletData;
        try {
          waveletData = waveletProvider.getReadableWaveletData(waveletName);
          final WaveEntity waveEntity = getWaveEntity(waveletData);
          addWaveToUser(waveEntity, participantId);
          updateWaveEntity(waveEntity, waveletData);
        } catch (final WaveServerException e) {
          LOG.error("Failed to update index for " + waveletName, e);
          throw e;
        }
        return null;
      }
    });
    executor.execute(task);
    return task;
  }

  @Override
  public ListenableFuture<Void> onParticipantRemoved(final WaveletName waveletName,
      final ParticipantId participantId) {
    Preconditions.checkNotNull(waveletName);
    Preconditions.checkNotNull(participantId);
    final ListenableFutureTask<Void> task = ListenableFutureTask.<Void> create(new Callable<Void>() {

      @Override
      @KuneTransactional
      public Void call() throws Exception {
        ReadableWaveletData waveletData;
        try {
          waveletData = waveletProvider.getReadableWaveletData(waveletName);
          final WaveEntity waveEntity = getWaveEntity(waveletData);
          removeWaveToUser(waveEntity, participantId);
          updateWaveEntity(waveEntity, waveletData);
        } catch (final WaveServerException e) {
          LOG.error("Failed to update index for " + waveletName, e);
          throw e;
        }
        return null;
      }
    });
    executor.execute(task);
    return task;
  }

  @Override
  @KuneTransactional
  public ListenableFuture<Void> onWaveInit(final WaveletName waveletName) {
    Preconditions.checkNotNull(waveletName);
    LOG.debug("On wave init of wave " + waveletName.toString());
    try {
      final ReadableWaveletData waveletData = waveletProvider.getReadableWaveletData(waveletName);
      final WaveEntity waveEntity = getWaveEntity(waveletData);
      Preconditions.checkNotNull(waveEntity);
      for (final ParticipantId participantId : waveletData.getParticipants()) {
        addWaveToUser(waveEntity, participantId);
      }
    } catch (final WaveServerException e) {
      LOG.error("Failed to initialize index for " + waveletName, e);
    }
    final ListenableFutureTask<Void> task = ListenableFutureTask.<Void> create(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        return null;
      }
    });
    executor.execute(task);
    return task;
  }

  @KuneTransactional
  private void removeWaveToUser(final WaveEntity waveEntity, final ParticipantId participantId) {
    Preconditions.checkNotNull(waveEntity);
    LOG.debug("Remove wave to participant " + participantId.getAddress());
    final ParticipantEntity participant = participantEntityManager.createIfNotExist(participantId.getAddress());
    waveEntityManager.remove(waveEntity, participant);
  }

  @Override
  @KuneTransactional
  public Multimap<WaveId, WaveletId> retrievePerUserWaveView(final ParticipantId participantId) {
    final String address = participantId.getAddress();
    LOG.debug("Retrive waves view of user " + address);
    final Multimap<WaveId, WaveletId> userWavesViewMap = HashMultimap.create();
    try {
      final ParticipantEntity participantEntity = participantEntityManager.find(address);
      if (participantEntity != null) {
        for (final WaveEntity wave : participantEntity.getWaves()) {
          userWavesViewMap.put(WaveId.deserialise(wave.getWaveId()),
              WaveletId.deserialise(wave.getWaveletId()));
        }
      } else {
        throw new NoResultException();
      }
    } catch (final javax.persistence.NoResultException e) {
      logNotFound(participantId);
    }
    return userWavesViewMap;
  }

  private void updateWaveEntity(final WaveEntity waveEntity, final ReadableWaveletData waveletData) {
    waveEntityManager.setLastModifiedTime(waveEntity, waveletData.getLastModifiedTime());
  }

}
