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
package cc.kune.core.server;

import java.util.Date;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.HashSessionManager;

import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.notifier.UsersOnline;
import cc.kune.core.server.xmpp.XmppRosterPresenceProvider;
import cc.kune.core.shared.SessionConstants;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// @LogThis
@Singleton
public class UserSessionManager implements UsersOnline {

  public static final Log LOG = LogFactory.getLog(UserSessionManager.class);

  private final UserManager manager;
  private final XmppRosterPresenceProvider presenceManager;
  private final Provider<UserSession> userSessionProv;

  @Inject
  public UserSessionManager(final UserManager manager, final Provider<UserSession> userSessionProv,
      final SessionManager jettySessionManager, final UserSessionMonitor userSessionMonitor,
      final XmppRosterPresenceProvider presence) {
    this.manager = manager;
    this.userSessionProv = userSessionProv;
    this.presenceManager = presence;
    final HashSessionManager hSessionManager = (HashSessionManager) jettySessionManager;
    hSessionManager.setMaxInactiveInterval(-1);
    hSessionManager.setSavePeriod(5);
    hSessionManager.addEventListener(userSessionMonitor);
    LOG.debug(String.format("User sessions: %d", hSessionManager.getSessions()));
    LOG.debug(String.format("User sessions total: %d", hSessionManager.getSessionsTotal()));
    // this prevent saving the session??
    // hSessionManager.setUsingCookies(true);
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

  @Override
  public boolean isOnline(final String shortname) {
    try {
      final long diffToLastConnection = (new Date()).getTime()
          - presenceManager.getLastConnected(shortname);
      return diffToLastConnection < SessionConstants._2_HOURS;
    } catch (final NoResultException e) {
      return false;
    }
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
