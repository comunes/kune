/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server;

import cc.kune.core.server.manager.UserManager;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserSessionManager {

  private final UserManager manager;
  private final Provider<UserSession> userSessionProv;

  @Inject
  public UserSessionManager(final UserManager manager, final Provider<UserSession> userSessionProv) {
    this.manager = manager;
    this.userSessionProv = userSessionProv;
  }

  public String getHash() {
    return getUserSession().getHash();
  }

  public User getUser() {
    return manager.find(getUserSession().getUserId());
  }

  private UserSession getUserSession() {
    return userSessionProv.get();
  }

  public boolean isUserLoggedIn() {
    return getUserSession().getUserId() != null;
  }

  public boolean isUserNotLoggedIn() {
    return !isUserLoggedIn();
  }

  public void login(final Long userId, final String newUserHash) {
    getUserSession().setUserId(userId);
    getUserSession().setHash(newUserHash);
  }

  public void logout() {
    getUserSession().setUserId(null);
    getUserSession().setHash(null);
  }
}
