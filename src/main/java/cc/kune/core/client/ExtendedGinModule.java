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
package cc.kune.core.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtendedGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class ExtendedGinModule extends AbstractPresenterModule {

  /**
   * Eagle.
   * 
   * @param type
   *          the type
   */
  protected void eagle(final Class<?> type) {
    bind(type).asEagerSingleton();
  }

  /**
   * S.
   * 
   * @param type
   *          the type
   */
  protected void s(final Class<?> type) {
    bind(type).in(Singleton.class);
  }

  /**
   * S.
   * 
   * @param <V>
   *          the value type
   * @param <W>
   *          the generic type
   * @param type
   *          the type
   * @param typeImpl
   *          the type impl
   */
  protected <V, W> void s(final Class<V> type, final Class<? extends V> typeImpl) {
    bind(type).to(typeImpl).in(Singleton.class);
  }

}
