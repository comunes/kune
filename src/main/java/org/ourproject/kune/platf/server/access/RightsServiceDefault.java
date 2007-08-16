package org.ourproject.kune.platf.server.access;

import java.util.ArrayList;
import java.util.HashSet;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

class RightsServiceDefault implements RightsService {
    // TODO: mirar rendimiento
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

	isAdministrable = depthFirstSearch(userGroup, accessList.getAdmins(), AccessRights.ADMIN);
	if (!isAdministrable) {
	    visited.clear();
	    isEditable = depthFirstSearch(userGroup, accessList.getEditors(), AccessRights.EDIT);
	    if (!isEditable) {
		visited.clear();
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
