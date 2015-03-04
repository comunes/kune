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
package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.user.client.ui.IsWidget;

public class WidgetMenuDescriptor extends MenuDescriptor {

  public static final String WIDGET = "widget_menu";

  public WidgetMenuDescriptor() {
    this(new BaseAction(null, null));
  }

  public WidgetMenuDescriptor(final AbstractAction action) {
    super(action);
    setParent(NO_PARENT);
    putValue(MENU_CLEAR, false);
  }

  public WidgetMenuDescriptor(final AbstractAction action, final IsWidget widget) {
    this(action);
    putValue(WIDGET, widget);
  }

  public WidgetMenuDescriptor(final IsWidget widget) {
    this(new BaseAction(null, null), widget);
  }

  @Override
  public void clear() {
    toggle(MENU_CLEAR);
    super.clear();
  }

  @Override
  public Class<?> getType() {
    return WidgetMenuDescriptor.class;
  }

  public IsWidget getWidget() {
    return (IsWidget) getValue(WIDGET);
  }

  public void setWidget(final IsWidget widget) {
    putValue(WIDGET, widget);
  }
}
