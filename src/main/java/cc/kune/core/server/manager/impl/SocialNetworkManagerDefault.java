/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.AlreadyGroupMemberException;
import cc.kune.core.client.errors.AlreadyUserMemberException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.InvalidSNOperationException;
import cc.kune.core.client.errors.LastAdminInGroupException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.error.ServerException;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.domain.Group;
import cc.kune.domain.ParticipationData;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.SocialNetworkData;
import cc.kune.domain.User;
import cc.kune.domain.UserBuddiesData;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements
    SocialNetworkManager {
  public static final Log LOG = LogFactory.getLog(SocialNetworkManagerDefault.class);

  private final AccessRightsService accessRightsService;
  private final GroupFinder finder;
  private final Mapper mapper;
  private final NotificationService notifyService;
  private final SocialNetworkCache snCache;

  private final UserManager userManager;

  @Inject
  public SocialNetworkManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final GroupFinder finder, final AccessRightsService accessRightsService, final Mapper mapper,
      final UserManager userManager, final SocialNetworkCache snCache,
      final NotificationService notifyService) {
    super(provider, SocialNetwork.class);
    this.finder = finder;
    this.accessRightsService = accessRightsService;
    this.mapper = mapper;
    this.userManager = userManager;
    this.snCache = snCache;
    this.notifyService = notifyService;
  }

  @Override
  public void acceptJoinGroup(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException, AccessViolationException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    final Set<Group> pendingCollabs = sn.getPendingCollaborators().getList();
    if (pendingCollabs.contains(group)) {
      addGroupToCollabs(userLogged, group, inGroup);
      notifyService.notifyGroupMembers(group, inGroup, "Accepted as member",
          "You are now member of this group");
    } else {
      throw new DefaultException("User is not a pending collaborator");
    }
  }

  void addAdmin(final User newAdmin, final Group group) {
    final SocialNetwork sn = group.getSocialNetwork();
    sn.addAdmin(newAdmin.getUserGroup());
    snCache.expire(group);
  }

  @Override
  public void addAsBuddie(final User userLogged, final User toUser) {
    notifyService.notifyUserToUserByEmail(userLogged, toUser, "Added as buddie",
        "He/she added you as buddie");
    snCache.expire(userLogged.getUserGroup());
    snCache.expire(toUser.getUserGroup());
  }

  @Override
  public void addGroupToAdmins(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    checkGroupAddingToSelf(group, inGroup);
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    checkGroupIsNotAlreadyAMember(group, sn);
    sn.addAdmin(group);
    if (sn.isPendingCollab(group)) {
      sn.removePendingCollaborator(group);
    }
    notifyService.notifyGroupMembers(group, inGroup, "Added as administrator",
        "You are now admin of this group");
    snCache.expire(group);
    snCache.expire(inGroup);
  }

  @Override
  public void addGroupToCollabs(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    checkGroupAddingToSelf(group, inGroup);
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    checkGroupIsNotAlreadyAMember(group, sn);
    sn.addCollaborator(group);
    if (sn.isPendingCollab(group)) {
      sn.removePendingCollaborator(group);
    }
    notifyService.notifyGroupMembers(group, inGroup, "Added as collaborator",
        "You are now a collaborator of this group");
    snCache.expire(group);
    snCache.expire(inGroup);
  }

  @Override
  public void addGroupToViewers(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    checkGroupAddingToSelf(group, inGroup);
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    checkGroupIsNotAlreadyAMember(group, sn);
    sn.addViewer(group);
    if (sn.isPendingCollab(group)) {
      sn.removePendingCollaborator(group);
    }
    snCache.expire(group);
    snCache.expire(inGroup);
  }

  private void checkGroupAddingToSelf(final Group group, final Group inGroup) throws DefaultException {
    if (group.equals(inGroup)) {
      throwGroupMemberException(group);
    }
  }

  private void checkGroupIsNotAlreadyAMember(final Group group, final SocialNetwork sn)
      throws DefaultException {
    if (sn.isAdmin(group) || sn.isCollab(group) || sn.isViewer(group) && notEveryOneCanView(sn)) {
      throwGroupMemberException(group);
    }
  }

  private void checkUserLoggedIsAdmin(final User userLogged, final SocialNetwork sn)
      throws AccessViolationException {
    if (!accessRightsService.get(userLogged, sn.getAccessLists()).isAdministrable()) {
      throw new AccessViolationException();
    }
  }

  @Override
  public void deleteMember(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException, AccessViolationException {
    final SocialNetwork sn = inGroup.getSocialNetwork();

    checkUserLoggedIsAdmin(userLogged, sn);
    unJoinGroup(group, inGroup);
    notifyService.notifyGroupMembers(group, inGroup, "Removed as collaborator",
        "You have been removed as collaborator of this group");
    snCache.expire(group);
    snCache.expire(inGroup);
  }

  @Override
  public void denyJoinGroup(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    final Set<Group> pendingCollabs = sn.getPendingCollaborators().getList();
    if (pendingCollabs.contains(group)) {
      sn.removePendingCollaborator(group);
      notifyService.notifyGroupMembers(group, inGroup, "Membership denied",
          "Your membership to this group has been rejected");
    } else {
      throw new DefaultException("Person/Group is not a pending collaborator");
    }
    snCache.expire(group);
    snCache.expire(inGroup);
  }

  @Override
  public ParticipationData findParticipation(final User userLogged, final Group group)
      throws AccessViolationException {
    get(userLogged, group); // check access
    final Long groupId = group.getId();
    final Set<Group> adminInGroups = finder.findAdminInGroups(groupId);
    // Don't show self user group
    if (group.isPersonal()) {
      adminInGroups.remove(group);
    }
    // adminInGroups.remove(userLogged.getUserGroup());
    final Set<Group> collabInGroups = finder.findCollabInGroups(groupId);
    return new ParticipationData(adminInGroups, collabInGroups);
  }

  @Override
  public List<Group> findParticipationAggregated(final User userLogged, final Group group)
      throws AccessViolationException {
    get(userLogged, group); // check access
    final Long groupId = group.getId();
    final List<Group> groups = finder.findParticipatingInGroups(groupId);
    // Don't show self user group
    if (group.isPersonal()) {
      groups.remove(group);
    }
    return groups;
  }

  @Override
  public SocialNetworkDataDTO generateResponse(final User userLogged, final Group group) {
    return mapper.map(getSocialNetworkData(userLogged, group), SocialNetworkDataDTO.class);
  }

  @Override
  public SocialNetwork get(final User petitioner, final Group group) throws AccessViolationException {
    final SocialNetwork sn = group.getSocialNetwork();
    if (!sn.getAccessLists().getViewers().includes(petitioner.getUserGroup())
        && !sn.getAccessLists().getEditors().includes(petitioner.getUserGroup())
        && !sn.getAccessLists().getAdmins().includes(petitioner.getUserGroup())) {
      throw new AccessViolationException();
    }
    return sn;
  }

  @Override
  public SocialNetworkData getSocialNetworkData(final User userLogged, final Group group) {
    final SocialNetworkData socialNetData = new SocialNetworkData();
    socialNetData.setGroupMembers(get(userLogged, group));

    final SocialNetworkData cached = snCache.get(userLogged, group);
    if (cached != null) {
      LOG.debug("Returning cached SN");
      return cached;
    }
    final AccessRights groupRights = accessRightsService.get(userLogged, group.getAccessLists());
    socialNetData.setGroupRights(groupRights);
    socialNetData.setUserParticipation(findParticipation(userLogged, group));
    // socialNetData.setGroupMembers(get(userLogged, group));
    if (group.isPersonal()) {
      final UserBuddiesData userBuddies = userManager.getUserBuddies(group.getShortName());
      final User userGroup = userManager.findByShortname(group.getShortName());
      socialNetData.setUserBuddies(userBuddies);
      final UserSNetVisibility buddiesVisibility = userGroup.getSNetVisibility();
      socialNetData.setIsBuddiesVisible(true);
      switch (buddiesVisibility) {
      case anyone:
        break;
      case onlyyou:
        if (userLogged.equals(User.UNKNOWN_USER) || !userLogged.getUserGroup().equals(group)) {
          socialNetData.setIsBuddiesVisible(false);
          socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
        }
        break;
      case yourbuddies:
        final boolean notMe = !userLogged.equals(userGroup);
        final boolean notABuddie = !userBuddies.contains(userLogged.getShortName());
        if (notMe && notABuddie) {
          socialNetData.setIsBuddiesVisible(false);
          socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
        }
        break;
      }
      socialNetData.setUserBuddiesVisibility(buddiesVisibility);
    } else {
      final SocialNetworkVisibility visibility = group.getSocialNetwork().getVisibility();
      socialNetData.setIsMembersVisible(true);
      switch (visibility) {
      case anyone:
        break;
      case onlyadmins:
        if (!groupRights.isAdministrable()) {
          socialNetData.setIsMembersVisible(false);
          socialNetData.setGroupMembers(SocialNetwork.EMPTY);
        }
        break;
      case onlymembers:
        if (!groupRights.isEditable()) {
          socialNetData.setIsMembersVisible(false);
          socialNetData.setGroupMembers(SocialNetwork.EMPTY);
        }
        break;
      }
      socialNetData.setSocialNetworkVisibility(visibility);
      socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
    }
    snCache.put(userLogged, group, socialNetData);
    return socialNetData;
  }

  private boolean isClosed(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Closed);
  }

  private boolean isModerated(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Moderated);
  }

  private boolean isOpen(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Open);
  }

  private boolean notEveryOneCanView(final SocialNetwork sn) {
    return !sn.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE);
  }

  @Override
  public SocialNetworkRequestResult requestToJoin(final User userLogged, final Group inGroup)
      throws DefaultException, UserMustBeLoggedException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    if (!User.isKnownUser(userLogged)) {
      throw new UserMustBeLoggedException();
    }
    final AdmissionType admissionType = inGroup.getAdmissionType();
    if (admissionType == null) {
      throw new ServerException("No admissionType");
    }
    final Group userGroup = userLogged.getUserGroup();
    checkGroupIsNotAlreadyAMember(userGroup, sn);
    if (isModerated(admissionType)) {
      sn.addPendingCollaborator(userGroup);
      notifyService.notifyGroupAdmins(userGroup, inGroup, "Pending collaborator",
          "There is a pending collaborator in this group. Please accept or deny him/her");
      snCache.expire(userGroup);
      snCache.expire(inGroup);
      return SocialNetworkRequestResult.moderated;
    } else if (isOpen(admissionType)) {
      if (inGroup.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
        sn.addAdmin(userGroup);
        inGroup.setGroupType(GroupType.PROJECT);
        inGroup.setAdmissionType(AdmissionType.Moderated);
        persist(inGroup, Group.class);
      } else {
        sn.addCollaborator(userGroup);
      }
      snCache.expire(userGroup);
      snCache.expire(inGroup);
      return SocialNetworkRequestResult.accepted;
    } else if (isClosed(admissionType)) {
      return SocialNetworkRequestResult.denied;
    } else {
      throw new DefaultException("State not expected in SocialNetworkManagerDefault class");
    }
  }

  @Override
  public void setAdminAsCollab(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    if (sn.isAdmin(group)) {
      if (sn.getAccessLists().getAdmins().getList().size() == 1) {
        throw new LastAdminInGroupException();
      }
      sn.removeAdmin(group);
      sn.addCollaborator(group);
      notifyService.notifyGroupMembers(group, inGroup, "Membership changed",
          "Your membership to this group has changed. You are now a collaborator of this group");
      snCache.expire(group);
      snCache.expire(inGroup);
    } else {
      throw new InvalidSNOperationException("Person/Group is not an admin");
    }
  }

  @Override
  public void setCollabAsAdmin(final User userLogged, final Group group, final Group inGroup)
      throws DefaultException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    checkUserLoggedIsAdmin(userLogged, sn);
    if (sn.isCollab(group)) {
      notifyService.notifyGroupMembers(group, inGroup, "Membership changed",
          "Your membership to this group has changed. You are now an administrator of this group");
      snCache.expire(group);
      snCache.expire(inGroup);
      sn.removeCollaborator(group);
      sn.addAdmin(group);
    } else {
      throw new InvalidSNOperationException("Person/Group is not a collaborator");
    }
  }

  private void throwGroupMemberException(final Group group) throws DefaultException {
    if (group.isPersonal()) {
      throw new AlreadyUserMemberException();
    } else {
      throw new AlreadyGroupMemberException();
    }
  }

  @Override
  public void unJoinGroup(final Group groupToUnJoin, final Group inGroup) throws DefaultException {
    final SocialNetwork sn = inGroup.getSocialNetwork();
    if (sn.isAdmin(groupToUnJoin)) {
      if (sn.getAccessLists().getAdmins().getList().size() == 1) {
        if (sn.getAccessLists().getEditors().getList().size() > 0) {
          throw new LastAdminInGroupException();
        } else {
          inGroup.setGroupType(GroupType.ORPHANED_PROJECT);
          inGroup.setAdmissionType(AdmissionType.Open);
        }
      }
      sn.removeAdmin(groupToUnJoin);
    } else if (sn.isCollab(groupToUnJoin)) {
      sn.removeCollaborator(groupToUnJoin);
    } else {
      throw new DefaultException("Person/Group is not a collaborator");
    }
    notifyService.notifyGroupAdmins(groupToUnJoin, inGroup, "A member left this group",
        "A member has left this group");
    snCache.expire(groupToUnJoin);
    snCache.expire(inGroup);
  }

}
