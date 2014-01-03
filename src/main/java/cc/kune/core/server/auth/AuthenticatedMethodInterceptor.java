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
package cc.kune.core.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticatedMethodInterceptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AuthenticatedMethodInterceptor implements MethodInterceptor {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(AuthenticatedMethodInterceptor.class);

  /** The request provider. */
  @Inject
  Provider<HttpServletRequest> requestProvider;

  /** The user session manager. */
  @Inject
  UserSessionManager userSessionManager;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
   * .MethodInvocation)
   */
  @Override
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    try {
      final Object[] arguments = invocation.getArguments();
      // Some browsers getCookie returns "null" as String instead of null
      final String userHash = arguments[0] == null || arguments[0].equals("null") ? null
          : (String) arguments[0];

      final String method = invocation.getMethod().getName();
      logLine(method, userHash, true);
      LOG.info("Method: " + method);
      LOG.info("Userhash received: " + userHash);

      final Authenticated authAnnotation = invocation.getStaticPart().getAnnotation(Authenticated.class);
      final boolean mandatory = authAnnotation.mandatory();

      if (userHash == null && mandatory) {
        LOG.info("Not logged in server and mandatory");
        logLine(method, userHash, false);
        throw new UserMustBeLoggedException();
      } else if (userSessionManager.isUserNotLoggedIn() && mandatory) {
        LOG.info("Session expired (not logged in server and mandatory)");
        logLine(method, userHash, false);
        throw new SessionExpiredException();
      } else if (userHash == null && !userSessionManager.isUserLoggedIn()) {
        // Ok, do nothing
      } else if (userHash != null && userSessionManager.isUserNotLoggedIn()) {
        LOG.info("Session expired (not logged in server)");
        logLine(method, userHash, false);
        throw new SessionExpiredException();
      } else {
        final String serverHash = userSessionManager.getHash();
        if (serverHash != null && !serverHash.equals(userHash)) {
          userSessionManager.logout();
          final User user = userSessionManager.getUser();
          final String userName = user != null ? " for user " + user.getShortName() : "";
          LOG.info("Session expired (userHash: " + userHash + " different from server hash: "
              + serverHash + ")" + userName);
          logLine(method, userHash, false);
          throw new SessionExpiredException();
        }
      }
      final Object result = invocation.proceed();
      logLine(method, userHash, false);
      return result;
    } finally {
    }
  }

  /**
   * Log line.
   * 
   * @param method
   *          the method
   * @param userHash
   *          the user hash
   * @param start
   *          the start
   */
  private void logLine(final String method, final String userHash, final boolean start) {
    LOG.info(new StringBuffer().append("----- ").append(start ? "Starting" : "Ending").append(
        " method: ").append(method).append("- userhash: ").append(userHash).append(" -----"));
  }

}
