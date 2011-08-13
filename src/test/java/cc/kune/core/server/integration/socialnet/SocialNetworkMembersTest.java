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
package cc.kune.core.server.integration.socialnet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.UserSession;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class SocialNetworkMembersTest extends IntegrationTest {
  private Group group;
  @Inject
  GroupManager groupFinder;
  @Inject
  UserSession session;

  @Inject
  SocialNetworkManager socialNetManager;

  @Transactional
  @Before
  public void init() {
    new IntegrationTestHelper(this);
    group = groupFinder.findByShortName(getSiteAdminShortName());
  }

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

  @Test
  public void testAdminMembersOfGroupFinder() throws Exception {
    doLogin();
    final Set<Group> result = groupFinder.findAdminInGroups(group.getId());
    assertEquals(2, result.size());
  }

  @Test
  public void testCollabMembersOfGroupFinder() throws Exception {
    doLogin();
    final Set<Group> result = groupFinder.findCollabInGroups(group.getId());
    assertEquals(0, result.size());
  }

}
