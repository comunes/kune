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
package cc.kune.core.shared.dto;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupListDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupListDTO implements IsSerializable {

  /** The Constant EVERYONE. */
  public static final String EVERYONE = "EVERYONE";

  /** The Constant NOBODY. */
  public static final String NOBODY = "NOBODY";

  /** The Constant NORMAL. */
  public static final String NORMAL = "NORMAL";

  /** The list. */
  private Set<GroupDTO> list;

  /** The mode. */
  private String mode;

  /**
   * Instantiates a new group list dto.
   */
  public GroupListDTO() {
    this(null);
  }

  /**
   * Instantiates a new group list dto.
   * 
   * @param list
   *          the list
   */
  public GroupListDTO(final Set<GroupDTO> list) {
    this.list = list;
  }

  /**
   * Gets the list.
   * 
   * @return the list
   */
  public Set<GroupDTO> getList() {
    return list;
  }

  /**
   * Gets the mode.
   * 
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * Includes.
   * 
   * @param group
   *          the group
   * @return true, if successful
   */
  public boolean includes(final GroupDTO group) {
    // Duplicate code in GroupList
    if (mode.equals(NOBODY)) {
      return false;
    }
    if (mode.equals(EVERYONE)) {
      return true;
    }
    final boolean contains = list.contains(group);
    return contains;
  }

  /**
   * Sets the list.
   * 
   * @param list
   *          the new list
   */
  public void setList(final Set<GroupDTO> list) {
    this.list = list;
  }

  /**
   * Sets the mode.
   * 
   * @param mode
   *          the new mode
   */
  public void setMode(final String mode) {
    this.mode = mode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "GroupListDTO[(" + mode + "): " + list + "]";
  }
}
