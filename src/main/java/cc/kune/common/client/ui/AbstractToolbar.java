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
package cc.kune.common.client.ui;

import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Interface AbstractToolbar.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface AbstractToolbar {

  /**
   * Adds the.
   *
   * @param widget the widget
   */
  void add(final Widget widget);

  /**
   * Adds the.
   *
   * @param widget the widget
   * @param valign the valign
   */
  void add(final Widget widget, VerticalAlignmentConstant valign);

  /**
   * Adds the fill.
   *
   * @return the widget
   */
  Widget addFill();

  /**
   * Adds the separator.
   *
   * @return the widget
   */
  Widget addSeparator();

  /**
   * Adds the spacer.
   *
   * @return the widget
   */
  Widget addSpacer();

  /**
   * Gets the offset height.
   *
   * @return the offset height
   */
  int getOffsetHeight();

  /**
   * Insert.
   *
   * @param widget the widget
   * @param position the position
   */
  void insert(final Widget widget, int position);

  /**
   * Checks if is attached.
   *
   * @return true, if is attached
   */
  boolean isAttached();

  /**
   * Removes the.
   *
   * @param widget the widget
   * @return true, if successful
   */
  boolean remove(final Widget widget);

  /**
   * Removes the all.
   */
  void removeAll();

  /**
   * Sets the blank style.
   */
  void setBlankStyle();

  /**
   * Sets the height.
   *
   * @param height the new height
   */
  void setHeight(String height);

  /**
   * Sets the normal style.
   */
  void setNormalStyle();

}
