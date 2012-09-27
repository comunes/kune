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
package cc.kune.gspace.client.viewers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FolderViewerPresenterTest {

  @Test
  public void with1() {
    assertEquals(ToolbarStyles.CSS_BTN_ALONE, ToolbarStyles.calculateStyle(0, 1));
  }

  @Test
  public void with2() {
    assertEquals(ToolbarStyles.CSS_BTN_LEFT, ToolbarStyles.calculateStyle(0, 2));
    assertEquals(ToolbarStyles.CSS_BTN_RIGTH, ToolbarStyles.calculateStyle(1, 2));
  }

  @Test
  public void with3() {
    assertEquals(ToolbarStyles.CSS_BTN_LEFT, ToolbarStyles.calculateStyle(0, 3));
    assertEquals(ToolbarStyles.CSS_BTN_CENTER, ToolbarStyles.calculateStyle(1, 3));
    assertEquals(ToolbarStyles.CSS_BTN_RIGTH, ToolbarStyles.calculateStyle(2, 3));
  }

  @Test
  public void with4() {
    assertEquals(ToolbarStyles.CSS_BTN_LEFT, ToolbarStyles.calculateStyle(0, 4));
    assertEquals(ToolbarStyles.CSS_BTN_CENTER, ToolbarStyles.calculateStyle(1, 4));
    assertEquals(ToolbarStyles.CSS_BTN_CENTER, ToolbarStyles.calculateStyle(2, 4));
    assertEquals(ToolbarStyles.CSS_BTN_RIGTH, ToolbarStyles.calculateStyle(3, 4));
  }
}
