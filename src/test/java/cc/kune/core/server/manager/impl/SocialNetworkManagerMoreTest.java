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

import static org.junit.Assert.*;

import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.AlreadyGroupMemberException;
import cc.kune.core.client.errors.LastAdminInGroupException;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.domain.ParticipationData;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkManagerMoreTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkManagerMoreTest extends AbstractSocialNetworkManagerTest {

  /** The social network manager. */
  @Inject
  protected SocialNetworkManagerDefault socialNetworkManager;

  /**
   * Last admin unjoin group with collabs fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = LastAdminInGroupException.class)
  public void lastAdminUnjoinGroupWithCollabsFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.unJoinGroup(admin.getUserGroup(), group);
  }

  /**
   * Last admin unjoin group without collabs orphaned.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void lastAdminUnjoinGroupWithoutCollabsOrphaned() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.unJoinGroup(admin.getUserGroup(), group);
    assertEquals(GroupType.ORPHANED_PROJECT, group.getGroupType());
    assertEquals(0, group.getSocialNetwork().getAccessLists().getAdmins().getList().size());
    assertEquals(0, group.getSocialNetwork().getAccessLists().getEditors().getList().size());
  }

  /**
   * Not admin try delete member.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AccessViolationException.class)
  public void notAdminTryDeleteMember() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.deleteMember(otherUser, userGroup, group);
  }

  /**
   * Participation dont show self group.
   */
  @Test
  public void participationDontShowSelfGroup() {
    group.getSocialNetwork().getAccessLists().getViewers().setMode(GroupListMode.EVERYONE);
    user.getUserGroup().getSocialNetwork().getAccessLists().getViewers().setMode(GroupListMode.EVERYONE);
    assertEquals(GroupListMode.EVERYONE,
        group.getSocialNetwork().getAccessLists().getViewers().getMode());
    ParticipationData part = socialNetworkManager.findParticipation(User.UNKNOWN_USER, group);
    assertFalse(part.getGroupsIsAdmin().contains(group));
    assertFalse(part.getGroupsIsCollab().contains(group));

    socialNetworkManager.addAdmin(admin, group);
    part = socialNetworkManager.findParticipation(admin, group);
    assertFalse(part.getGroupsIsAdmin().contains(group));
    assertFalse(part.getGroupsIsCollab().contains(group));

    socialNetworkManager.addAdmin(user, user.getUserGroup());
    part = socialNetworkManager.findParticipation(User.UNKNOWN_USER, user.getUserGroup());
    assertFalse(part.getGroupsIsAdmin().contains(user.getUserGroup()));
    assertFalse(part.getGroupsIsCollab().contains(user.getUserGroup()));
  }

  /**
   * Request join a closed group deny.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void requestJoinAClosedGroupDeny() throws Exception {
    group.setAdmissionType(AdmissionType.Closed);

    final SocialNetworkRequestResult result = socialNetworkManager.requestToJoin(user, group);
    assertEquals(result, SocialNetworkRequestResult.denied);
  }

  /**
   * Request join a moderated group add user group to pending.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void requestJoinAModeratedGroupAddUserGroupToPending() throws Exception {
    group.setAdmissionType(AdmissionType.Moderated);

    final SocialNetworkRequestResult result = socialNetworkManager.requestToJoin(user, group);
    assertEquals(result, SocialNetworkRequestResult.moderated);
    assertTrue(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
  }

  /**
   * Request join a open group add user group to editors.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void requestJoinAOpenGroupAddUserGroupToEditors() throws Exception {
    group.setAdmissionType(AdmissionType.Open);

    final SocialNetworkRequestResult result = socialNetworkManager.requestToJoin(user, group);
    assertEquals(result, SocialNetworkRequestResult.accepted);
    assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
  }

  /**
   * Request join a orphaned group add user group to admins.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void requestJoinAOrphanedGroupAddUserGroupToAdmins() throws Exception {
    orphanedGroup.setAdmissionType(AdmissionType.Open);

    final SocialNetworkRequestResult result = socialNetworkManager.requestToJoin(user, orphanedGroup);
    assertEquals(result, SocialNetworkRequestResult.accepted);
    assertTrue(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getList().contains(
        userGroup));
    assertEquals(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getMode(),
        GroupListMode.NORMAL);

    // FIXME Check change group type to PROJECT
  }

  /**
   * Request join twice a orphaned group add user group to admins.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AlreadyGroupMemberException.class)
  public void requestJoinTwiceAOrphanedGroupAddUserGroupToAdmins() throws Exception {
    orphanedGroup.setAdmissionType(AdmissionType.Open);

    final SocialNetworkRequestResult result = socialNetworkManager.requestToJoin(user, orphanedGroup);
    assertEquals(SocialNetworkRequestResult.accepted, result);
    socialNetworkManager.requestToJoin(user, orphanedGroup);
  }

  /**
   * Request to join twice dont duplicate pending.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void requestToJoinTwiceDontDuplicatePending() throws Exception {
    group.setAdmissionType(AdmissionType.Moderated);

    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.requestToJoin(user, group);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 1);
  }

  /**
   * Sets the admin anon member fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void setAdminAnonMemberFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.setAdminAsCollab(admin, userGroup, group);
  }

  /**
   * Sets the admin as collab.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void setAdminAsCollab() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);
    socialNetworkManager.setAdminAsCollab(admin, userGroup, group);

    assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertFalse(group.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 1);
    assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 1);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Sets the admin as collab not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AccessViolationException.class)
  public void setAdminAsCollabNotAdminFails() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.setCollabAsAdmin(otherUser, userGroup, group);
  }

  /**
   * Sets the admin not collab fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void setAdminNotCollabFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);
  }

  /**
   * Sets the collab as admin.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void setCollabAsAdmin() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);

    assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertTrue(group.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 2);
    assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NOBODY);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Sets the collab as admin not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AccessViolationException.class)
  public void setCollabAsAdminNotAdminFails() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    socialNetworkManager.setCollabAsAdmin(otherUser, userGroup, group);
  }

}
