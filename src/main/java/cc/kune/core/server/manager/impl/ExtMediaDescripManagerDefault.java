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

import java.util.List;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.ExtMediaDescripManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.domain.ExtMediaDescrip;
import cc.kune.domain.finders.ExtMediaDescripFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtMediaDescripManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ExtMediaDescripManagerDefault extends DefaultManager<ExtMediaDescrip, Long> implements
    ExtMediaDescripManager {

  /** The ext media finder. */
  private final ExtMediaDescripFinder extMediaFinder;

  /**
   * Instantiates a new ext media descrip manager default.
   * 
   * @param provider
   *          the provider
   * @param extMediaFinder
   *          the ext media finder
   */
  @Inject
  public ExtMediaDescripManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final ExtMediaDescripFinder extMediaFinder) {
    super(provider, ExtMediaDescrip.class);
    this.extMediaFinder = extMediaFinder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.ExtMediaDescripManager#getAll()
   */
  @Override
  public List<ExtMediaDescrip> getAll() {
    return extMediaFinder.getAll();
  }
}
