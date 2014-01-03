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
package cc.kune.core.server.domain;

import static cc.kune.core.shared.domain.GroupListMode.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.access.AccessRightsServiceDefault;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.GroupList;

/**
 * The Class AccessListTest make the test of {@link AccessListsDTO} table.
 */
public class AccessListTest {
  private AccessRightsServiceDefault accessRightsService;
  private AccessLists acl;
  private Group group1;
  private Group group2;
  private Group group3;

  private void configure(final GroupListMode adminMode, final GroupListMode editMode,
      final GroupListMode viewMode) {
    acl.getAdmins().setMode(adminMode);
    acl.getEditors().setMode(editMode);
    acl.getViewers().setMode(viewMode);
    acl.setList(AccessRol.Administrator, acl.getAdmins());
    acl.setList(AccessRol.Editor, acl.getEditors());
    acl.setList(AccessRol.Viewer, acl.getViewers());
  }

  @Before
  public void createList() {
    acl = new AccessLists();
    group1 = new Group("one", "group one");
    group2 = new Group("two", "group two");
    group3 = new Group("three", "group three");
    accessRightsService = new AccessRightsServiceDefault();
  }

  @Test
  public void testEverybodyEverybodyEverybody() {
    configure(EVERYONE, EVERYONE, EVERYONE);
    // acl.getAdmins().add(group1);
    verify(group1, true, true, true);
    verify(group2, true, true, true);
    verify(group3, true, true, true);
    verify(0, 0, 0);
  }

  @Test
  public void testNormalEverybodyEverybody() {
    configure(NORMAL, EVERYONE, EVERYONE);
    acl.getAdmins().add(group1);
    verify(group1, true, true, true);
    verify(group2, false, true, true);
    verify(group3, false, true, true);
    verify(1, 0, 0);
  }

  @Test
  public void testNormalNobodyNobody() {
    configure(NORMAL, NOBODY, NOBODY);
    acl.getAdmins().add(group1);
    verify(group1, true, true, true);
    verify(group2, false, false, false);
    verify(group3, false, false, false);
    verify(1, 0, 0);
  }

  @Test
  public void testNormalNobodyNormal() {
    configure(NORMAL, NOBODY, NORMAL);
    acl.getAdmins().add(group1);
    acl.getViewers().add(group2);
    verify(group1, true, true, true);
    verify(group2, false, false, true);
    verify(group3, false, false, false);
    verify(1, 0, 1);
  }

  @Test
  public void testNormalNormalEmptyEverybody() {
    configure(NORMAL, NORMAL, EVERYONE);
    acl.getAdmins().add(group1);
    verify(group1, true, true, true);
    verify(group2, false, false, true);
    verify(group3, false, false, true);
    verify(1, 0, 0);
  }

  @Test
  public void testNormalNormalEmptyNormal() {
    configure(NORMAL, NORMAL, NORMAL);
    acl.getAdmins().add(group1);
    acl.getViewers().add(group2);
    verify(group1, true, true, true);
    verify(group2, false, false, true);
    verify(group3, false, false, false);
    verify(1, 0, 1);
  }

  @Test
  public void testNormalNormalEverybody() {
    configure(NORMAL, NORMAL, EVERYONE);
    acl.getAdmins().add(group1);
    acl.getEditors().add(group2);
    verify(group1, true, true, true);
    verify(group2, false, true, true);
    verify(group3, false, false, true);
    verify(1, 1, 0);
  }

  @Test
  public void testNormalNormalNobody() {
    configure(NORMAL, NORMAL, NOBODY);
    acl.getAdmins().add(group1);
    acl.getEditors().add(group2);
    verify(group1, true, true, true);
    verify(group2, false, true, true);
    verify(group3, false, false, false);
    verify(1, 1, 0);
  }

  @Test
  public void testSequence() {
    configure(NORMAL, NORMAL, NOBODY);
    acl.getAdmins().add(group1);
    verify(1, 0, 0);
    verify(group1, true, true, true);
    verify(group2, false, false, false);
    verify(group3, false, false, false);
    acl.getEditors().add(group2);
    verify(group1, true, true, true);
    verify(group2, false, true, true);
    verify(group3, false, false, false);
    verify(1, 1, 0);
    configure(NORMAL, NOBODY, NOBODY);
    verify(group1, true, true, true);
    verify(group2, false, false, false);
    verify(group3, false, false, false);
    verify(1, 1, 0);
    configure(NORMAL, NORMAL, NOBODY);
    verify(group1, true, true, true);
    verify(group2, false, true, true);
    verify(group3, false, false, false);
    verify(1, 1, 0);
    acl.getEditors().remove(group2);
    verify(group1, true, true, true);
    verify(group2, false, false, false);
    verify(group3, false, false, false);
    verify(1, 0, 0);
  }

  private void verify(final Group group, final boolean administrable, final boolean editable,
      final boolean visible) {
    final AccessRights rights = accessRightsService.get(group, acl);
    assertEquals(administrable, rights.isAdministrable());
    assertEquals(editable, rights.isEditable());
    assertEquals(visible, rights.isVisible());
  }

  private void verify(final int admins, final int editors, final int viewers) {
    verifySize(acl.getAdmins(), admins);
    verifySize(acl.getEditors(), editors);
    verifySize(acl.getViewers(), viewers);
  }

  private void verifySize(final GroupList list, final int count) {
    assertEquals(count, list.getList().size());
  }

}
