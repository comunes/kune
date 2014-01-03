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
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;

import com.google.gwt.i18n.client.HasDirection.Direction;

// TODO: Auto-generated Javadoc
/**
 * The Interface GuiActionDescrip.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GuiActionDescrip {

  /** The Constant DROP_TARGET. */
  public static final String DROP_TARGET = "dropTarget";

  /** The Constant NO_PARENT. */
  public static final AbstractGuiActionDescrip NO_PARENT = new NoParentGuiActionDescriptor();

  /** The Constant NO_POSITION. */
  public static final int NO_POSITION = -1;

  /**
   * The Constant TARGET. {@link #TARGET} is used to associate a
   * {@link #AbstractGuiActionDescrip} with an object like groups, group names,
   * users, and so on, and used to execute actions against these targets
   */
  public static final String TARGET = "target";

  /** The Constant TOOGLE_TOOLTIP_VISIBLE. */
  public static final String TOOGLE_TOOLTIP_VISIBLE = "toogle-tooltip-visible";

  /** The Constant VISIBLE. */
  public static final String VISIBLE = "visibleprop";

  /**
   * Adds the condition (checked only when new content/container is evaluated in
   * a folder list, etc).
   * 
   * @param addCondition
   *          the add condition
   */
  void add(final GuiAddCondition addCondition);

  /**
   * Adds the property change listener.
   * 
   * @param listener
   *          the listener
   */
  void addPropertyChangeListener(final PropertyChangeListener listener);

  /**
   * Fire.
   * 
   * @param event
   *          the event
   */
  void fire(final ActionEvent event);

  /**
   * Gets the action.
   * 
   * @return the action
   */
  AbstractAction getAction();

  /**
   * Gets the direction.
   * 
   * @return the direction
   */
  Direction getDirection();

  /**
   * Gets the id.
   * 
   * @return the id
   */
  String getId();

  /**
   * Gets the keys.
   * 
   * @return the keys
   */
  Object[] getKeys();

  /**
   * Gets the location.
   * 
   * @return the location
   */
  String getLocation();

  /**
   * Gets the parent.
   * 
   * @return the parent
   */
  GuiActionDescrip getParent();

  /**
   * Gets the position.
   * 
   * @return the position
   */
  int getPosition();

  /**
   * Gets the property change listeners.
   * 
   * @return the property change listeners
   */
  PropertyChangeListener[] getPropertyChangeListeners();

  /**
   * Gets the target.
   * 
   * @return the target
   */
  Object getTarget();

  /**
   * Gets the type.
   * 
   * @return the type
   */
  Class<?> getType();

  /**
   * Gets the value.
   * 
   * @param key
   *          the key
   * @return the value
   */
  Object getValue(final String key);

  /**
   * Checks for target.
   * 
   * @return true, if successful
   */
  boolean hasTarget();

  /**
   * Checks if is child.
   * 
   * @return true, if is child
   */
  boolean isChild();

  /**
   * Checks if is enabled.
   * 
   * @return true, if is enabled
   */
  boolean isEnabled();

  /**
   * Checks if is rTL.
   * 
   * @return true, if is rTL
   */
  boolean isRTL();

  /**
   * Checks if is visible.
   * 
   * @return true, if is visible
   */
  boolean isVisible();

  /**
   * Must be added.
   * 
   * @return true, if successful
   */
  boolean mustBeAdded();

  /**
   * Fired when the widget is attached.
   */
  void onAttach();

  /**
   * Fired when the widget is detached.
   */
  void onDetach();

  /**
   * Put value.
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  public void putValue(final String key, final Object value);

  /**
   * Removes the property change listener.
   * 
   * @param listener
   *          the listener
   */
  void removePropertyChangeListener(final PropertyChangeListener listener);

  /**
   * Sets the drop target.
   * 
   * @param dropTarget
   *          the new drop target
   */
  void setDropTarget(DropTarget dropTarget);

  /**
   * Sets the enabled.
   * 
   * @param enabled
   *          the new enabled
   */
  void setEnabled(final boolean enabled);

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  void setId(final String id);

  /**
   * Sets the location.
   * 
   * @param location
   *          the new location
   */
  void setLocation(final String location);

  /**
   * Sets the parent.
   * 
   * @param parent
   *          the new parent
   */
  void setParent(final GuiActionDescrip parent);

  /**
   * Sets the parent.
   * 
   * @param parent
   *          the parent
   * @param addToParent
   *          if yes, the action will be added directly to the parent, useful to
   *          attach menu items (or other child actions) directly to its parent
   *          without any other check (for instance session status, etc).
   *          Default to yes
   */
  void setParent(GuiActionDescrip parent, boolean addToParent);

  /**
   * Sets the position.
   * 
   * @param position
   *          the new position
   */
  void setPosition(final int position);

  /**
   * Sets the rTL.
   * 
   * @param isRTL
   *          the new rTL
   */
  void setRTL(final boolean isRTL);

  /**
   * Sets the styles.
   * 
   * @param styles
   *          the new styles
   */
  void setStyles(final String styles);

  /**
   * Sets the target.
   * 
   * @param object
   *          the new target
   */
  void setTarget(final Object object);

  /**
   * Sets the visible.
   * 
   * @param visible
   *          the new visible
   */
  void setVisible(final boolean visible);

  /**
   * Toggle tooltip visible.
   */
  void toggleTooltipVisible();

  /**
   * Toogle visible.
   */
  void toogleVisible();

  /**
   * With action.
   * 
   * @param action
   *          the action
   * @return the gui action descrip
   */
  GuiActionDescrip withAction(AbstractAction action);

  /**
   * Adds the condition (checked only when new content/container is evaluated in
   * a folder list, etc).
   * 
   * @param addCondition
   *          the add condition
   */
  void withAddCondition(GuiAddCondition addCondition);

  /**
   * With icon.
   * 
   * @param icon
   *          the icon
   * @return the gui action descrip
   */
  GuiActionDescrip withIcon(Object icon);

  /**
   * With icon cls.
   * 
   * @param icon
   *          the icon
   * @return the gui action descrip
   */
  GuiActionDescrip withIconCls(String icon);

  /**
   * With id.
   * 
   * @param id
   *          the id
   * @return the gui action descrip
   */
  GuiActionDescrip withId(String id);

  /**
   * With parent.
   * 
   * @param parent
   *          the parent
   * @return the gui action descrip
   */
  GuiActionDescrip withParent(GuiActionDescrip parent);

  /**
   * With parent.
   * 
   * @param parent
   *          the parent
   * @param addToParent
   *          if yes, the action will be added directly to the parent, useful to
   *          attach menu items (or other child actions) directly to its parent
   *          without any other check (for instance session status, etc)
   * @return the gui action descrip
   */
  GuiActionDescrip withParent(GuiActionDescrip parent, boolean addToParent);

  /**
   * With position.
   * 
   * @param position
   *          the position
   * @return the gui action descrip
   */
  GuiActionDescrip withPosition(int position);

  /**
   * With shortcut.
   *
   * @param keystroke the keystroke
   * @param register the register
   * @return the gui action descrip
   */
  GuiActionDescrip withShortcut(KeyStroke keystroke, GlobalShortcutRegister register);

  /**
   * With shortcut.
   *
   * @param keys the keys (for instance "Ctrl+A")
   * @param register the register
   * @return the gui action descrip
   */
  GuiActionDescrip withShortcut(String keys, GlobalShortcutRegister register);

  /**
   * With styles.
   * 
   * @param styles
   *          the styles
   * @return the gui action descrip
   */
  GuiActionDescrip withStyles(String styles);

  /**
   * With text.
   * 
   * @param text
   *          the text
   * @return the gui action descrip
   */
  GuiActionDescrip withText(String text);

  /**
   * With tool tip.
   * 
   * @param tooltip
   *          the tooltip
   * @return the gui action descrip
   */
  GuiActionDescrip withToolTip(String tooltip);

  /**
   * With visible.
   * 
   * @param visible
   *          the visibility
   * @return the gui action descrip
   */
  GuiActionDescrip withVisible(boolean visible);
}
