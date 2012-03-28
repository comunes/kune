/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

public class GroupServerUtils {
  @Inject
  private static UserFinder userFinder;

  public static void getAllUserMembers(final Set<User> users, final Group ofGroup,
      final SocialNetworkSubGroup subGroup) {
    final Collection<Group> members = GroupServerUtils.getGroupMembers(ofGroup, subGroup);
    for (final Group member : members) {
      if (member.isPersonal()) {
        final String shortName = member.getShortName();
        try {
          final User user = userFinder.findByShortName(shortName);
          users.add(user);
        } catch (final NoResultException e) {
        }
      } else {
        // Is a group, so go recursively
        getAllUserMembers(users, member, subGroup);
      }
    }
  }

  public static void getAllUserMembersAsString(final Set<String> users, final Group ofGroup,
      final SocialNetworkSubGroup subGroup) {
    final Collection<Group> members = GroupServerUtils.getGroupMembers(ofGroup, subGroup);
    for (final Group member : members) {
      if (member.isPersonal()) {
        final String shortName = member.getShortName();
        try {
          users.add(shortName);
        } catch (final NoResultException e) {
        }
      } else {
        // Is a group, so go recursively
        getAllUserMembersAsString(users, member, subGroup);
      }
    }
  }

  public static final Collection<Group> getGroupMembers(final Group ofGroup,
      final SocialNetworkSubGroup subGroup) {
    final Collection<Group> members = new LinkedHashSet<Group>();
    if (subGroup.equals(SocialNetworkSubGroup.ADMINS) || subGroup.equals(SocialNetworkSubGroup.ALL_GROUP_MEMBERS)) {
      final Set<Group> admins = ofGroup.getSocialNetwork().getAccessLists().getAdmins().getList();
      members.addAll(admins);
    }
    if (subGroup.equals(SocialNetworkSubGroup.COLLABS) || subGroup.equals(SocialNetworkSubGroup.ALL_GROUP_MEMBERS)) {
      final Set<Group> collabs = ofGroup.getSocialNetwork().getAccessLists().getEditors().getList();
      members.addAll(collabs);
    }
    return members;
  }
}
