/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

// TODO: Auto-generated Javadoc
// @LogThis
/**
 * The Class UserSessionManager.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class UserSessionManager implements UsersOnline {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(UserSessionManager.class);

  /** The manager. */
  private final UserManager manager;
  
  /** The presence manager. */
  private final XmppRosterPresenceProvider presenceManager;
  
  /** The user session prov. */
  private final Provider<UserSession> userSessionProv;

  /**
   * Instantiates a new user session manager.
   *
   * @param manager the manager
   * @param userSessionProv the user session prov
   * @param jettySessionManager the jetty session manager
   * @param userSessionMonitor the user session monitor
   * @param presence the presence
   */
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

  /**
   * Gets the hash.
   *
   * @return the hash
   */
  public String getHash() {
    return getUserSession().getHash();
  }

  /**
   * Gets the user.
   *
   * @return the user
   */
  public User getUser() {
    return manager.find(getUserSession().getUserId());
  }

  /**
   * Gets the user session.
   *
   * @return the user session
   */
  private UserSession getUserSession() {
    return userSessionProv.get();
  }

  /* (non-Javadoc)
   * @see cc.kune.core.server.notifier.UsersOnline#isOnline(java.lang.String)
   */
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

  /**
   * Checks if is user logged in.
   *
   * @return true, if is user logged in
   */
  public boolean isUserLoggedIn() {
    return getUserSession().getUserId() != null;
  }

  /**
   * Checks if is user not logged in.
   *
   * @return true, if is user not logged in
   */
  public boolean isUserNotLoggedIn() {
    return !isUserLoggedIn();
  }

  /**
   * Login.
   *
   * @param userId the user id
   * @param newUserHash the new user hash
   */
  public void login(final Long userId, final String newUserHash) {
    getUserSession().setUserId(userId);
    getUserSession().setHash(newUserHash);
  }

  /**
   * Logout.
   */
  public void logout() {
    getUserSession().setUserId(null);
    getUserSession().setHash(null);
  }
}
