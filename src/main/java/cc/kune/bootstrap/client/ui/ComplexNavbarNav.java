/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.constants.Styles;

import cc.kune.common.client.actions.ui.ParentWidget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ComplexNavbarNav extends NavbarNav implements ParentWidget {

  private Pull currentPull;

  @Override
  public void add(final UIObject uiObject) {
    final Widget widget = setPull(uiObject);
    super.add(widget);
  }

  /**
   * Adds the fill.
   *
   * @return the widget
   */
  public Widget addFill() {
    currentPull = Pull.RIGHT; // From now, all widgets to the right
    return new Label(""); // return an empty widget;
  }

  /**
   * Adds the separator.
   *
   * @return the widget
   */
  public Widget addSeparator() {
    final Label separator = new Label("");
    setPull(separator);
    this.add(separator);
    return separator;
  }

  /**
   * Adds the spacer.
   *
   * @return the widget
   */
  public Widget addSpacer() {
    final Label separator = new Label(" ");
    setPull(separator);
    this.add(separator);
    return separator;
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    setPull(uiObject);
    super.insert((Widget) uiObject, position);
  }

  private Widget setPull(final UIObject uiObject) {
    final Widget widget = (Widget) uiObject;
    if (Pull.RIGHT.equals(currentPull)) {
      widget.addStyleName(Styles.PULL_RIGHT);
    }
    return widget;
  }

}
