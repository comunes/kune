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
package cc.kune.common.client.actions.ui;

import com.google.gwt.user.client.ui.UIObject;

/**
 * The Interface ParentWidget.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ParentWidget {

  /**
   * The parent ui used to add a child like menu items. Useful when the parent
   * widget to add a child is, for instance, a part of the parent widget
   */
  String PARENT_UI = "PARENT_UI";

  /**
   * Adds to the parent.
   *
   * @param uiObject
   *          the ui object
   */
  public void add(UIObject uiObject);

  /**
   * Insert in to the parent.
   *
   * @param position
   *          the position
   *
   * @param uiObject
   *          the ui object
   */
  public void insert(final int position, final UIObject uiObject);
}
