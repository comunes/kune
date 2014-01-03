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
package cc.kune.gxtbinds.client.actions.gxtui;

import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class GxtDefTooltip.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GxtDefTooltip extends ToolTipConfig {

  /**
   * The Enum Position.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum Position {

    /** The bottom. */
    bottom,
    /** The left. */
    left,
    /** The right. */
    right,
    /** The top. */
    top
  }

  /**
   * Instantiates a new gxt def tooltip.
   * 
   * @param text
   *          the text
   */
  public GxtDefTooltip(final String text) {
    this(null, text, Position.left);
  }

  /**
   * Instantiates a new gxt def tooltip.
   * 
   * @param title
   *          the title
   * @param text
   *          the text
   */
  public GxtDefTooltip(final String title, final String text) {
    this(title, text, Position.left);
  }

  /**
   * Instantiates a new gxt def tooltip.
   * 
   * @param title
   *          the title
   * @param text
   *          the text
   * @param pos
   *          the pos
   */
  public GxtDefTooltip(final String title, final String text, final Position pos) {
    setText(text);
    setTitle(title);
    setMouseOffset(new int[] { 0, 0 });
    setAnchor(pos.name());
    setCloseable(false);
    setTrackMouse(true);
  }
}
