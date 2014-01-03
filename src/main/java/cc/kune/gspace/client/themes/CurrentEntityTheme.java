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
package cc.kune.gspace.client.themes;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentEntityTheme.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CurrentEntityTheme {

  /** The back colors. */
  private static String[] backColors = new String[8];

  /** The colors. */
  private static String[] colors = new String[8];

  /**
   * Filter.
   * 
   * @param color
   *          the color
   * @return the string
   */
  private static String filter(final String color) {
    return color == null ? "#FFF" : color;
  }

  /**
   * Gets the back color.
   * 
   * @param number
   *          the number
   * @return the back color
   */
  public static String getBackColor(final int number) {
    return filter(backColors[number]);
  }

  /**
   * Gets the color.
   * 
   * @param number
   *          the number
   * @return the color
   */
  public static String getColor(final int number) {
    return filter(colors[number]);
  }

  /**
   * Sets the colors.
   * 
   * @param colors
   *          the colors
   * @param backColors
   *          the back colors
   */
  public static void setColors(final String[] colors, final String[] backColors) {
    CurrentEntityTheme.colors = colors;
    CurrentEntityTheme.backColors = backColors;
  }

  /**
   * Instantiates a new current entity theme.
   */
  public CurrentEntityTheme() {
  }
}
