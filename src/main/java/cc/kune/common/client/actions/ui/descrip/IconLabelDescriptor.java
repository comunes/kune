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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class IconLabelDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class IconLabelDescriptor extends AbstractGuiActionDescrip {

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param action the action
   */
  public IconLabelDescriptor(final AbstractAction action) {
    super(action);
  }

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param text the text
   */
  public IconLabelDescriptor(final String text) {
    this(text, "");
  }

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param text the text
   * @param icon the icon
   */
  public IconLabelDescriptor(final String text, final ImageResource icon) {
    this(text, null, icon);
  }

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   */
  public IconLabelDescriptor(final String text, final String tooltip) {
    this(text, tooltip, "");
  }

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public IconLabelDescriptor(final String text, final String tooltip, final ImageResource icon) {
    this(new BaseAction(text, tooltip, icon));
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
    putValue(Action.SMALL_ICON, icon);
  }

  /**
   * Instantiates a new icon label descriptor.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public IconLabelDescriptor(final String text, final String tooltip, final String icon) {
    this(new BaseAction(text, tooltip, icon));
    putValue(Action.NAME, text);
    putValue(Action.TOOLTIP, tooltip);
    putValue(Action.SMALL_ICON, icon);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return IconLabelDescriptor.class;
  }

}
