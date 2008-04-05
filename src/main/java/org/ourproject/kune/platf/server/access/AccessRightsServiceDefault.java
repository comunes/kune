
package org.ourproject.kune.platf.server.access;

import java.util.ArrayList;
import java.util.HashSet;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

public class AccessRightsServiceDefault implements AccessRightsService {
    // TODO: check performance

    public AccessRights get(final User user, final AccessLists lists) {
        return get(user.getUserGroup(), lists);
    }

    public AccessRights get(final Group userGroup, final AccessLists accessList) {
        boolean isAdministrable = false;
        boolean isEditable = false;
        boolean isVisible = false;

        // FIXME, future: admin users can admin, edit, view everything
        // (not now while we are doing tests)
        isVisible = isEditable = isAdministrable = canAccess(userGroup, accessList, AccessRol.Administrator);
        if (!isEditable) {
            isVisible = isEditable = canAccess(userGroup, accessList, AccessRol.Editor);
        }
        if (!isVisible) {
            isVisible = accessList.getViewers().isEmpty() || canAccess(userGroup, accessList, AccessRol.Viewer);
        }

        return new AccessRights(isAdministrable, isEditable, isVisible);
    }

    private boolean canAccess(final Group searchedGroup, final AccessLists lists, final AccessRol rol) {
        GroupList list = lists.getList(rol);
        return depthFirstSearch(new HashSet<Group>(), searchedGroup, list, rol);
    }

    /*
     * http://en.wikipedia.org/wiki/Depth-first_search
     */
    private boolean depthFirstSearch(final HashSet<Group> visited, final Group searchedGroup, final GroupList list,
            final AccessRol rol) {
        if (list.includes(searchedGroup)) {
            return true;
        }
        ArrayList<Group> noVisitedYet = list.duplicate();
        noVisitedYet.removeAll(visited);
        for (Group group : noVisitedYet) {
            visited.add(group);
            SocialNetwork socialNetwork = group.getSocialNetwork();
            GroupList groupList = socialNetwork.getAccessLists().getList(rol);
            return depthFirstSearch(visited, searchedGroup, groupList, rol);
        }
        return false;
    }
}
