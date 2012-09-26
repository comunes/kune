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

package cc.kune.core.server.manager.impl;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.WaveEntityManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.domain.WaveEntity;
import cc.kune.domain.WaveRefKey;
import cc.kune.domain.finders.WaveEntityFinder;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class WaveEntityManagerDefault extends DefaultManager<WaveEntity, WaveRefKey> implements
    WaveEntityManager {

  private final WaveEntityFinder finder;
  private final Provider<EntityManager> provider2;

  @Inject
  public WaveEntityManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final WaveEntityFinder finder) {
    super(provider, WaveEntity.class);
    provider2 = provider;
    this.finder = finder;
  }

  @Override
  public WaveEntity add(final String domain, final String waveId, final String waveletId,

  final Long lastModifiedTime) {
    provider2.get().getTransaction().begin();
    final WaveEntity wave = new WaveEntity(domain, waveId, waveletId, lastModifiedTime);
    persist(wave, WaveEntity.class);
    provider2.get().getTransaction().commit();
    Preconditions.checkState(finder.count() > 0);

    return wave;
  }

}