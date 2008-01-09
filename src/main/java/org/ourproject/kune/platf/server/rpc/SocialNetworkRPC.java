/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessType;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
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
    @Transactional(type = TransactionType.READ_WRITE)
    public String requestJoinGroup(final String hash, final String groupShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User user = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        return socialNetworkManager.requestToJoin(user, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO unJoinGroup(final String hash, final String groupShortName)
            throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        socialNetworkManager.unJoinGroup(userLogged.getUserGroup(), group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO AcceptJoinGroup(final String hash, final String groupShortName,
            final String groupToAcceptShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToAccept = groupManager.findByShortName(groupToAcceptShortName);
        socialNetworkManager.acceptJoinGroup(userLogged, groupToAccept, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO deleteMember(final String hash, final String groupShortName,
            final String groupToDeleleShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToDelete = groupManager.findByShortName(groupToDeleleShortName);
        socialNetworkManager.deleteMember(userLogged, groupToDelete, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO denyJoinGroup(final String hash, final String groupShortName,
            final String groupToDenyShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToDenyJoin = groupManager.findByShortName(groupToDenyShortName);
        socialNetworkManager.denyJoinGroup(userLogged, groupToDenyJoin, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO setCollabAsAdmin(final String hash, final String groupShortName,
            final String groupToSetAdminShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToSetAdmin = groupManager.findByShortName(groupToSetAdminShortName);
        socialNetworkManager.setCollabAsAdmin(userLogged, groupToSetAdmin, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO setAdminAsCollab(final String hash, final String groupShortName,
            final String groupToSetCollabShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToSetCollab = groupManager.findByShortName(groupToSetCollabShortName);
        socialNetworkManager.setAdminAsCollab(userLogged, groupToSetCollab, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addAdminMember(final String hash, final String groupShortName,
            final String groupToAddShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToAdmins(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addCollabMember(final String hash, final String groupShortName,
            final String groupToAddShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToCollabs(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE)
    public SocialNetworkResultDTO addViewerMember(final String hash, final String groupShortName,
            final String groupToAddShortName) throws SerializableException {
        UserSession userSession = getUserSession();
        User userLogged = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
        socialNetworkManager.addGroupToViewers(userLogged, groupToAdd, group);
        return new SocialNetworkResultDTO(getGroupMembers(userLogged, group), getParticipation(userLogged, group));
    }

    @Authenticated(mandatory = false)
    // At least you can access as Viewer to the Group
    @Authorizated(accessTypeRequired = AccessType.READ)
    @Transactional(type = TransactionType.READ_ONLY)
    public SocialNetworkDTO getGroupMembers(final String hash, final String groupShortName)
            throws SerializableException {
        UserSession userSession = getUserSession();
        User user = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        return getGroupMembers(user, group);
    }

    private SocialNetworkDTO getGroupMembers(final User user, final Group group) throws SerializableException {
        return mapper.map(socialNetworkManager.find(user, group), SocialNetworkDTO.class);
    }

    @Authenticated(mandatory = false)
    // At least you can access as Viewer to the Group
    @Authorizated(accessTypeRequired = AccessType.READ)
    @Transactional(type = TransactionType.READ_ONLY)
    public ParticipationDataDTO getParticipation(final String hash, final String groupShortName)
            throws SerializableException {
        UserSession userSession = getUserSession();
        User user = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        return getParticipation(user, group);
    }

    private ParticipationDataDTO getParticipation(final User user, final Group group) throws SerializableException {
        return mapper.map(socialNetworkManager.findParticipation(user, group), ParticipationDataDTO.class);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

}