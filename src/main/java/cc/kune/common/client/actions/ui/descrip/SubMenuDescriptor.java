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
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class SubMenuDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SubMenuDescriptor extends MenuDescriptor {

  /**
   * Builds the.
   *
   * @return the sub menu descriptor
   */
  public static SubMenuDescriptor build() {
    return new SubMenuDescriptor();
  }

  /**
   * Instantiates a new sub menu descriptor.
   */
  public SubMenuDescriptor() {
    this(new BaseAction(null, null));
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param action the action
   */
  public SubMenuDescriptor(final AbstractAction action) {
    this(NO_PARENT, action);
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param parent the parent
   * @param action the action
   */
  public SubMenuDescriptor(final GuiActionDescrip parent, final AbstractAction action) {
    super(action);
    setParent(parent);
    putValue(MENU_HIDE, false);
    putValue(MENU_SHOW, false);
    putValue(MENU_CLEAR, false);
    putValue(MENU_STANDALONE, false);
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param parent the parent
   * @param addToParent the add to parent
   * @param text the text
   */
  public SubMenuDescriptor(final GuiActionDescrip parent, final boolean addToParent, final String text) {
    this(text);
    setParent(parent, addToParent);
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param parent the parent
   * @param text the text
   */
  public SubMenuDescriptor(final GuiActionDescrip parent, final String text) {
    this(text);
    setParent(parent);
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param text the text
   */
  public SubMenuDescriptor(final String text) {
    this(new BaseAction(text, null));
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param text the text
   * @param icon the icon
   */
  public SubMenuDescriptor(final String text, final ImageResource icon) {
    this(new BaseAction(text, null, icon));
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   */
  public SubMenuDescriptor(final String text, final String tooltip) {
    this(new BaseAction(text, tooltip));
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public SubMenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  /**
   * Instantiates a new sub menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public SubMenuDescriptor(final String text, final String tooltip, final String icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.MenuDescriptor#getType()
   */
  @Override
  public Class<?> getType() {
    return SubMenuDescriptor.class;
  }

}
