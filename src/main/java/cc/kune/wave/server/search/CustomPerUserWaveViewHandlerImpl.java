/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.WaveEntityManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.domain.User;
import cc.kune.domain.WaveEntity;
import cc.kune.wave.server.kspecific.ParticipantUtils;

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

  private ParticipantUtils participantUtils;
  private UserManager userManager;
  private WaveEntityManager waveEntityManager;

  private final ReadableWaveletDataProvider waveletProvider;

  @Inject
  public CustomPerUserWaveViewHandlerImpl(final ReadableWaveletDataProvider waveletProvider) {
    this.waveletProvider = waveletProvider;
  }

  @KuneTransactional
  private void addWaveToUser(final WaveEntity waveEntity, final ParticipantId participantId) {
    Preconditions.checkNotNull(waveEntity);
    LOG.debug("Added wave to user " + participantId.getAddress());
    try {
      final User user = userManager.findByShortname(getShortname(participantId.getAddress()));
      if (user != null) {
        userManager.addWave(user, waveEntity);
      } else {
        warnUnknowUser(participantId);
      }
    } catch (final javax.persistence.NoResultException e) {
      LOG.warn("Failed to retrieve user " + participantId.getAddress(), e);
    }
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub
  }

  private String getShortname(final String address) {
    return participantUtils.getAddressName(address);
  }

  @KuneTransactional
  private WaveEntity getWaveEntity(final ReadableWaveletData waveletData) {
    WaveEntity waveEntity;
    final String waveId = waveletData.getWaveId().serialise();
    final String waveletId = waveletData.getWaveletId().serialise();
    final String domain = waveletData.getWaveId().getDomain();
    try {
      waveEntity = waveEntityManager.find(domain, waveId, waveletId);
    } catch (final javax.persistence.NoResultException e) {
      waveEntity = waveEntityManager.add(domain, waveId, waveletId, waveletData.getLastModifiedTime());
    }
    Preconditions.checkNotNull(waveEntity);
    return waveEntity;
  }

  public void init(final UserManager userManager, final WaveEntityManager waveEntityManager,
      final ParticipantUtils participantUtils) {
    Preconditions.checkNotNull(userManager);
    Preconditions.checkNotNull(waveEntityManager);
    Preconditions.checkNotNull(participantUtils);
    this.userManager = userManager;
    this.waveEntityManager = waveEntityManager;
    this.participantUtils = participantUtils;
  }

  @Override
  public ListenableFuture<Void> onParticipantAdded(final WaveletName waveletName,
      final ParticipantId participantId) {
    Preconditions.checkNotNull(waveletName);
    Preconditions.checkNotNull(participantId);

    @SuppressWarnings("deprecation")
    final ListenableFutureTask<Void> task = new ListenableFutureTask<Void>(new Callable<Void>() {

      @Override
      public Void call() throws Exception {
        ReadableWaveletData waveletData;
        try {
          waveletData = waveletProvider.getReadableWaveletData(waveletName);
          final WaveEntity waveEntity = getWaveEntity(waveletData);
          addWaveToUser(waveEntity, participantId);
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
    @SuppressWarnings("deprecation")
    final ListenableFutureTask<Void> task = new ListenableFutureTask<Void>(new Callable<Void>() {

      @Override
      public Void call() throws Exception {
        ReadableWaveletData waveletData;
        try {
          waveletData = waveletProvider.getReadableWaveletData(waveletName);
          final WaveEntity waveEntity = getWaveEntity(waveletData);
          removeWaveToUser(waveEntity, participantId);
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
  public ListenableFuture<Void> onWaveInit(final WaveletName waveletName) {
    Preconditions.checkNotNull(waveletName);
    LOG.debug("On wave init of wave " + waveletName.toString());
    ReadableWaveletData waveletData;
    try {
      waveletData = waveletProvider.getReadableWaveletData(waveletName);
      final WaveEntity waveEntity = getWaveEntity(waveletData);
      Preconditions.checkNotNull(waveEntity);
      for (final ParticipantId participantId : waveletData.getParticipants()) {
        addWaveToUser(waveEntity, participantId);
      }
    } catch (final WaveServerException e) {
      LOG.error("Failed to initialize index for " + waveletName, e);
    }
    @SuppressWarnings("deprecation")
    final ListenableFutureTask<Void> task = new ListenableFutureTask<Void>(new Callable<Void>() {
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
    LOG.debug("Remove wave to user " + participantId.getAddress());
    try {
      final User user = userManager.findByShortname(getShortname(participantId.getAddress()));
      if (user != null) {
        userManager.removeWave(user, waveEntity);
      } else {
        warnUnknowUser(participantId);
      }
    } catch (final javax.persistence.NoResultException e) {
      LOG.warn("Failed to retrieve user " + participantId.getAddress(), e);
    }
  }

  @Override
  @KuneTransactional
  public Multimap<WaveId, WaveletId> retrievePerUserWaveView(final ParticipantId participantId) {
    LOG.debug("Retrive waves view of user " + participantId.getAddress());
    final Multimap<WaveId, WaveletId> userWavesViewMap = HashMultimap.create();
    try {
      final User user = userManager.findByShortname(getShortname(participantId.getAddress()));
      if (user != null) {
        for (final WaveEntity wave : user.getWaves()) {
          userWavesViewMap.put(WaveId.deserialise(wave.getWaveId()),
              WaveletId.deserialise(wave.getWaveletId()));
        }
      } else {
        warnUnknowUser(participantId);
      }
    } catch (final javax.persistence.NoResultException e) {
      LOG.error("Failed to retrieve user " + participantId.getAddress(), e);
    }
    return userWavesViewMap;
  }

  private void warnUnknowUser(final ParticipantId participantId) {
    LOG.warn("Unknown user " + participantId.getAddress());
  }

}
