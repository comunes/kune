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
package cc.kune.core.server.integration.socialnet;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkMembersTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkMembersTest extends IntegrationTest {

  /** The group. */
  private Group group;

  /** The group finder. */
  @Inject
  GroupManager groupFinder;

  /** The session. */
  @Inject
  UserSessionManager session;

  /** The social net manager. */
  @Inject
  SocialNetworkManager socialNetManager;

  /**
   * Inits the.
   */
  @Transactional
  @Before
  public void init() {
    new IntegrationTestHelper(true, this);
    group = groupFinder.findByShortName(getSiteAdminShortName());
  }

  /**
   * Test admin add twice.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAdminAddTwice() throws Exception {
    doLogin();
    final Set<Group> result = groupFinder.findAdminInGroups(group.getId());
    final AccessLists acl = group.getSocialNetwork().getAccessLists();
    assertTrue(acl.getAdmins().includes(group));
    assertFalse(acl.getEditors().includes(group));
    acl.addAdmin(group);
    acl.addAdmin(group);
    acl.addAdmin(group);
    assertEquals(2, result.size());
  }

  /**
   * Test admin members of group finder.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testAdminMembersOfGroupFinder() throws Exception {
    doLogin();
    final Set<Group> result = groupFinder.findAdminInGroups(group.getId());
    assertEquals(2, result.size());
  }

  /**
   * Test collab members of group finder.
   * 
   * @throws Exception
   *           the exception
   */
  @Test
  public void testCollabMembersOfGroupFinder() throws Exception {
    doLogin();
    final Set<Group> result = groupFinder.findCollabInGroups(group.getId());
    assertEquals(0, result.size());
  }

}
