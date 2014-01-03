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

import java.util.ArrayList;
import java.util.List;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ChangeableObject;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;

import com.google.gwt.i18n.client.HasDirection.Direction;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUIActionDescriptor.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGuiActionDescrip extends ChangeableObject implements GuiActionDescrip {

  /** The action. */
  private AbstractAction action;

  /** The add conditions. */
  private final List<GuiAddCondition> addConditions;

  /** The item DOM id. */
  private String id;

  /** If is a Rigth-to-Left widget (for languages like Arabic). */
  private boolean isRTL = false;

  /** The item location. */
  private String location;

  /** The parent. */
  protected GuiActionDescrip parent = NO_PARENT;

  /** The position where the item will be inserted. */
  private int position;

  /**
   * Instantiates a new abstract ui action descriptor. This is used for describe
   * UI button, menus, menu items and so on
   * 
   * @param action
   *          the action
   */
  public AbstractGuiActionDescrip(final AbstractAction action) {
    this.action = action;
    putValue(Action.ENABLED, Boolean.TRUE);
    putValue(VISIBLE, Boolean.TRUE);
    position = NO_POSITION;
    parent = NO_PARENT;
    addConditions = new ArrayList<GuiAddCondition>();
  }

 /**
   * Creates a action descriptor from a previous created descriptor cloning its
   * values
   * 
   * @param descr
   *          the other descriptor
   */
  public AbstractGuiActionDescrip(final AbstractGuiActionDescrip descr) {
    this.action = descr.getAction();
    putValue(Action.ENABLED, descr.getValue(Action.ENABLED));
    putValue(VISIBLE, descr.getValue(VISIBLE));
    position = descr.getPosition();
    parent = descr.getParent();
    isRTL = descr.isRTL();
    location = descr.getLocation();
    addConditions = descr.getAddConditions();
    for (final Object keyO : descr.getKeys()) {
      final String key = (String) keyO;
      super.putValue(key, descr.getValue(key));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#add(cc.kune.common
   * .client.actions.ui.descrip.GuiAddCondition)
   */
  @Override
  public void add(final GuiAddCondition addCondition) {
    addConditions.add(addCondition);
  }

  public void detachFromParent() {
    parent = NO_PARENT;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#fire(cc.kune.
   * common.client.actions.ActionEvent)
   */
  @Override
  public void fire(final ActionEvent event) {
    action.actionPerformed(event);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getAction()
   */
  @Override
  public AbstractAction getAction() {
    return action;
  }

  public List<GuiAddCondition> getAddConditions() {
    return addConditions;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getDirection()
   */
  @Override
  public Direction getDirection() {
    return isRTL ? Direction.RTL : Direction.LTR;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getId()
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Gets the location, a string used to group actions with locations (top bar,
   * bottom bar, user bar...).
   * 
   * @return the location
   */
  @Override
  public String getLocation() {
    return location;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getParent()
   */
  @Override
  public GuiActionDescrip getParent() {
    return parent;
  }

  /**
   * Gets the position.
   * 
   * @return the position
   */
  @Override
  public int getPosition() {
    return position;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getTarget()
   */
  @Override
  public Object getTarget() {
    return getValue(TARGET);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#getType()
   */
  @Override
  public abstract Class<?> getType();

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ChangeableObject#getValue(java.lang.String)
   */
  @Override
  /**
   * We try to get the gui property (for instance the name) and if it's empty we try to get the same property in actions. This permit to have several gui items with the same action but different gui properties (like text descriptions) if necessary.
   */
  public Object getValue(final String key) {
    final Object guiValue = super.getValue(key);
    if (guiValue == null) {
      return action.getValue(key);
    } else {
      return guiValue;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#hasTarget()
   */
  @Override
  public boolean hasTarget() {
    return getValue(TARGET) != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#isChild()
   */
  @Override
  public boolean isChild() {
    return !parent.equals(NO_PARENT);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return (Boolean) getValue(Action.ENABLED);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#isRTL()
   */
  @Override
  public boolean isRTL() {
    return isRTL;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#isVisible()
   */
  @Override
  public boolean isVisible() {
    return (Boolean) getValue(VISIBLE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#mustBeAdded()
   */
  @Override
  public boolean mustBeAdded() {
    final boolean result = true;
    for (final GuiAddCondition addCondition : getAddConditions()) {
      if (!addCondition.mustBeAdded(this)) {
        return false;
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#onAttach()
   */
  @Override
  public void onAttach() {
    action.onAttach();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#onDetach()
   */
  @Override
  public void onDetach() {
    action.onDettach();
  }

  /**
   * Sets the action.
   * 
   * @param action
   *          the new action
   */
  public void setAction(final AbstractAction action) {
    this.action = action;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setDropTarget
   * (cc.kune.common.client.actions.ui.descrip.DropTarget)
   */
  @Override
  public void setDropTarget(final DropTarget dropTarget) {
    putValue(DROP_TARGET, dropTarget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setEnabled(boolean
   * )
   */
  @Override
  public void setEnabled(final boolean enabled) {
    putValue(Action.ENABLED, enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setId(java.lang
   * .String)
   */
  @Override
  public void setId(final String id) {
    this.id = id;
  }

  /**
   * If we have several toolbars, we can group with the "location" string key
   * actions that must be in the same location (ex: top bar, bottom bar, and so
   * on).
   * 
   * @param location
   *          the new location
   */
  @Override
  public void setLocation(final String location) {
    this.location = location;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setParent(cc.
   * kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void setParent(final GuiActionDescrip parent) {
    setParent(parent, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setParent(cc.
   * kune.common.client.actions.ui.descrip.GuiActionDescrip, boolean)
   */
  @Override
  public void setParent(final GuiActionDescrip parent, final boolean addToParent) {
    if (parent != NO_PARENT && addToParent) {
      ((AbstractParentGuiActionDescrip) parent).add(this);
    }
    this.parent = parent;
  }

  /**
   * Sets the position (where the UI element will be positioned, for instance in
   * a toolbar or in a menu).
   * 
   * @param position
   *          the new position
   */
  @Override
  public void setPosition(final int position) {
    this.position = position;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setRTL(boolean)
   */
  @Override
  public void setRTL(final boolean isRTL) {
    this.isRTL = isRTL;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setStyles(java
   * .lang.String)
   */
  @Override
  public void setStyles(final String styles) {
    putValue(Action.STYLES, styles);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setTarget(java
   * .lang.Object)
   */
  @Override
  public void setTarget(final Object object) {
    putValue(TARGET, object);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#setVisible(boolean
   * )
   */
  @Override
  public void setVisible(final boolean visible) {
    putValue(VISIBLE, visible);
  }

  /**
   * Toggle the value of a boolean property.
   * 
   * @param property
   *          the property
   */
  protected void toggle(final String property) {
    // Action detects changes in values, then we fire a change (whatever) to
    // fire this method in the UI
    final Object value = getValue(property);
    putValue(property, value == null ? true : !((Boolean) value));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#toggleTooltipVisible
   * ()
   */
  @Override
  public void toggleTooltipVisible() {
    toggle(TOOGLE_TOOLTIP_VISIBLE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#toogleVisible()
   */
  @Override
  public void toogleVisible() {
    putValue(VISIBLE, !isVisible());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final String name = (String) getValue(Action.NAME);
    final String tooltip = (String) getValue(Action.TOOLTIP);
    return "[GuiActionDescrip: " + getClass().getName() + (name == null ? "" : " " + name)
        + (tooltip == null ? "" : " " + tooltip) + "]";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withAction(cc
   * .kune.common.client.actions.AbstractAction)
   */
  @Override
  public GuiActionDescrip withAction(final AbstractAction action) {
    setAction(action);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withAddCondition
   * (cc.kune.common.client.actions.ui.descrip.GuiAddCondition)
   */
  @Override
  public void withAddCondition(final GuiAddCondition addCondition) {
    add(addCondition);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withIcon(java
   * .lang.Object)
   */
  @Override
  public GuiActionDescrip withIcon(final Object icon) {
    super.putValue(Action.SMALL_ICON, icon);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withIconCls(java
   * .lang.String)
   */
  @Override
  public GuiActionDescrip withIconCls(final String icon) {
    putValue(Action.SMALL_ICON, icon);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withId(java.lang
   * .String)
   */
  @Override
  public GuiActionDescrip withId(final String id) {
    setId(id);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withParent(cc
   * .kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public GuiActionDescrip withParent(final GuiActionDescrip parent) {
    setParent(parent);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withParent(cc
   * .kune.common.client.actions.ui.descrip.GuiActionDescrip, boolean)
   */
  @Override
  public GuiActionDescrip withParent(final GuiActionDescrip parent, final boolean addToParent) {
    setParent(parent, addToParent);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withPosition(int)
   */
  @Override
  public GuiActionDescrip withPosition(final int position) {
    setPosition(position);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withShortcut(
   * cc.kune.common.client.actions.KeyStroke,
   * cc.kune.common.client.shortcuts.GlobalShortcutRegister)
   */
  @Override
  public GuiActionDescrip withShortcut(final KeyStroke shortcut, final GlobalShortcutRegister register) {
    putValue(Action.ACCELERATOR_KEY, shortcut);
    register.put(shortcut, action);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withShortcut(
   * java.lang.String, cc.kune.common.client.shortcuts.GlobalShortcutRegister)
   */
  @Override
  public GuiActionDescrip withShortcut(final String keys, final GlobalShortcutRegister register) {
    final KeyStroke shortcut = Shortcut.getShortcut(keys);
    putValue(Action.ACCELERATOR_KEY, shortcut);
    register.put(shortcut, action);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withStyles(java
   * .lang.String)
   */
  @Override
  public GuiActionDescrip withStyles(final String styles) {
    setStyles(styles);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withText(java
   * .lang.String)
   */
  @Override
  public GuiActionDescrip withText(final String text) {
    putValue(Action.NAME, text);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withToolTip(java
   * .lang.String)
   */
  @Override
  public GuiActionDescrip withToolTip(final String tooltip) {
    putValue(Action.TOOLTIP, tooltip);
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescrip#withVisible(boolean
   * )
   */
  @Override
  public GuiActionDescrip withVisible(final boolean visible) {
    setVisible(visible);
    return this;
  }
}
