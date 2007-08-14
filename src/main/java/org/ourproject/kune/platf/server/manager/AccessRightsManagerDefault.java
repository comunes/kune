package org.ourproject.kune.platf.server.manager;

import java.util.ArrayList;
import java.util.HashSet;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRights;

import com.google.inject.Singleton;

@Singleton
public class AccessRightsManagerDefault implements AccessRightsManager {
    // TODO: mirar rendimiento
    HashSet<Group> visited;

    public AccessRights get(final User user, final AccessLists accessList) {
	visited = new HashSet<Group>();
	AccessRights accessRights = new AccessRights();
	boolean isAdministrable = false;
	boolean isEditable = false;
	boolean isVisible = false;

	isAdministrable = depthFirstSearch(user.getUserGroup(), accessList.getAdmins(), AccessRights.ADMIN);
	if (!isAdministrable) {
	    visited.clear();
	    isEditable = depthFirstSearch(user.getUserGroup(), accessList.getEditors(), AccessRights.EDIT);
	    if (!isEditable) {
		visited.clear();
		isVisible = depthFirstSearch(user.getUserGroup(), accessList.getViewers(), AccessRights.VIEW);
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
