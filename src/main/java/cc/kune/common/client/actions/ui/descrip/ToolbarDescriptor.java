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

import org.gwtbootstrap3.client.ui.constants.NavbarPosition;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

/**
 * The Class ToolbarDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolbarDescriptor extends AbstractParentGuiActionDescrip {

  public static final String POSITION = "toolbar-position";
  /** The Constant TOOLBAR_CLEAR. */
  public static final String TOOLBAR_CLEAR = "menuclear";

  /**
   * Builds the.
   *
   * @return the toolbar descriptor
   */
  public static ToolbarDescriptor build() {
    return new ToolbarDescriptor();
  }

  /**
   * Instantiates a new toolbar descriptor.
   */
  public ToolbarDescriptor() {
    super(new BaseAction(null, null));
    super.getAction().putValue(TOOLBAR_CLEAR, false);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.descrip.AbstractParentGuiActionDescrip
   * #clear()
   */
  @Override
  public void clear() {
    // Action detects changes in values, then we fire a change (whatever) to
    // fire this method in the UI
    putValue(TOOLBAR_CLEAR, !((Boolean) getValue(TOOLBAR_CLEAR)));
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return ToolbarDescriptor.class;
  }

  public void setPosition(final NavbarPosition position) {
    putValue(POSITION, position);
  }

  /**
   * Sets the text.
   *
   * @param text
   *          the new text
   */
  public void setText(final String text) {
    putValue(Action.NAME, text);
  }
}
