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

package org.ourproject.kune.platf.server.access;

import java.util.ArrayList;
import java.util.HashSet;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

class RightsServiceDefault implements RightsService {
    // TODO: check performance
    HashSet<Group> visited;

    public AccessRights get(final Group userGroup, final AccessLists accessList) {
	visited = new HashSet<Group>();
	AccessRights accessRights = new AccessRights();
	boolean isAdministrable = false;
	boolean isEditable = false;
	boolean isVisible = false;

	// FIXME: para salir del paso
	// hay que pensar: "por todo el mundo" y "por nadie" en las access list
	if (userGroup == null) {
	    return new AccessRights(false, false, true);
	}

	// FIXME, future: site and admin users can admin, edit, view everything
	// (not now while we are doing tests)

	isAdministrable = depthFirstSearch(userGroup, accessList.getAdmins(), AccessRights.ADMIN);
	if (!isAdministrable) {
	    visited.clear();
	    isEditable = depthFirstSearch(userGroup, accessList.getEditors(), AccessRights.EDIT);
	    if (!isEditable) {
		visited.clear();
		if (accessList.getViewers().isEmpty())
		    // If nobody in viewers then is visible for everybody
		    // FIXME: the same with edit? wiki mode?
		    isVisible = true;
		else
		    isVisible = depthFirstSearch(userGroup, accessList.getViewers(), AccessRights.VIEW);
	    } else {
		isVisible = true;
	    }
	} else {
	    isEditable = true;
	    isVisible = true;
	}
	accessRights.setAdministrable(isAdministrable);
	accessRights.setEditable(isEditable);
	accessRights.setVisible(isVisible);
	return accessRights;
    }

    /*
     * http://en.wikipedia.org/wiki/Depth-first_search
     */
    private boolean depthFirstSearch(final Group searchedGroup, final GroupList list, final int type) {
	if (list.contains(searchedGroup)) {
	    return true;
	}
	ArrayList<Group> noVisitedYet = list.duplicate();
	noVisitedYet.removeAll(visited);
	for (Group group : noVisitedYet) {
	    visited.add(group);
	    SocialNetwork socialNetwork = group.getSocialNetwork();
	    AccessLists accessLists = socialNetwork.getAccessList();
	    if (type == AccessRights.ADMIN) {
		return depthFirstSearch(searchedGroup, accessLists.getAdmins(), type);
	    } else if (type == AccessRights.EDIT) {
		return depthFirstSearch(searchedGroup, accessLists.getEditors(), type);
	    } else if (type == AccessRights.VIEW) {
		return depthFirstSearch(searchedGroup, accessLists.getViewers(), type);
	    }
	}
	return false;
    }
}
