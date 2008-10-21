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
package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SocialNetworkRPC implements SocialNetworkService, RPC {

    private final Provider<UserSession> userSessionProvider;
    private final GroupManager groupManager;
    private final SocialNetworkManager socialNetworkManager;
    private final Mapper mapper;

    @Inject
    public SocialNetworkRPC(final Provider<UserSession> userSessionProvider, final GroupManager groupManager,
            final SocialNetworkManager socialNetworkManager, final Mapper mapper) {
        this.userSessionProvider = userSessionProvider;
        this.groupManager = groupManager;
        this.socialNetworkManager = socialNetworkManager;
        this.mapper = mapper;
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO acceptJoinGroup(final String hash, final StateToken groupToken,
            final String groupToAcceptShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToAccept = groupManager.findByShortName(groupToAcceptShortName);
        socialNetworkManager.acceptJoinGroup(userLogged, groupToAccept, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addAdminMember(final String hash, final StateToken groupToken,
            final String groupToAddShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToAdmins(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addCollabMember(final String hash, final StateToken groupToken,
            final String groupToAddShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToCollabs(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addViewerMember(final String hash, final StateToken groupToken,
            final String groupToAddShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToViewers(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO deleteMember(final String hash, final StateToken groupToken,
            final String groupToDeleleShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToDelete = groupManager.findByShortName(groupToDeleleShortName);
        socialNetworkManager.deleteMember(userLogged, groupToDelete, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO denyJoinGroup(final String hash, final StateToken groupToken,
            final String groupToDenyShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToDenyJoin = groupManager.findByShortName(groupToDenyShortName);
        socialNetworkManager.denyJoinGroup(userLogged, groupToDenyJoin, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated(mandatory = false)
    // At least you can access as Viewer to the Group
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_ONLY)
    public SocialNetworkDTO getGroupMembers(final String hash, final StateToken groupToken) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        return getGroupMembers(user, group);
    }

    @Authenticated(mandatory = false)
    // At least you can access as Viewer to the Group
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_ONLY)
    public ParticipationDataDTO getParticipation(final String hash, final StateToken groupToken)
            throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        return getParticipation(user, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkRequestResult requestJoinGroup(final String hash, final StateToken groupToken)
            throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        return socialNetworkManager.requestToJoin(user, group);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO setAdminAsCollab(final String hash, final StateToken groupToken,
            final String groupToSetCollabShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToSetCollab = groupManager.findByShortName(groupToSetCollabShortName);
        socialNetworkManager.setAdminAsCollab(userLogged, groupToSetCollab, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO setCollabAsAdmin(final String hash, final StateToken groupToken,
            final String groupToSetAdminShortName) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        final Group groupToSetAdmin = groupManager.findByShortName(groupToSetAdminShortName);
        socialNetworkManager.setCollabAsAdmin(userLogged, groupToSetAdmin, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO unJoinGroup(final String hash, final StateToken groupToken) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User userLogged = userSession.getUser();
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        socialNetworkManager.unJoinGroup(userLogged.getUserGroup(), group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    private SocialNetworkDTO getGroupMembers(final User user, final Group group) throws DefaultException {
        return mapper.map(socialNetworkManager.get(user, group), SocialNetworkDTO.class);
    }

    private ParticipationDataDTO getParticipation(final User user, final Group group) throws DefaultException {
        return mapper.map(socialNetworkManager.findParticipation(user, group), ParticipationDataDTO.class);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

}
