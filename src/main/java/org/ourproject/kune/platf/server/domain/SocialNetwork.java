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

package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ourproject.kune.platf.server.access.AccessRol;

@Entity
@Table(name = "social_networks")
public class SocialNetwork {
    @Id
    @GeneratedValue
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    AccessLists accessLists;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList pendingCollaborators;

    public SocialNetwork() {
	accessLists = new AccessLists();
	pendingCollaborators = new GroupList();
    }

    public Long getId() {
	return id;
    }

    public GroupList getPendingCollaborators() {
	return pendingCollaborators;
    }

    public void setPendingCollaborators(final GroupList pendingCollaborators) {
	this.pendingCollaborators = pendingCollaborators;
    }

    public void setAccessLists(final AccessLists accessList) {
	this.accessLists = accessList;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void addAdmin(final Group group) {
	accessLists.addAdmin(group);
    }

    public void addCollaborator(final Group group) {
	accessLists.addEditor(group);
    }

    public void addViewer(final Group group) {
	accessLists.addViewer(group);
    }

    public void addPendingCollaborator(final Group group) {
	pendingCollaborators.add(group);
    }

    public AccessLists getAccessLists() {
	return accessLists;
    }

    public GroupList getGroupList(final AccessRol type) {
	if (type == AccessRol.Administrator) {
	    return accessLists.getAdmins();
	} else if (type == AccessRol.Editor) {
	    return accessLists.getEditors();
	} else if (type == AccessRol.Viewer) {
	    return accessLists.getViewers();
	} else {
	    throw new RuntimeException();
	}
    }
}
