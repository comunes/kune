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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.domain.Group;
import cc.kune.domain.GroupList;

public class GroupListTest {
  private Group includedGroup;
  private GroupList list;

  @Before
  public void createList() {
    list = new GroupList();
    includedGroup = new Group("one", "group");
    list.add(includedGroup);
  }

  @Test
  public void testModeEverybody() {
    list.setMode(GroupListMode.EVERYONE);
    assertTrue(list.includes(includedGroup));
    assertTrue(list.includes(new Group("other", "group")));
    assertTrue(list.includes(Group.NO_GROUP));
  }

  @Test
  public void testModeNobody() {
    list.setMode(GroupListMode.NOBODY);
    assertFalse(list.includes(includedGroup));
    assertFalse(list.includes(new Group("other", "group")));
    assertFalse(list.includes(Group.NO_GROUP));
  }

  @Test
  public void testModeNormal() {
    list.setMode(GroupListMode.NORMAL);
    assertTrue(list.includes(includedGroup));
    assertFalse(list.includes(new Group("other", "group")));
    assertFalse(list.includes(Group.NO_GROUP));
  }

}
