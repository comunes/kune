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
package cc.kune.core.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionRegistryByTypeTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ActionRegistryByTypeTest {

  /** The Constant OTHERACTIONGROUP. */
  private static final String OTHERACTIONGROUP = "otheractiongroup";

  /** The Constant SOMEACTIONGROUP. */
  private static final String SOMEACTIONGROUP = "someactiongroup";

  /** The Constant TOOL. */
  private static final String TOOL = "tool";

  /** The action. */
  private GuiActionDescrip action;

  /** The action registry by type. */
  private ActionRegistryByType actionRegistryByType;

  /** The all rights. */
  private AccessRights allRights;

  /**
   * Before.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Before
  public void before() {
    actionRegistryByType = new ActionRegistryByType();
    action = Mockito.mock(GuiActionDescrip.class);
    allRights = new AccessRights(true, true, true);
    actionRegistryByType.addAction(TOOL, SOMEACTIONGROUP, new Provider() {
      @Override
      public Object get() {
        return action;
      }
    });
  }

  /**
   * Test dont get current actions of other group.
   */
  @Test
  public void testDontGetCurrentActionsOfOtherGroup() {
    assertEquals(0,
        actionRegistryByType.getCurrentActions(TOOL, null, false, allRights, OTHERACTIONGROUP).size());
    assertEquals(
        0,
        actionRegistryByType.getCurrentActions(TOOL + "other", null, false, allRights, OTHERACTIONGROUP).size());
  }

  /**
   * Test get current actions of group.
   */
  @Test
  public void testGetCurrentActionsOfGroup() {
    assertEquals(1,
        actionRegistryByType.getCurrentActions(TOOL, null, false, allRights, SOMEACTIONGROUP).size());
  }
}
