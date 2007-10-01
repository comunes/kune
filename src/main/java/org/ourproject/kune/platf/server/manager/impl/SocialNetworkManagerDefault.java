/*
 *
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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements SocialNetworkManager {

    private final Provider<EntityManager> provider;

    @Inject
    public SocialNetworkManagerDefault(final Provider<EntityManager> provider) {
	super(provider, SocialNetwork.class);
	this.provider = provider;
    }

    public void addAdmin(final User user, final Group group) {
	SocialNetwork sn = group.getSocialNetwork();
	sn.addAdmin(user.getUserGroup());
    }

    public void addGroupToAdmins(final Group group, final Group inGroup) {
	SocialNetwork sn = inGroup.getSocialNetwork();
	sn.addAdmin(group);
    }

    public void addGroupToCollabs(final Group group, final Group inGroup) {
	SocialNetwork sn = inGroup.getSocialNetwork();
	sn.addCollaborator(group);
    }

    public void addGroupToViewers(final Group group, final Group inGroup) {
	SocialNetwork sn = inGroup.getSocialNetwork();
	sn.addViewer(group);
    }

    public String requestToJoin(final User user, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();
	AdmissionType admissionType = inGroup.getAdmissionType();
	if (admissionType == null) {
	    throw new RuntimeException();
	}
	if (isModerated(admissionType)) {
	    sn.addPendingCollaborator(user.getUserGroup());
	    return SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION;
	} else if (isOpen(admissionType)) {
	    sn.addCollaborator(user.getUserGroup());
	    return SocialNetworkDTO.REQ_JOIN_ACEPTED;
	} else if (isClosed(admissionType)) {
	    return SocialNetworkDTO.REQ_JOIN_DENIED;
	} else {
	    throw new SerializableException("State not expected in SocialNetworkManagerDefault class");
	}
    }

    public void acceptJoinGroup(final Group group, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();
	List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
	if (pendingCollabs.contains(group)) {
	    sn.addCollaborator(group);
	    sn.removePendingCollaborator(group);
	} else {
	    throw new SerializableException("User is not a pending collaborator");
	}
    }

    public void deleteMember(final Group group, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();

	if (sn.isAdmin(group)) {
	    sn.removeAdmin(group);
	} else if (sn.isCollab(group)) {
	    sn.removeCollaborator(group);
	} else {
	    throw new SerializableException("Person/Group is not a collaborator");
	}
    }

    public void denyJoinGroup(final Group group, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();
	List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
	if (pendingCollabs.contains(group)) {
	    sn.removePendingCollaborator(group);
	} else {
	    throw new SerializableException("Person/Group is not a pending collaborator");
	}
    }

    public void setCollabAsAdmin(final Group group, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();
	if (sn.isCollab(group)) {
	    sn.removeCollaborator(group);
	    sn.addAdmin(group);
	} else {
	    throw new SerializableException("Person/Group is not a collaborator");
	}
    }

    public void setAdminAsCollab(final Group group, final Group inGroup) throws SerializableException {
	SocialNetwork sn = inGroup.getSocialNetwork();
	if (sn.isAdmin(group)) {
	    sn.removeAdmin(group);
	    sn.addCollaborator(group);
	} else {
	    throw new SerializableException("Person/Group is not an admin");
	}
    }

    private boolean isClosed(final AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Closed);
    }

    private boolean isOpen(final AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Open);
    }

    private boolean isModerated(final AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Moderated);
    }

}
