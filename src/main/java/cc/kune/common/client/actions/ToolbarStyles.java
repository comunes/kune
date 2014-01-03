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
package cc.kune.common.client.actions;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolbarStyles.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolbarStyles {
  
  /** The Constant CSS_BTN_ALONE. */
  public static final String CSS_BTN_ALONE = "k-button,  k-button-alone, k-fl";
  
  /** The Constant CSS_BTN_CENTER. */
  public static final String CSS_BTN_CENTER = "k-button, k-button-center, k-fl";
  
  /** The Constant CSS_BTN_LEFT. */
  public static final String CSS_BTN_LEFT = "k-button, k-button-left, k-fl";
  
  /** The Constant CSS_BTN_RIGTH. */
  public static final String CSS_BTN_RIGTH = "k-button, k-button-right, k-fl";

  /**
   * Calculate style.
   *
   * @param pos the pos
   * @param length the length
   * @return the string
   */
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
