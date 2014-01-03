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
import cc.kune.core.server.mapper.KuneMapper;
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

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements
    SocialNetworkManager {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(SocialNetworkManagerDefault.class);

  /** The access rights service. */
  private final AccessRightsService accessRightsService;

  /** The finder. */
  private final GroupFinder finder;

  /** The mapper. */
  private final KuneMapper mapper;

  /** The notify service. */
  private final NotificationService notifyService;

  /** The sn cache. */
  private final SocialNetworkCache snCache;

  /** The user manager. */
  private final UserManager userManager;

  /**
   * Instantiates a new social network manager default.
   * 
   * @param provider
   *          the provider
   * @param finder
   *          the finder
   * @param accessRightsService
   *          the access rights service
   * @param mapper
   *          the mapper
   * @param userManager
   *          the user manager
   * @param snCache
   *          the sn cache
   * @param notifyService
   *          the notify service
   */
  @Inject
  public SocialNetworkManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final GroupFinder finder, final AccessRightsService accessRightsService, final KuneMapper mapper,
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#acceptJoinGroup(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /**
   * Adds the admin.
   * 
   * @param newAdmin
   *          the new admin
   * @param group
   *          the group
   */
  void addAdmin(final User newAdmin, final Group group) {
    final SocialNetwork sn = group.getSocialNetwork();
    sn.addAdmin(newAdmin.getUserGroup());
    snCache.expire(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#addAsBuddie(cc.kune.domain
   * .User, cc.kune.domain.User)
   */
  @Override
  public void addAsBuddie(final User userLogged, final User toUser) {
    notifyService.notifyUserToUserByEmail(userLogged, toUser, "Added as buddie",
        "He/she added you as buddie");
    snCache.expire(userLogged.getUserGroup());
    snCache.expire(toUser.getUserGroup());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#addGroupToAdmins(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#addGroupToCollabs(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#addGroupToViewers(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /**
   * Check group adding to self.
   * 
   * @param group
   *          the group
   * @param inGroup
   *          the in group
   * @throws DefaultException
   *           the default exception
   */
  private void checkGroupAddingToSelf(final Group group, final Group inGroup) throws DefaultException {
    if (group.equals(inGroup)) {
      throwGroupMemberException(group);
    }
  }

  /**
   * Check group is not already a member.
   * 
   * @param group
   *          the group
   * @param sn
   *          the sn
   * @throws DefaultException
   *           the default exception
   */
  private void checkGroupIsNotAlreadyAMember(final Group group, final SocialNetwork sn)
      throws DefaultException {
    if (sn.isAdmin(group) || sn.isCollab(group) || sn.isViewer(group) && notEveryOneCanView(sn)) {
      throwGroupMemberException(group);
    }
  }

  /**
   * Check user logged is admin.
   * 
   * @param userLogged
   *          the user logged
   * @param sn
   *          the sn
   * @throws AccessViolationException
   *           the access violation exception
   */
  private void checkUserLoggedIsAdmin(final User userLogged, final SocialNetwork sn)
      throws AccessViolationException {
    if (!accessRightsService.get(userLogged, sn.getAccessLists()).isAdministrable()) {
      throw new AccessViolationException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#deleteMember(cc.kune.domain
   * .User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#denyJoinGroup(cc.kune.
   * domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#findParticipation(cc.kune
   * .domain.User, cc.kune.domain.Group)
   */
  @Override
  public ParticipationData findParticipation(final User userLogged, final Group group)
      throws AccessViolationException {
    final SocialNetwork sn = group.getSocialNetwork();
    get(userLogged, group); // check access
    final Long groupId = group.getId();
    final boolean isMember = sn.isAdmin(userLogged.getUserGroup())
        || sn.isCollab(userLogged.getUserGroup());
    final Set<Group> adminInGroups = (isMember ? finder.findAdminInGroups(groupId)
        : finder.findAdminInGroups(groupId, GroupType.CLOSED));

    // Don't show self user group
    if (group.isPersonal()) {
      adminInGroups.remove(group);
    }
    // adminInGroups.remove(userLogged.getUserGroup());
    final Set<Group> collabInGroups = isMember ? finder.findCollabInGroups(groupId)
        : finder.findCollabInGroups(groupId, GroupType.CLOSED);
    return new ParticipationData(adminInGroups, collabInGroups);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#findParticipationAggregated
   * (cc.kune.domain.User, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#generateResponse(cc.kune
   * .domain.User, cc.kune.domain.Group)
   */
  @Override
  public SocialNetworkDataDTO generateResponse(final User userLogged, final Group group) {
    return mapper.map(getSocialNetworkData(userLogged, group), SocialNetworkDataDTO.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#get(cc.kune.domain.User,
   * cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#getSocialNetworkData(cc
   * .kune.domain.User, cc.kune.domain.Group)
   */
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

  /**
   * Checks if is closed.
   * 
   * @param admissionType
   *          the admission type
   * @return true, if is closed
   */
  private boolean isClosed(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Closed);
  }

  /**
   * Checks if is moderated.
   * 
   * @param admissionType
   *          the admission type
   * @return true, if is moderated
   */
  private boolean isModerated(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Moderated);
  }

  /**
   * Checks if is open.
   * 
   * @param admissionType
   *          the admission type
   * @return true, if is open
   */
  private boolean isOpen(final AdmissionType admissionType) {
    return admissionType.equals(AdmissionType.Open);
  }

  /**
   * Not every one can view.
   * 
   * @param sn
   *          the sn
   * @return true, if successful
   */
  private boolean notEveryOneCanView(final SocialNetwork sn) {
    return !sn.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#requestToJoin(cc.kune.
   * domain.User, cc.kune.domain.Group)
   */
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
      notifyService.notifyGroupAdmins(inGroup, inGroup, "Pending collaborator",
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#setAdminAsCollab(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#setCollabAsAdmin(cc.kune
   * .domain.User, cc.kune.domain.Group, cc.kune.domain.Group)
   */
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

  /**
   * Throw group member exception.
   * 
   * @param group
   *          the group
   * @throws DefaultException
   *           the default exception
   */
  private void throwGroupMemberException(final Group group) throws DefaultException {
    if (group.isPersonal()) {
      throw new AlreadyUserMemberException();
    } else {
      throw new AlreadyGroupMemberException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.SocialNetworkManager#unJoinGroup(cc.kune.domain
   * .Group, cc.kune.domain.Group)
   */
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
