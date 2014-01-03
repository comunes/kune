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
package cc.kune.common.client.actions.ui.descrip;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGuiActionDescripTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractGuiActionDescripTest {

  /** The add false cond. */
  private GuiAddCondition addFalseCond;

  /** The add true cond1. */
  private GuiAddCondition addTrueCond1;

  /** The add true cond2. */
  private GuiAddCondition addTrueCond2;

  /** The add true cond3. */
  private GuiAddCondition addTrueCond3;

  /** The descriptor. */
  private MenuDescriptor descriptor;

  /**
   * Before.
   */
  @Before
  public void before() {
    descriptor = new MenuDescriptor();
    addTrueCond1 = Mockito.mock(GuiAddCondition.class);
    addTrueCond2 = Mockito.mock(GuiAddCondition.class);
    addTrueCond3 = Mockito.mock(GuiAddCondition.class);
    addFalseCond = Mockito.mock(GuiAddCondition.class);
    Mockito.stub(addTrueCond1.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
    Mockito.stub(addTrueCond2.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
    Mockito.stub(addTrueCond3.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
    Mockito.stub(addFalseCond.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(false);
  }

  /**
   * Onffalse add conditions should dont be added.
   */
  @Test
  public void onffalseAddConditionsShouldDontBeAdded() {
    descriptor.add(addTrueCond1);
    descriptor.add(addTrueCond2);
    descriptor.add(addFalseCond);
    assertFalse(descriptor.mustBeAdded());
  }

  /**
   * Two true add conditions must be added.
   */
  @Test
  public void twoTrueAddConditionsMustBeAdded() {
    descriptor.add(addTrueCond1);
    descriptor.add(addTrueCond2);
    descriptor.add(addTrueCond3);
    assertTrue(descriptor.mustBeAdded());
  }

  /**
   * Without add conditions must be added.
   */
  @Test
  public void withoutAddConditionsMustBeAdded() {
    assertTrue(descriptor.mustBeAdded());
  }
}
