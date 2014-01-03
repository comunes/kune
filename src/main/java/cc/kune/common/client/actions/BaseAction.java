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
 * The Class BaseAction.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BaseAction extends AbstractAction {
  
  /**
   * Instantiates a new base action.
   *
   * @param text the text
   * @param tooltip the tooltip
   */
  public BaseAction(final String text, final String tooltip) {
    super();
    super.putValue(Action.NAME, text);
    super.putValue(Action.TOOLTIP, tooltip);
  }

  /**
   * Instantiates a new base action.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public BaseAction(final String text, final String tooltip, final ImageResource icon) {
    super();
    super.putValue(Action.NAME, text);
    super.putValue(Action.TOOLTIP, tooltip);
    super.putValue(Action.SMALL_ICON, icon);
  }

  /**
   * Instantiates a new base action.
   *
   * @param text the text
   * @param tooltip the tooltip
   * @param icon the icon
   */
  public BaseAction(final String text, final String tooltip, final String icon) {
    super();
    super.putValue(Action.NAME, text);
    super.putValue(Action.TOOLTIP, tooltip);
    super.putValue(Action.SMALL_ICON, icon);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
   */
  public void actionPerformed(final ActionEvent actionEvent) {
    // Nothing to do
  }
}
