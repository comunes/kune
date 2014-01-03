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

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ShouldBeMemberMethodInterceptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShouldBeMemberMethodInterceptor implements MethodInterceptor {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(ShouldBeMemberMethodInterceptor.class);

  /** The group finder. */
  @Inject
  Provider<GroupFinder> groupFinder;

  /** The kune properties. */
  @Inject
  Provider<KuneProperties> kuneProperties;

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
    final Object[] arguments = invocation.getArguments();
    // Some browsers getCookie returns "null" as String instead of null
    final String userHash = arguments[0] == null || arguments[0].equals("null") ? null
        : (String) arguments[0];

    LOG.info("Method: " + invocation.getMethod().getName());
    LOG.info("Userhash received: " + userHash);
    LOG.info("--------------------------------------------------------------------------------");

    final ShouldBeMember authAnnotation = invocation.getStaticPart().getAnnotation(ShouldBeMember.class);
    final AccessRol rol = authAnnotation.rol();

    final Group allowedGroup = groupFinder.get().findByShortName(
        kuneProperties.get().get(authAnnotation.groupKuneProperty()));
    final AccessLists acl = allowedGroup.getAccessLists();
    LOG.info(String.format("Translator group: %s, acl %s", allowedGroup.getShortName(), acl));
    LOG.info(String.format("Auth rol required: %s", rol.toString()));

    if (userHash == null) {
      throw new UserMustBeLoggedException();
    } else if (userSessionManager.isUserNotLoggedIn()) {
      LOG.info("Session expired (not logged in server and mandatory)");
      throw new SessionExpiredException();
    } else {
      final User user = userSessionManager.getUser();
      if (!AccessRightsUtils.correctMember(user, allowedGroup, rol)) {
        LOG.info(String.format(
            "Don't have rights for do that. User: %s, not %s member of %s with acl %s",
            user.getShortName(), rol, allowedGroup.getShortName(), acl));
        throw new AccessViolationException();
      }
    }
    final Object result = invocation.proceed();
    return result;
  }
}
