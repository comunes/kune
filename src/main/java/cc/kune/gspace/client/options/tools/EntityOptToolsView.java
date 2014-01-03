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
package cc.kune.gspace.client.options.tools;

import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.EntityOptionsTabView;

import com.google.gwt.event.dom.client.ClickHandler;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntityOptToolsView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface EntityOptToolsView extends EntityOptionsTabView {

  /**
   * Adds the.
   * 
   * @param tool
   *          the tool
   * @param enabled
   *          the enabled
   * @param checked
   *          the checked
   * @param clickHandler
   *          the click handler
   */
  void add(ToolSimpleDTO tool, boolean enabled, boolean checked, ClickHandler clickHandler);

  /**
   * Clear.
   */
  void clear();

  /**
   * Checks if is checked.
   * 
   * @param tool
   *          the tool
   * @return true, if is checked
   */
  boolean isChecked(String tool);

  /**
   * Mask.
   */
  void mask();

  /**
   * Sets the checked.
   * 
   * @param tool
   *          the tool
   * @param checked
   *          the checked
   */
  void setChecked(String tool, boolean checked);

  /**
   * Sets the enabled.
   * 
   * @param tool
   *          the tool
   * @param enabled
   *          the enabled
   */
  void setEnabled(String tool, boolean enabled);

  /**
   * Sets the tooltip.
   * 
   * @param tool
   *          the tool
   * @param tip
   *          the tip
   */
  void setTooltip(String tool, String tip);

  /**
   * Unmask.
   */
  void unmask();
}
