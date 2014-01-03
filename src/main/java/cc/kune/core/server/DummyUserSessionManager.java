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

package cc.kune.core.server;

import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.notifier.UsersOnline;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DummyUserSessionManager implements UserSessionManager, UsersOnline {

  private String userHash;
  private Long userId;
  private final UserManager userManager;

  @Inject
  public DummyUserSessionManager(final UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  public String getHash() {
    return userHash;
  }

  @Override
  public User getUser() {
    return userManager.find(userId);
  }

  @Override
  public boolean isOnline(final String shortname) {
    return false;
  }

  @Override
  public boolean isUserLoggedIn() {
    return userHash != null;
  }

  @Override
  public boolean isUserNotLoggedIn() {
    return !isUserLoggedIn();
  }

  @Override
  public void login(final Long userId, final String userHash) {
    this.userId = userId;
    this.userHash = userHash;
  }

  @Override
  public void logout() {
    userId = null;
    userHash = null;
  }

}
