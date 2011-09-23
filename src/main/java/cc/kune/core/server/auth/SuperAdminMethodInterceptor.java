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
package cc.kune.core.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SuperAdminMethodInterceptor implements MethodInterceptor {

  public static final Log LOG = LogFactory.getLog(SuperAdminMethodInterceptor.class);

  @Inject
  Provider<GroupFinder> groupFinder;
  @Inject
  Provider<KuneProperties> kuneProperties;
  @Inject
  Provider<HttpServletRequest> requestProvider;
  @Inject
  Provider<SessionService> sessionServiceProvider;
  private Group siteGroup;
  @Inject
  UserSessionManager userSessionManager;

  @Override
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    final Object[] arguments = invocation.getArguments();
    // Some browsers getCookie returns "null" as String instead of null
    final String userHash = arguments[0] == null || arguments[0].equals("null") ? null
        : (String) arguments[0];

    LOG.info("Method: " + invocation.getMethod().getName());
    LOG.info("Userhash received: " + userHash);
    LOG.info("--------------------------------------------------------------------------------");

    final SuperAdmin authAnnotation = invocation.getStaticPart().getAnnotation(SuperAdmin.class);
    final AccessRol rol = authAnnotation.rol();

    if (siteGroup == null) {
      siteGroup = groupFinder.get().findByShortName(
          kuneProperties.get().get(KuneProperties.DEFAULT_SITE_SHORT_NAME));
    }

    if (userHash == null) {
      throw new UserMustBeLoggedException();
    } else if (userSessionManager.isUserNotLoggedIn()) {
      LOG.info("Session expired (not logged in server and mandatory)");
      throw new SessionExpiredException();
    } else {
      final User user = userSessionManager.getUser();
      if (!AccessRightsUtils.correctMember(user, siteGroup, rol)) {
        throw new AccessViolationException();
      }
    }
    final Object result = invocation.proceed();
    return result;
  }
}
