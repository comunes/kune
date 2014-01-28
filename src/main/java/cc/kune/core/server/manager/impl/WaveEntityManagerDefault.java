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
package cc.kune.core.server.manager.impl;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.WaveEntityManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.domain.ParticipantEntity;
import cc.kune.domain.WaveEntity;
import cc.kune.domain.WaveRefKey;
import cc.kune.domain.finders.WaveEntityFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
// @LogThis
public class WaveEntityManagerDefault extends DefaultManager<WaveEntity, WaveRefKey> implements
    WaveEntityManager {

  private final WaveEntityFinder finder;

  @Inject
  public WaveEntityManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final WaveEntityFinder finder) {
    super(provider, WaveEntity.class);
    this.finder = finder;
  }

  @Override
  @KuneTransactional
  public WaveEntity add(final String domain, final String waveId, final String waveletId,
      final Long lastModifiedTime, final ParticipantEntity creator, final Long creationTime) {
    final WaveEntity wave = new WaveEntity(domain, waveId, waveletId, lastModifiedTime, creator,
        creationTime);
    persist(wave, WaveEntity.class);
    return wave;
  }

  @Override
  @KuneTransactional
  public void add(final WaveEntity wave, final ParticipantEntity participant) {
    wave.add(participant);
    merge(wave);
  }

  @Override
  @KuneTransactional
  public WaveEntity find(final String domain, final String waveId, final String waveletId) {
    return finder.find(domain, waveId, waveletId);
  }

  @Override
  @KuneTransactional
  public void remove(final WaveEntity wave, final ParticipantEntity participant) {
    wave.remove(participant);
    merge(wave);
  }

  @Override
  @KuneTransactional
  public void setLastModifiedTime(final WaveEntity wave, final long lastModifiedTime) {
    wave.setLastModifiedTime(lastModifiedTime);
    merge(wave);
  }

}
