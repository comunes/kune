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
package cc.kune.common.client.actions;

public class ToolbarStyles {
  public static final String CSS_BTN_ALONE = "k-button,  k-button-alone, k-fl";
  public static final String CSS_BTN_CENTER = "k-button, k-button-center, k-fl";
  public static final String CSS_BTN_LEFT = "k-button, k-button-left, k-fl";
  public static final String CSS_BTN_RIGTH = "k-button, k-button-right, k-fl";

  public static String calculateStyle(final int pos, final int length) {
    // GWT.log("PATH pos: " + pos + " length: " + length);
    if (length == 1) {
      return ToolbarStyles.CSS_BTN_ALONE;
    }
    if (pos == 0) {
      return ToolbarStyles.CSS_BTN_LEFT;
    }
    if (pos == length - 1) {
      return ToolbarStyles.CSS_BTN_RIGTH;
    }
    return ToolbarStyles.CSS_BTN_CENTER;
  }

}
