/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MenuDescriptor extends AbstractParentGuiActionDescrip {

  /** The Constant MENU_CLEAR. */
  public static final String MENU_CLEAR = "menuclear";
  
  /** The Constant MENU_HIDE. */
  public static final String MENU_HIDE = "hidemenu";
  
  /** The Constant MENU_ONHIDE. */
  public static final String MENU_ONHIDE = "menuonhide";
  
  /** The Constant MENU_ONSHOW. */
  public static final String MENU_ONSHOW = "menuonshow";
  
  /** The Constant MENU_RIGHTICON. */
  public static final String MENU_RIGHTICON = "menurighticon";
  
  /** The Constant MENU_SELECT_ITEM. */
  public static final String MENU_SELECT_ITEM = "menuselitem";
  
  /** The Constant MENU_SELECTION_DOWN. */
  public static final String MENU_SELECTION_DOWN = "menuseldown";
  
  /** The Constant MENU_SELECTION_UP. */
  public static final String MENU_SELECTION_UP = "menuselup";
  
  /** The Constant MENU_SHOW. */
  public static final String MENU_SHOW = "showmenu";
  
  /** The Constant MENU_SHOW_NEAR_TO. */
  public static final String MENU_SHOW_NEAR_TO = "showmenunearto";
  
  /** The Constant MENU_STANDALONE. */
  protected static final String MENU_STANDALONE = "menustandalone";

  /**
   * Instantiates a new menu descriptor.
   */
  public MenuDescriptor() {
    this(new BaseAction(null, null));
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param action the action
   */
  public MenuDescriptor(final AbstractAction action) {
    this(NO_PARENT, action);
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param parent the parent
   * @param action the action
   */
  public MenuDescriptor(final GuiActionDescrip parent, final AbstractAction action) {
    super(action);
    setParent(parent);
    putValue(MENU_HIDE, false);
    putValue(MENU_SHOW, false);
    putValue(MENU_CLEAR, false);
    putValue(MENU_STANDALONE, false);
    putValue(MENU_SELECTION_DOWN, false);
    putValue(MENU_SELECTION_UP, false);
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param text the text
   */
  public MenuDescriptor(final String text) {
    this(new BaseAction(text, null));
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param text the text
   * @param icon the icon
   */
  public MenuDescriptor(final String text, final ImageResource icon) {
    this(new BaseAction(text, null, icon));
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   */
  public MenuDescriptor(final String text, final String tooltip) {
    this(new BaseAction(text, tooltip));
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  /**
   * Instantiates a new menu descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public MenuDescriptor(final String text, final String tooltip, final String icon) {
    this(new BaseAction(text, tooltip, icon));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.AbstractParentGuiActionDescrip#clear()
   */
  @Override
  public void clear() {
    toggle(MENU_CLEAR);
    super.clear();
  }

  /**
   * Gets the right icon.
   *
   * @return the right icon
   */
  public ImageResource getRightIcon() {
    return (ImageResource) super.getValue(MENU_RIGHTICON);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return MenuDescriptor.class;
  }

  /**
   * Hide.
   */
  public void hide() {
    toggle(MENU_HIDE);
  }

  /**
   * Checks if is standalone.
   *
   * @return true, if is standalone
   */
  public boolean isStandalone() {
    return (Boolean) super.getValue(MENU_STANDALONE);
  }

  /**
   * Move selection down.
   */
  public void moveSelectionDown() {
    toggle(MENU_SELECTION_DOWN);
  }

  /**
   * Move selection up.
   */
  public void moveSelectionUp() {
    toggle(MENU_SELECTION_UP);
  }

  /**
   * Select menu.
   *
   * @param item the item
   */
  public void selectMenu(final MenuItemDescriptor item) {
    putValue(MENU_SELECT_ITEM, item);
  }

  /**
   * Sets the menu position.
   *
   * @param position the new menu position
   */
  public void setMenuPosition(final Position position) {
    putValue(MENU_SHOW_NEAR_TO, position);
  }

  /**
   * Sets the right icon.
   *
   * @param icon the new right icon
   */
  public void setRightIcon(final ImageResource icon) {
    putValue(MENU_RIGHTICON, icon);
  }

  /**
   * Sets the standalone property (if the menu should have button (for a
   * toolbar) or is a menu independent.
   * 
   * @param standalone
   *          the new standalone
   */
  public void setStandalone(final boolean standalone) {
    putValue(MENU_STANDALONE, standalone);
  }

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(final String text) {
    putValue(Action.NAME, text);
  }

  /**
   * Show the menu (remember to set the menu position before).
   */
  public void show() {
    toggle(MENU_SHOW);
  }

  /**
   * Show the menu near the Element object specified.
   *
   * @param object the element to show menu near of it
   */
  public void show(final Object object) {
    putValue(MENU_SHOW_NEAR_TO, object);
    show();
  }

  /**
   * Show the menu near the position specified.
   *
   * @param position the position to show menu near of it
   */
  public void show(final Position position) {
    setMenuPosition(position);
    show();
  }

  /**
   * Show the menu near the Element id specified.
   *
   * @param id the element id to show menu near of it
   */
  public void show(final String id) {
    putValue(MENU_SHOW_NEAR_TO, id);
    show();
  }
}
