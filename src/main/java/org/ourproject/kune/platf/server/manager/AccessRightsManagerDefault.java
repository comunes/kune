package org.ourproject.kune.platf.server.manager;

import java.util.HashSet;
import java.util.List;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRights;

import com.google.inject.Singleton;

@Singleton
public class AccessRightsManagerDefault implements AccessRightsManager {

    HashSet<Group> visited;

    public AccessRights get(final User user, final AccessLists accessList) {
	visited = new HashSet<Group>();
	AccessRights accessRights = new AccessRights();
	boolean isAdministrable = false;
	boolean isEditable = false;
	boolean isVisible = false;

	isAdministrable = dfs(user.getUserGroup(), accessList.getAdmin(), AccessRights.ADMIN);
	if (!isAdministrable) {
	    visited.clear();
	    isEditable = dfs(user.getUserGroup(), accessList.getEdit(), AccessRights.EDIT);
	    if (!isEditable) {
		visited.clear();
		isVisible = dfs(user.getUserGroup(), accessList.getView(), AccessRights.VIEW);
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
    private boolean dfs(final Group searchedGroup, final List<Group> list, final int type) {
	if (list.contains(searchedGroup)) {
	    return true;
	}
	list.removeAll(visited);
	for (Group group : list) {
	    visited.add(group);
	    if (type == AccessRights.ADMIN) {
		return dfs(searchedGroup, group.getSocialNetwork().getAdmins(), type);
	    } else if (type == AccessRights.EDIT) {
		return dfs(searchedGroup, group.getSocialNetwork().getCollaborators(), type);
	    } else if (type == AccessRights.VIEW) {
		return dfs(searchedGroup, group.getSocialNetwork().getViewer(), type);
	    }
	}
	return false;
    }
}
