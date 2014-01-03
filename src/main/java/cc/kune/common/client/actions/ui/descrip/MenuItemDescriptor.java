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

import cc.kune.common.client.actions.AbstractAction;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuItemDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MenuItemDescriptor extends AbstractGuiActionDescrip {

  /** The ui. */
  public static String UI = "menuitem-ui";

  /**
   * Builds the.
   *
   * @param action the action
   * @return the menu item descriptor
   */
  public static MenuItemDescriptor build(final AbstractAction action) {
    return new MenuItemDescriptor(action);
  }

  /**
   * Builds the.
   *
   * @param parent the parent
   * @param action the action
   * @return the menu item descriptor
   */
  public static MenuItemDescriptor build(final MenuDescriptor parent, final AbstractAction action) {
    return new MenuItemDescriptor(parent, action);
  }

  /**
   * A simple menu item definition.
   * 
   * You must define a menu item with its parent menu. Only use this constructor
   * if you'll set the parent menu in the future (before render)
   *
   * @param action the action
   */
  public MenuItemDescriptor(final AbstractAction action) {
    super(action);
  }

  /**
   * A simple menu item definition.
   * 
   * This is the preferred and more common used constructor.
   *
   * @param parent menu
   * @param action the action
   */
  public MenuItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
    super(action);
    setParent(parent);
  }

  /**
   * A simple menu item definition.
   * 
   * This is the preferred and more common used constructor.
   *
   * @param parent menu
   * @param addToParent if yes, the action will be added directly to the parent, useful to
   * attach menu items (or other child actions) directly to its parent
   * without any other check (for instance session status, etc)
   * @param action the action
   */
  public MenuItemDescriptor(final MenuDescriptor parent, final boolean addToParent,
      final AbstractAction action) {
    super(action);
    setParent(parent, addToParent);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return MenuItemDescriptor.class;
  }
}
