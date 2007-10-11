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
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SocialNetworkRPC implements SocialNetworkService, RPC {

    private final UserSession session;
    private final GroupManager groupManager;
    private final SocialNetworkManager socialNetworkManager;
    private final Mapper mapper;

    @Inject
    public SocialNetworkRPC(final UserSession session, final GroupManager groupManager,
	    final SocialNetworkManager socialNetworkManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.socialNetworkManager = socialNetworkManager;
	this.mapper = mapper;
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public String requestJoinGroup(final String hash, final String groupShortName) throws SerializableException {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	return socialNetworkManager.requestToJoin(user, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void unJoinGroup(final String hash, final String groupToUnJoinShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToUnJoin = groupManager.findByShortName(groupToUnJoinShortName);
	socialNetworkManager.deleteMember(groupToUnJoin, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void AcceptJoinGroup(final String hash, final String groupToAcceptShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAccept = groupManager.findByShortName(groupToAcceptShortName);
	socialNetworkManager.acceptJoinGroup(groupToAccept, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteMember(final String hash, final String groupToDeleleShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToDelete = groupManager.findByShortName(groupToDeleleShortName);
	socialNetworkManager.deleteMember(groupToDelete, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void denyJoinGroup(final String hash, final String groupToDenyShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToDenyJoin = groupManager.findByShortName(groupToDenyShortName);
	socialNetworkManager.denyJoinGroup(groupToDenyJoin, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void setCollabAsAdmin(final String hash, final String groupToSetAdminShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToSetAdmin = groupManager.findByShortName(groupToSetAdminShortName);
	socialNetworkManager.setCollabAsAdmin(groupToSetAdmin, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void setAdminAsCollab(final String hash, final String groupToSetCollabShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToSetCollab = groupManager.findByShortName(groupToSetCollabShortName);
	socialNetworkManager.setAdminAsCollab(groupToSetCollab, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addAdminMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToAdmins(groupToAdd, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addCollabMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToCollabs(groupToAdd, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addViewerMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToViewers(groupToAdd, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public SocialNetworkDTO getGroupMembers(final String hash, final String groupShortName)
	    throws AccessViolationException {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	return mapper.map(socialNetworkManager.find(user, group), SocialNetworkDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public ParticipationDataDTO getParticipation(final String hash, final String groupShortName)
	    throws AccessViolationException {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	return mapper.map(socialNetworkManager.findParticipation(user, group), ParticipationDataDTO.class);
    }

}
