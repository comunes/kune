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

import java.util.Date;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
/**
 * The Class UserSessionManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class UserSessionManagerImpl implements UsersOnline, UserSessionManager {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(UserSessionManagerImpl.class);

  /** The presence manager. */
  private final XmppRosterPresenceProvider presenceManager;

  private final Provider<HttpServletRequest> requestProvider;

  /** The manager. */
  private final UserManager userManager;

  private final org.waveprotocol.box.server.authentication.SessionManager waveSessionManager;

  /**
   * Instantiates a new user session manager.
   * 
   * @param userManager
   *          the manager
   * @param userSessionProv
   *          the user session prov
   * @param jettySessionManager
   *          the jetty session manager
   * @param userSessionMonitor
   *          the user session monitor
   * @param presence
   *          the presence
   */
  @Inject
  public UserSessionManagerImpl(final UserManager userManager,
      final org.waveprotocol.box.server.authentication.SessionManager waveSessionManager,
      final SessionManager jettySessionManager, final UserSessionMonitor userSessionMonitor,
      final XmppRosterPresenceProvider presence, final Provider<HttpServletRequest> requestProvider) {
    this.userManager = userManager;
    this.waveSessionManager = waveSessionManager;
    this.presenceManager = presence;
    this.requestProvider = requestProvider;
    final HashSessionManager hSessionManager = (HashSessionManager) jettySessionManager;
    hSessionManager.setMaxInactiveInterval(-1);
    hSessionManager.setSavePeriod(5);
    hSessionManager.addEventListener(userSessionMonitor);
    LOG.debug(String.format("User sessions: %d", hSessionManager.getSessions()));
    LOG.debug(String.format("User sessions total: %d", hSessionManager.getSessionsTotal()));
    // this prevent saving the session??
    // hSessionManager.setUsingCookies(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#getHashFromSession()
   */
  @Override
  public String getHash() {
    final HttpSession session = getSession();
    return session == null ? null : (String) session.getAttribute(USER_HASH);
  }

  private HttpSession getSession() {
    return requestProvider.get().getSession();
  }

  @SuppressWarnings("unused")
  // Right now this is only used in websocket connect (can be cross-domain) to
  // get the session and in the InitData generation to get the websocket info
  private HttpSession getSessionFromHash(final String userHash) {
    HttpSession session = null;
    if (userHash != null) {
      try {
        session = waveSessionManager.getSessionFromToken(userHash);
      } catch (final NullPointerException e) {
        // If the hash don't exist jetty throws a NPE
      }
    }
    return session;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#getUser()
   */
  @Override
  public User getUser() {
    final HttpSession session = getSession();
    if (session != null) {
      final Object attribute = session.getAttribute(USER_ID);
      return userManager.find((Long) attribute);
    } else {
      return User.UNKNOWN_USER;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.notifier.UsersOnline#isOnline(java.lang.String)
   */
  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#isOnline(java.lang.String)
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#isUserLoggedIn()
   */
  @Override
  public boolean isUserLoggedIn() {
    final HttpSession session = getSession();
    if (session == null) {
      return false;
    } else {
      return (session.getAttribute(USER_ID) != null);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.UserSessionManager#isUserNotLoggedIn(java.lang.String)
   */
  @Override
  public boolean isUserNotLoggedIn() {
    return !isUserLoggedIn();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#login(java.lang.Long,
   * java.lang.String)
   */
  @Override
  public void login(final Long userId, final String newUserHash) {
    final HttpSession session = getSession();
    session.setAttribute(USER_ID, userId);
    session.setAttribute(USER_HASH, newUserHash);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.UserSessionManager#logout()
   */
  @Override
  public void logout() {
    final HttpSession session = getSession();
    session.setAttribute(USER_ID, null);
    session.setAttribute(USER_HASH, null);
    waveSessionManager.logout(session);
  }
}
