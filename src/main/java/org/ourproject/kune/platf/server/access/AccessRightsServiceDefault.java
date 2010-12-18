/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.server.access;

import java.util.ArrayList;
import java.util.HashSet;


import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.GroupList;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

import com.google.inject.Singleton;

@Singleton
public class AccessRightsServiceDefault implements AccessRightsService {
    // TODO: check performance

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

    public AccessRights get(final User user, final AccessLists lists) {
        return get(user.getUserGroup(), lists);
    }

    private boolean canAccess(final Group searchedGroup, final AccessLists lists, final AccessRol rol) {
        final GroupList list = lists.getList(rol);
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
        final ArrayList<Group> noVisitedYet = list.duplicate();
        noVisitedYet.removeAll(visited);
        for (final Group group : noVisitedYet) {
            visited.add(group);
            final SocialNetwork socialNetwork = group.getSocialNetwork();
            final GroupList groupList = socialNetwork.getAccessLists().getList(rol);
            return depthFirstSearch(visited, searchedGroup, groupList, rol);
        }
        return false;
    }
}
