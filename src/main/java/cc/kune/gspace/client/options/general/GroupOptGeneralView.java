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
package cc.kune.gspace.client.options.general;

import cc.kune.core.shared.dto.GroupType;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroupOptGeneralView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GroupOptGeneralView extends EntityOptGeneralView {

  /**
   * Gets the group type.
   * 
   * @return the group type
   */
  GroupType getGroupType();

  /**
   * Gets the long name.
   * 
   * @return the long name
   */
  String getLongName();

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  String getShortName();

  /**
   * Sets the group type.
   * 
   * @param groupType
   *          the new group type
   */
  void setGroupType(GroupType groupType);

  /**
   * Sets the long name.
   * 
   * @param longName
   *          the new long name
   */
  void setLongName(String longName);

  /**
   * Sets the short name.
   * 
   * @param shortName
   *          the new short name
   */
  void setShortName(String shortName);

  /**
   * Sets the short name enabled.
   * 
   * @param enabled
   *          the new short name enabled
   */
  void setShortNameEnabled(boolean enabled);

}
