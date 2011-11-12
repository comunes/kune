/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
import cc.kune.core.client.dnd.DropTarget;

import com.google.gwt.i18n.client.HasDirection.Direction;

public interface GuiActionDescrip {

  public static final String DROP_TARGET = "dropTarget";

  public static final AbstractGuiActionDescrip NO_PARENT = new NoParentGuiActionDescriptor();

  public static final int NO_POSITION = -1;

  /**
   * {@link #TARGET} is used to associate a {@link #AbstractGuiActionDescrip}
   * with an object like groups, group names, users, and so on, and used to
   * execute actions against these targets
   */
  public static final String TARGET = "target";

  public static final String TOOGLE_TOOLTIP_VISIBLE = "toogle-tooltip-visible";
  public static final String VISIBLE = "visibleprop";

  void add(final GuiAddCondition addCondition);

  void addPropertyChangeListener(final PropertyChangeListener listener);

  void fire(final ActionEvent event);

  AbstractAction getAction();

  Direction getDirection();

  String getId();

  Object[] getKeys();

  String getLocation();

  GuiActionDescrip getParent();

  int getPosition();

  PropertyChangeListener[] getPropertyChangeListeners();

  Object getTarget();

  Class<?> getType();

  Object getValue(final String key);

  boolean hasTarget();

  boolean isChild();

  boolean isEnabled();

  boolean isRTL();

  boolean isVisible();

  boolean mustBeAdded();

  public void putValue(final String key, final Object value);

  void removePropertyChangeListener(final PropertyChangeListener listener);

  void setDropTarget(DropTarget dropTarget);

  void setEnabled(final boolean enabled);

  void setId(final String id);

  void setLocation(final String location);

  void setParent(final GuiActionDescrip parent);

  /**
   * @param parent
   * @param addToParent
   *          if yes, the action will be added directly to the parent, useful to
   *          attach menu items (or other child actions) directly to its parent
   *          without any other check (for instance session status, etc).
   *          Default to yes
   */
  void setParent(GuiActionDescrip parent, boolean addToParent);

  void setPosition(final int position);

  void setRTL(final boolean isRTL);

  void setStyles(final String styles);

  void setTarget(final Object object);

  void setVisible(final boolean visible);

  void toggleTooltipVisible();

  void toogleVisible();

  GuiActionDescrip withIcon(Object icon);

  GuiActionDescrip withIconCls(String icon);

  GuiActionDescrip withId(String id);

  GuiActionDescrip withParent(GuiActionDescrip parent);

  /**
   * @param parent
   * @param addToParent
   *          if yes, the action will be added directly to the parent, useful to
   *          attach menu items (or other child actions) directly to its parent
   *          without any other check (for instance session status, etc)
   */
  GuiActionDescrip withParent(GuiActionDescrip parent, boolean addToParent);

  GuiActionDescrip withShortcut(KeyStroke keystroke);

  GuiActionDescrip withStyles(String styles);

  GuiActionDescrip withText(String text);

  GuiActionDescrip withToolTip(String tooltip);
}
