/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) unActionder
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

/**
 * The Class ToolbarItemDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolbarItemDescriptor extends AbstractGuiActionDescrip {

  /**
   * Instantiates a new toolbar item descriptor.
   */
  public ToolbarItemDescriptor() {
    this("", "");
  }

  public ToolbarItemDescriptor(final AbstractAction action) {
    super(action);
  }

  /**
   * Instantiates a new toolbar item descriptor.
   *
   * @param text
   *          the text
   */
  public ToolbarItemDescriptor(final String text) {
    this(text, "");
  }

  public ToolbarItemDescriptor(final String text, final AbstractAction action) {
    this(text, null, action);
  }

  /**
   * Instantiates a new toolbar item descriptor.
   *
   * @param text
   *          the text
   * @param tooltip
   *          the tooltip
   */
  public ToolbarItemDescriptor(final String text, final String tooltip) {
    super(new BaseAction(text, tooltip));
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
  }

  public ToolbarItemDescriptor(final String text, final String tooltip, final AbstractAction action) {
    super(action);
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return ToolbarItemDescriptor.class;
  }

}
