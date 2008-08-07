/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.auth;

import javax.persistence.NoResultException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.access.AccessRightsService;
import org.ourproject.kune.platf.server.access.AccessType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupAuthorizatedMethodInterceptor implements MethodInterceptor {

    @Inject
    Provider<UserSession> userSessionProvider;
    @Inject
    Provider<GroupManager> groupManagerProvider;
    @Inject
    Provider<AccessRightsService> accessRightsServiceProvider;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
	final Object[] arguments = invocation.getArguments();
	final String groupShortName = (String) arguments[1];

	final UserSession userSession = userSessionProvider.get();
	final GroupManager groupManager = groupManagerProvider.get();

	final GroupAuthorizated authoAnnotation = invocation.getStaticPart().getAnnotation(GroupAuthorizated.class);
	final AccessType accessType = authoAnnotation.accessTypeRequired();

	final User user = userSession.getUser();

	Group group = Group.NO_GROUP;
	try {
	    group = groupManager.findByShortName(groupShortName);
	} catch (final NoResultException e) {
	    throw new AccessViolationException();
	}

	if (!correctMember(user, group, accessType)) {
	    throw new AccessViolationException();
	}

	final Object result = invocation.proceed();
	return result;
    }

    private boolean correctMember(final User user, final Group group, final AccessType memberType)
	    throws AccessViolationException {
	final AccessRightsService accessRightsService = accessRightsServiceProvider.get();
	final AccessRights accessRights = accessRightsService.get(user, group.getSocialNetwork().getAccessLists());

	switch (memberType) {
	case ADMIN:
	    return accessRights.isAdministrable();
	case EDIT:
	    return accessRights.isEditable();
	default:
	    return accessRights.isVisible();
	}
    }

}
