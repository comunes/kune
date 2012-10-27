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
package cc.kune.core.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.UserSessionManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthenticatedMethodInterceptor implements MethodInterceptor {

  public static final Log LOG = LogFactory.getLog(AuthenticatedMethodInterceptor.class);

  @Inject
  Provider<HttpServletRequest> requestProvider;

  @Inject
  UserSessionManager userSessionManager;

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
      } else if (userSessionManager.isUserNotLoggedIn() && userHash == null) {
        // Ok, do nothing
      } else if (userSessionManager.isUserNotLoggedIn() && userHash != null) {
        LOG.info("Session expired (not logged in server)");
        logLine(method, userHash, false);
        throw new SessionExpiredException();
      } else if (!userSessionManager.getHash().equals(userHash)) {
        final String serverHash = userSessionManager.getHash();
        userSessionManager.logout();
        LOG.info("Session expired (userHash: " + userHash + " different from server hash: " + serverHash
            + ")");
        logLine(method, userHash, false);
        throw new SessionExpiredException();
      }
      final Object result = invocation.proceed();
      logLine(method, userHash, false);
      return result;
    } finally {
    }
  }

  private void logLine(final String method, final String userHash, final boolean start) {
    LOG.info(new StringBuffer().append("----- ").append(start ? "Starting" : "Ending").append(
        " method: ").append(method).append("- userhash: ").append(userHash).append(" -----"));
  }

}
