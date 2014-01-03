/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.access;

import java.util.ArrayList;
import java.util.HashSet;

import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.GroupList;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessRightsServiceDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AccessRightsServiceDefault implements AccessRightsService {
  // TODO: check performance

  /**
   * Can access.
   * 
   * @param searchedGroup
   *          the searched group
   * @param lists
   *          the lists
   * @param rol
   *          the rol
   * @return true, if successful
   */
  private boolean canAccess(final Group searchedGroup, final AccessLists lists, final AccessRol rol) {
    final GroupList list = lists.getList(rol);
    return depthFirstSearch(new HashSet<Group>(), searchedGroup, list, rol);
  }

  /*
   * http://en.wikipedia.org/wiki/Depth-first_search
   */
  /**
   * Depth first search.
   * 
   * @param visited
   *          the visited
   * @param searchedGroup
   *          the searched group
   * @param list
   *          the list
   * @param rol
   *          the rol
   * @return true, if successful
   */
  private boolean depthFirstSearch(final HashSet<Group> visited, final Group searchedGroup,
      final GroupList list, final AccessRol rol) {
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

  /**
   * Gets the.
   * 
   * @param userGroup
   *          the user group
   * @param accessList
   *          the access list
   * @return the access rights
   */
  public AccessRights get(final Group userGroup, final AccessLists accessList) {
    boolean isAdministrable = false;
    boolean isEditable = false;
    boolean isVisible = false;

    isVisible = isEditable = isAdministrable = canAccess(userGroup, accessList, AccessRol.Administrator);
    if (!isEditable) {
      isVisible = isEditable = canAccess(userGroup, accessList, AccessRol.Editor);
    }
    if (!isVisible) {
      isVisible = (accessList.getViewers().getMode().equals(GroupListMode.EVERYONE))
          || canAccess(userGroup, accessList, AccessRol.Viewer);
    }

    return new AccessRights(isAdministrable, isEditable, isVisible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.AccessRightsService#get(cc.kune.domain.User,
   * cc.kune.domain.AccessLists)
   */
  @Override
  public AccessRights get(final User user, final AccessLists lists) {
    return get(user.getUserGroup(), lists);
  }
}
