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
 \*/
package cc.kune.gspace.client.tool.selector;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

// TODO: Auto-generated Javadoc
/**
 * The Interface ToolSelectorItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ToolSelectorItem {

  /**
   * Gets the tool short name.
   * 
   * @return the tool short name
   */
  String getToolShortName();

  /**
   * Gets the view.
   * 
   * @return the view
   */
  ToolSelectorItemView getView();

  /**
   * Gets the visible for rol.
   * 
   * @return the visible for rol
   */
  AccessRolDTO getVisibleForRol();

  /**
   * Sets the group short name.
   * 
   * @param groupShortName
   *          the new group short name
   */
  void setGroupShortName(String groupShortName);

  /**
   * Sets the selected.
   * 
   * @param selected
   *          the new selected
   */
  void setSelected(boolean selected);

  /**
   * Sets the token.
   * 
   * @param token
   *          the new token
   */
  void setToken(StateToken token);

  /**
   * Sets the visible.
   * 
   * @param visible
   *          the new visible
   */
  void setVisible(boolean visible);

}
