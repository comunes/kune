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
package cc.kune.gspace.client.tool.selector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.impl.EventBusTester;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter.ToolSelectorProxy;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter.ToolSelectorView;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorPresenterTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorPresenterTest {

  /** The Constant GROUP1_NAME. */
  private static final String GROUP1_NAME = "group1";

  /** The Constant GROUP1_TOOL1. */
  private static final StateToken GROUP1_TOOL1 = new StateToken("group1.tool1");

  /** The Constant GROUP1_TOOL2. */
  private static final StateToken GROUP1_TOOL2 = new StateToken("group1.tool2");

  /** The Constant TOOL1_NAME. */
  private static final String TOOL1_NAME = "tool1";

  /** The Constant TOOL2_NAME. */
  private static final String TOOL2_NAME = "tool2";

  /** The tool selector. */
  private ToolSelectorPresenter toolSelector;

  /** The tool selector item1. */
  private ToolSelectorItem toolSelectorItem1;

  /** The tool selector item2. */
  private ToolSelectorItem toolSelectorItem2;

  /**
   * Adds the first tool must not select.
   */
  @Test
  public void addFirstToolMustNotSelect() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    Mockito.verify(toolSelectorItem1).setSelected(false);
  }

  /**
   * Adds the tool without name must give exception.
   */
  @Test(expected = RuntimeException.class)
  public void addToolWithoutNameMustGiveException() {
    toolSelector.addTool(toolSelectorItem1);
  }

  /**
   * Adds the two tools must select nothing.
   */
  @Test
  public void addTwoToolsMustSelectNothing() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.addTool(toolSelectorItem2);
    Mockito.verify(toolSelectorItem1).setSelected(false);
    Mockito.verify(toolSelectorItem2).setSelected(false);
  }

  /**
   * Adds the two tools with same name must give exception.
   */
  @Test(expected = RuntimeException.class)
  public void addTwoToolsWithSameNameMustGiveException() {
    setToolNames();
    final ToolSelectorItem toolSelectorItemCopy = Mockito.mock(ToolSelectorItem.class);
    Mockito.when(toolSelectorItemCopy.getToolShortName()).thenReturn(TOOL1_NAME);
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.addTool(toolSelectorItemCopy);
  }

  /**
   * Begin.
   */
  @Before
  public void begin() {
    final StateManager stateManager = Mockito.mock(StateManager.class);
    // final WsThemeManager themePresenter =
    // Mockito.mock(WsThemeManager.class);
    toolSelector = new ToolSelectorPresenter(new EventBusTester(), Mockito.mock(ToolSelectorView.class),
        Mockito.mock(ToolSelectorProxy.class), stateManager);
    toolSelectorItem1 = Mockito.mock(ToolSelectorItem.class);
    toolSelectorItem2 = Mockito.mock(ToolSelectorItem.class);
  }

  /**
   * Sets the state first must select.
   */
  @Test
  public void setStateFirstMustSelect() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.onToolChanged("", TOOL1_NAME, null, GROUP1_TOOL1);
    Mockito.verify(toolSelectorItem1, Mockito.times(1)).setSelected(false);
    Mockito.verify(toolSelectorItem1, Mockito.times(1)).setSelected(true);
  }

  /**
   * Sets the state with a different group must set link.
   */
  @Test
  public void setStateWithADifferentGroupMustSetLink() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.addTool(toolSelectorItem2);
    toolSelector.onGroupChanged(GROUP1_NAME);
    Mockito.verify(toolSelectorItem1, Mockito.times(1)).setGroupShortName(GROUP1_NAME);
    Mockito.verify(toolSelectorItem2, Mockito.times(1)).setGroupShortName(GROUP1_NAME);
  }

  /**
   * Sets the state with a different tool name must select and un select the
   * older.
   */
  @Test
  public void setStateWithADifferentToolNameMustSelectAndUnSelectTheOlder() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.addTool(toolSelectorItem2);
    toolSelector.onToolChanged(TOOL1_NAME, TOOL2_NAME, GROUP1_TOOL1, GROUP1_TOOL2);
    Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(false);
    Mockito.verify(toolSelectorItem1, Mockito.times(2)).setSelected(false);
    Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(true);
  }

  /**
   * Sets the state with no tool name must un select the older.
   */
  @Test
  public void setStateWithNoToolNameMustUnSelectTheOlder() {
    setToolNames();
    toolSelector.addTool(toolSelectorItem1);
    toolSelector.addTool(toolSelectorItem2);
    toolSelector.onToolChanged(TOOL1_NAME, "", GROUP1_TOOL1, null);
    Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(false);
    Mockito.verify(toolSelectorItem1, Mockito.times(2)).setSelected(false);
    Mockito.verify(toolSelectorItem2, Mockito.never()).setSelected(true);
  }

  /**
   * Sets the tool names.
   */
  public void setToolNames() {
    Mockito.when(toolSelectorItem1.getToolShortName()).thenReturn(TOOL1_NAME);
    Mockito.when(toolSelectorItem2.getToolShortName()).thenReturn(TOOL2_NAME);
  }

}
