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

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements SocialNetworkManager {

    @Inject
    public SocialNetworkManagerDefault(final Provider<EntityManager> provider) {
	super(provider, SocialNetwork.class);
    }

    public void addAdmin(final Group group, final User user) {
	SocialNetwork sn = group.getSocialNetwork();
	sn.addAdmin(user.getUserGroup());
    }

    public String requestToJoin(Group group, User user) {
	SocialNetwork sn = group.getSocialNetwork();
	AdmissionType admissionType = group.getAdmissionType();
	if (admissionType == null)
	    throw new RuntimeException();
	if (isModerated(admissionType)) {
	    sn.addPendingCollaborator(user.getUserGroup());
	    return SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION;
	} else if (isOpen(admissionType)) {
	    sn.addCollaborator(user.getUserGroup());
	    return SocialNetworkDTO.REQ_JOIN_ACEPTED;
	} else if (isClosed(admissionType)) {
	    return SocialNetworkDTO.REQ_JOIN_DENIED;
	} else if (isPersonal(admissionType)) {
	    return SocialNetworkDTO.REQ_JOIN_DENIED;
	} else {
	    throw new RuntimeException();
	}
    }

    private boolean isPersonal(AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Personal);
    }

    private boolean isClosed(AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Closed);
    }

    private boolean isOpen(AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Open);
    }

    private boolean isModerated(AdmissionType admissionType) {
	return admissionType.equals(AdmissionType.Moderated);
    }
}
