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

import cc.kune.core.server.manager.UserSignInLogManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.domain.User;
import cc.kune.domain.UserSignInLog;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSignInLogManagerDefault manages the table of user signin
 * records.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class UserSignInLogManagerDefault extends DefaultManager<UserSignInLog, Long> implements
    UserSignInLogManager {

  /**
   * Instantiates a new user sign in log manager default.
   * 
   * @param provider
   *          the provider
   */
  @Inject
  public UserSignInLogManagerDefault(@DataSourceKune final Provider<EntityManager> provider) {
    super(provider, UserSignInLog.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.UserSignInLogManager#log(cc.kune.domain.User,
   * java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
   */
  @Override
  public void log(final User user, final String ipAddress, final String userAgent, final String hash) {
    final UserSignInLog logRegister = new UserSignInLog(user, ipAddress, userAgent, hash);
    persist(logRegister);
  }

}