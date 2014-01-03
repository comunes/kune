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

import javax.persistence.NoResultException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizatedMethodInterceptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AuthorizatedMethodInterceptor implements MethodInterceptor {

  /** The access service provider. */
  @Inject
  private Provider<AccessService> accessServiceProvider;

  /** The group manager provider. */
  @Inject
  private Provider<GroupManager> groupManagerProvider;

  /** The user session provider. */
  @Inject
  private Provider<UserSessionManager> userSessionProvider;

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
    final StateToken token = (StateToken) arguments[1];

    final UserSessionManager userSession = userSessionProvider.get();
    final GroupManager groupManager = groupManagerProvider.get();
    final AccessService accessService = accessServiceProvider.get();

    final Authorizated authoAnnotation = invocation.getStaticPart().getAnnotation(Authorizated.class);
    final AccessRol accessRol = authoAnnotation.accessRolRequired();
    final ActionLevel actionLevel = authoAnnotation.actionLevel();
    final boolean mustBeMember = authoAnnotation.mustCheckMembership();

    final User user = userSession.getUser();
    Group group = Group.NO_GROUP;
    try {
      group = groupManager.findByShortName(token.getGroup());
    } catch (final NoResultException e) {
      // continue, and check later
    }

    switch (actionLevel) {
    case content:
    default:
      final Content content = accessService.accessToContent(ContentUtils.parseId(token.getDocument()),
          user, accessRol);
      if (!content.getContainer().getOwner().equals(group)) {
        throw new AccessViolationException();
      }
      if (!content.getContainer().getId().equals(ContentUtils.parseId(token.getFolder()))) {
        throw new AccessViolationException();
      }
      if (!content.getContainer().getToolName().equals(token.getTool())) {
        throw new AccessViolationException();
      }
    case container:
      final Container container = accessService.accessToContainer(
          ContentUtils.parseId(token.getFolder()), user, accessRol);
      if (!container.getOwner().equals(group)) {
        throw new AccessViolationException();
      }
    case tool:
    case group:
      break;
    }

    if (mustBeMember) {
      if (!AccessRightsUtils.correctMember(user, group, accessRol)) {
        throw new AccessViolationException();
      }
    }

    return invocation.proceed();
  }

}
