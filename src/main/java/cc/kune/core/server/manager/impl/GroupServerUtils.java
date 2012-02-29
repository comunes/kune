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
    if (subGroup.equals(SocialNetworkSubGroup.admins) || subGroup.equals(SocialNetworkSubGroup.all)) {
      final Set<Group> admins = ofGroup.getSocialNetwork().getAccessLists().getAdmins().getList();
      members.addAll(admins);
    }
    if (subGroup.equals(SocialNetworkSubGroup.collabs) || subGroup.equals(SocialNetworkSubGroup.all)) {
      final Set<Group> collabs = ofGroup.getSocialNetwork().getAccessLists().getEditors().getList();
      members.addAll(collabs);
    }
    return members;
  }
}
