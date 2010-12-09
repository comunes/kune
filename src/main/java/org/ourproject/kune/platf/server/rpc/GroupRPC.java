/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetworkVisibility;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.shared.dto.AdmissionTypeDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.SocialNetworkVisibilityDTO;
import cc.kune.core.shared.dto.StateToken;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class GroupRPC implements RPC, GroupService {
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final Provider<UserSession> userSessionProvider;
    private final ContentManager contentManager;

    @Inject
    public GroupRPC(final Provider<UserSession> userSessionProvider, final GroupManager groupManager,
            final ContentManager contentManager, final Mapper mapper) {
        this.userSessionProvider = userSessionProvider;
        this.groupManager = groupManager;
        this.contentManager = contentManager;
        this.mapper = mapper;
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license) {
        final User user = getUserLogged();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        groupManager.changeDefLicense(user, group, license.getShortName());
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public void changeGroupWsTheme(final String userHash, final StateToken groupToken, final String theme)
            throws DefaultException {
        final User user = getUserLogged();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        groupManager.changeWsTheme(user, group, theme);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public GroupDTO clearGroupBackImage(final String userHash, final StateToken token) {
        final Group group = groupManager.findByShortName(token.getGroup());
        groupManager.clearGroupBackImage(group);
        return mapper.map(group, GroupDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = DefaultException.class)
    public StateToken createNewGroup(final String userHash, final GroupDTO groupDTO, final String publicDesc,
            final String tags, final String[] enabledTools) throws DefaultException {
        final User user = getUserLogged();
        final Group group = mapper.map(groupDTO, Group.class);
        final Group newGroup = groupManager.createGroup(group, user);
        final Long defContentId = newGroup.getDefaultContent().getId();
        contentManager.save(user, newGroup.getDefaultContent(), publicDesc);
        contentManager.setTags(user, defContentId, tags);
        return newGroup.getDefaultContent().getStateToken();
    };

    @Authenticated(mandatory = false)
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
    public GroupDTO getGroup(final String userHash, final StateToken groupToken) {
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        return mapper.map(group, GroupDTO.class);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public GroupDTO setGroupBackImage(final String userHash, final StateToken token) {
        final Group group = groupManager.findByShortName(token.getGroup());
        final Content content = contentManager.find(ContentUtils.parseId(token.getDocument()));
        groupManager.setGroupBackImage(group, content);
        return mapper.map(group, GroupDTO.class);
    }

    @Authenticated(mandatory = true)
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setGroupNewMembersJoiningPolicy(final String userHash, final StateToken token,
            final AdmissionTypeDTO admissionPolicy) {
        final Group group = groupManager.findByShortName(token.getGroup());
        group.setAdmissionType(AdmissionType.valueOf(admissionPolicy.toString()));
    }

    @Authenticated(mandatory = true)
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setSocialNetworkVisibility(final String userHash, final StateToken token,
            final SocialNetworkVisibilityDTO visibility) {
        final Group group = groupManager.findByShortName(token.getGroup());
        group.getSocialNetwork().setVisibility(SocialNetworkVisibility.valueOf(visibility.toString()));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setToolEnabled(final String userHash, final StateToken groupToken, final String toolName,
            final boolean enabled) {
        groupManager.setToolEnabled(getUserLogged(), groupToken.getGroup(), toolName, enabled);
    }

    private User getUserLogged() {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        return user;
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

}
