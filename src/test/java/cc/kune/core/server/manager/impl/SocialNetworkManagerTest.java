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
import cc.kune.core.shared.domain.GroupListMode;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkManagerTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkManagerTest extends AbstractSocialNetworkManagerTest {

  /** The social network manager. */
  @Inject
  protected SocialNetworkManagerDefault socialNetworkManager;

  /**
   * Accept join group.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void acceptJoinGroup() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);

    assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 1);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Accept join not pending group fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void acceptJoinNotPendingGroupFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
  }

  /**
   * Adds the admin as admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AlreadyGroupMemberException.class)
  public void addAdminAsAdminFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.addGroupToAdmins(admin, admin.getUserGroup(), group);
  }

  /**
   * Adds the admin as collab fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AlreadyGroupMemberException.class)
  public void addAdminAsCollabFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.addGroupToCollabs(admin, admin.getUserGroup(), group);
  }

  /**
   * Adds the admin as viewer fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AlreadyGroupMemberException.class)
  public void addAdminAsViewerFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.addGroupToViewers(admin, admin.getUserGroup(), group);
  }

  /**
   * Adds the admin not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void addAdminNotAdminFails() throws Exception {
    socialNetworkManager.addGroupToAdmins(otherUser, userGroup, group);
  }

  /**
   * Adds the collab not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void addCollabNotAdminFails() throws Exception {
    socialNetworkManager.addGroupToCollabs(otherUser, userGroup, group);
  }

  /**
   * Adds the pending as collab directly.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void addPendingAsCollabDirectly() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addGroupToCollabs(admin, userGroup, group);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Adds the pending as collab directly as admin.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void addPendingAsCollabDirectlyAsAdmin() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addGroupToAdmins(admin, userGroup, group);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Adds the viewer not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void addViewerNotAdminFails() throws Exception {
    socialNetworkManager.addGroupToViewers(otherUser, userGroup, group);
  }

  /**
   * Delete member.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void deleteMember() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    socialNetworkManager.deleteMember(admin, userGroup, group);
    assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NOBODY);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Delete not member fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void deleteNotMemberFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.deleteMember(admin, userGroup, group);
  }

  /**
   * Deny join group.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void denyJoinGroup() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.denyJoinGroup(admin, userGroup, group);

    assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Deny join group not admin fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = AccessViolationException.class)
  public void denyJoinGroupNotAdminFails() throws Exception {
    socialNetworkManager.requestToJoin(user, group);
    socialNetworkManager.denyJoinGroup(otherUser, userGroup, group);
  }

  /**
   * Deny not pending fails.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = Exception.class)
  public void denyNotPendingFails() throws Exception {
    socialNetworkManager.addAdmin(admin, group);
    socialNetworkManager.denyJoinGroup(admin, userGroup, group);
  }

  /**
   * Ilegal admission type.
   * 
   * @throws Exception
   *           the exception
   */
  @Test(expected = RuntimeException.class)
  public void ilegalAdmissionType() throws Exception {
    group.setAdmissionType(null);
    socialNetworkManager.requestToJoin(user, group);
  }

}
