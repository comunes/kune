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

import javax.persistence.NoResultException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class AuthorizatedMethodInterceptor implements MethodInterceptor {

    @Inject
    private Provider<UserSession> userSessionProvider;
    @Inject
    private Provider<GroupManager> groupManagerProvider;
    @Inject
    private Provider<AccessRightsService> accessRightsServiceProvider;
    @Inject
    private Provider<AccessService> accessServiceProvider;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Object[] arguments = invocation.getArguments();
        final StateToken token = (StateToken) arguments[1];

        final UserSession userSession = userSessionProvider.get();
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
            final Content content = accessService.accessToContent(ContentUtils.parseId(token.getDocument()), user,
                    accessRol);
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
            final Container container = accessService.accessToContainer(ContentUtils.parseId(token.getFolder()), user,
                    accessRol);
            if (!container.getOwner().equals(group)) {
                throw new AccessViolationException();
            }
        case tool:
        case group:
            break;
        }

        if (mustBeMember) {
            if (!correctMember(user, group, accessRol)) {
                throw new AccessViolationException();
            }
        }

        return invocation.proceed();
    }

    private boolean correctMember(final User user, final Group group, final AccessRol memberType)
            throws AccessViolationException {

        final AccessRights accessRights = accessRightsServiceProvider.get().get(user,
                group.getSocialNetwork().getAccessLists());

        switch (memberType) {
        case Administrator:
            return accessRights.isAdministrable();
        case Editor:
            return accessRights.isEditable();
        default:
            return accessRights.isVisible();
        }
    }

}
