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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.TestDomainHelper;
import cc.kune.core.server.testhelper.ctx.DomainContext;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.SocialNetwork;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessRightsServiceTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AccessRightsServiceTest {

  /** The access rights manager. */
  private AccessRightsServiceDefault accessRightsManager;

  /** The group1. */
  private Group group1;

  /** The group2. */
  private Group group2;

  /** The group3. */
  private Group group3;

  /**
   * Access rights should be transitive.
   */
  @Test
  public void accessRightsShouldBeTransitive() {
    final DomainContext ctx = new DomainContext();
    ctx.createUsers("user1", "user2", "user3");
    ctx.inSocialNetworkOf("user1").addAsCollaborator("user2");
    ctx.inSocialNetworkOf("user2").addAsCollaborator("user3");
    final AccessRights rights = accessRightsManager.get(ctx.getUser("user3"),
        ctx.getDefaultAccessListOf("user1"));
    assertTrue(rights.isEditable());
  }

  /**
   * Admin rights gives edit and view rights.
   */
  @Test
  public void adminRightsGivesEditAndViewRights() {
    final DomainContext ctx = new DomainContext();
    ctx.createUsers("user1", "user2");
    ctx.inSocialNetworkOf("user1").addAsAdministrator("user2");
    final AccessRights rights = accessRightsManager.get(ctx.getUser("user2"),
        ctx.getDefaultAccessListOf("user1"));
    assertTrue(rights.isAdministrable());
    assertTrue(rights.isEditable());
    assertTrue(rights.isVisible());
  }

  /**
   * Check user access rights admins and edit and view false.
   */
  @Test
  public void checkUserAccessRightsAdminsAndEditAndViewFalse() {
    final SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2,
        group3);
    final SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, group1,
        group1);
    group1.setSocialNetwork(socialNetwork);
    group2.setSocialNetwork(socialNetwork2);
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

    final AccessRights response = accessRightsManager.get(group3, accessLists);
    assertFalse(response.isAdministrable());
    assertFalse(response.isEditable());
    assertFalse(response.isVisible());
  }

  /**
   * Check user access rights admins and edit false.
   */
  @Test
  public void checkUserAccessRightsAdminsAndEditFalse() {
    final SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2,
        group3);
    final SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, group3,
        group1);
    group1.setSocialNetwork(socialNetwork);
    group2.setSocialNetwork(socialNetwork2);
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

    final AccessRights response = accessRightsManager.get(group3, accessLists);
    assertFalse(response.isAdministrable());
    assertFalse(response.isEditable());
    assertTrue(response.isVisible());
  }

  /**
   * Check user access rights admins false.
   */
  @Test
  public void checkUserAccessRightsAdminsFalse() {
    final SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2,
        group3);
    final SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group3, group3,
        group1);
    group1.setSocialNetwork(socialNetwork);
    group2.setSocialNetwork(socialNetwork2);
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

    final AccessRights response = accessRightsManager.get(group3, accessLists);
    assertFalse(response.isAdministrable());
    assertTrue(response.isEditable());
    assertTrue(response.isVisible());
  }

  /**
   * Check user access rights in sn.
   */
  @Test
  public void checkUserAccessRightsInSN() {
    final SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2,
        group3);
    final SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group3, group3, group3,
        group1);
    group1.setSocialNetwork(socialNetwork);
    group2.setSocialNetwork(socialNetwork2);
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group3, group1, group2);

    AccessRights response = accessRightsManager.get(group3, accessLists);
    assertTrue(response.isAdministrable());
    assertTrue(response.isEditable());
    assertTrue(response.isVisible());
    AccessRights responseGroup1 = accessRightsManager.get(group1, accessLists);
    assertFalse(responseGroup1.isAdministrable());
    assertTrue(responseGroup1.isEditable());
    assertTrue(responseGroup1.isVisible());
    AccessRights responseGroup2 = accessRightsManager.get(group2, accessLists);
    assertFalse(responseGroup2.isAdministrable());
    // Editable because group2 joins grupo1, and group 1 has edit perms
    assertTrue(responseGroup2.isEditable());
    assertTrue(responseGroup2.isVisible());

    final AccessLists accessLists2 = TestDomainHelper.createAccessLists(group2, group3, null);
    response = accessRightsManager.get(group3, accessLists2);
    assertTrue(response.isAdministrable());
    assertTrue(response.isEditable());
    assertTrue(response.isVisible());
    responseGroup1 = accessRightsManager.get(group1, accessLists2);
    assertFalse(responseGroup1.isAdministrable());
    assertFalse(responseGroup1.isEditable());
    assertFalse(responseGroup1.isVisible());
    responseGroup2 = accessRightsManager.get(group2, accessLists2);
    assertTrue(responseGroup2.isAdministrable());
    // Editable because group2 joins grupo1, and group 1 has edit perms
    assertTrue(responseGroup2.isEditable());
    assertTrue(responseGroup2.isVisible());
  }

  /**
   * Check user access rights view null equal to false.
   */
  @Test
  public void checkUserAccessRightsViewNullEqualToFalse() {
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, null);
    final AccessRights response = accessRightsManager.get(group3, accessLists);
    assertFalse(response.isVisible());
  }

  /**
   * Check user access rights view null equal to true.
   */
  @Test
  public void checkUserAccessRightsViewNullEqualToTrue() {
    final AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, null);
    accessLists.getViewers().setMode(GroupListMode.EVERYONE);
    final AccessRights response = accessRightsManager.get(group3, accessLists);
    assertTrue(response.isVisible());
  }

  /**
   * Inits the.
   */
  @Before
  public void init() {
    accessRightsManager = new AccessRightsServiceDefault();
    group1 = TestDomainHelper.createGroup(1);
    group2 = TestDomainHelper.createGroup(2);
    group3 = TestDomainHelper.createGroup(3);
  }

}
