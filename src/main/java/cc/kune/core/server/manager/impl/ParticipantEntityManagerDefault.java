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

import cc.kune.core.server.manager.ParticipantEntityManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.domain.ParticipantEntity;
import cc.kune.domain.finders.ParticipantEntityFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The Class SharedWavesManagerDefault.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ParticipantEntityManagerDefault extends DefaultManager<ParticipantEntity, Long> implements
    ParticipantEntityManager {

  private final ParticipantEntityFinder finder;

  @Inject
  public ParticipantEntityManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final ParticipantEntityFinder finder) {
    super(provider, ParticipantEntity.class);
    this.finder = finder;
  }

  @Override
  @KuneTransactional
  public ParticipantEntity createIfNotExist(final String address) {
    ParticipantEntity participant;
    try {
      participant = finder.findByAddress(address);

    } catch (final javax.persistence.NoResultException e) {
      participant = new ParticipantEntity(address);
      persist(participant);
    }

    return participant;
  }

  @Override
  @KuneTransactional
  public ParticipantEntity find(final String address) {
    return finder.findByAddress(address);
  }

}