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
package cc.kune.common.client.actions;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractExtendedAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractExtendedAction extends AbstractAction {
  
  /** The Constant NO_ICON. */
  public static final String NO_ICON = null;
  
  /** The Constant NO_TEXT. */
  public static final String NO_TEXT = null;

  /**
   * Instantiates a new abstract extended action.
   */
  public AbstractExtendedAction() {
    super();
  }

  /**
   * Instantiates a new abstract extended action.
   *
   * @param text the text
   */
  public AbstractExtendedAction(final String text) {
    this(text, null, null);
  }

  /**
   * Instantiates a new abstract extended action.
   *
   * @param text the text
   * @param iconCls the icon cls
   */
  public AbstractExtendedAction(final String text, final String iconCls) {
    this(text, null, iconCls);
  }

  /**
   * Instantiates a new abstract extended action.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param iconCls the icon cls
   */
  public AbstractExtendedAction(final String text, final String tooltip, final String iconCls) {
    super();
    super.putValue(Action.NAME, text);
    super.putValue(Action.TOOLTIP, tooltip);
    super.putValue(Action.SMALL_ICON, iconCls);
  }

  /**
   * With icon.
   *
   * @param icon the icon
   * @return the abstract extended action
   */
  public AbstractExtendedAction withIcon(final ImageResource icon) {
    super.putValue(Action.SMALL_ICON, icon);
    return this;
  }

  /**
   * With icon cls.
   *
   * @param icon the icon
   * @return the abstract extended action
   */
  public AbstractExtendedAction withIconCls(final String icon) {
    super.putValue(Action.TOOLTIP, icon);
    return this;
  }

  /**
   * With text.
   *
   * @param text the text
   * @return the abstract extended action
   */
  public AbstractExtendedAction withText(final String text) {
    super.putValue(Action.NAME, text);
    return this;
  }

  /**
   * With tool tip.
   *
   * @param tooltip the tooltip
   * @return the abstract extended action
   */
  public AbstractExtendedAction withToolTip(final String tooltip) {
    super.putValue(Action.TOOLTIP, tooltip);
    return this;
  }
}
