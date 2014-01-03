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

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/*
 * AccessList samples (using GroupListMode)

 |---------------------------------------------------+--------+---------------+----------+---------|
 | AccessList (A: Admins, E:Editors, V: Viewers      |        | Administrable | Editable | Visible |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:EVERYONE, E: EVERYONE, V:EVERYONE)             | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | yes           | yes      | yes     |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E: NORMAL, V:EVERYONE)         | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | no       | yes     |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E: NORMAL(Group2), V:EVERYONE) | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | yes      | yes     |
 |                                                   | Group3 | no            | no       | yes     |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E: NORMAL, V:NORMAL(Group2))   | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | no       | yes     |
 |                                                   | Group3 | no            | no       | no      |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E:NORMAL(Group2), V: NOBODY)   | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | yes      | yes     |
 |                                                   | Group3 | no            | no       | no      |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E: NOBODY, V:NORMAL(Group2))   | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | no       | yes     |
 |                                                   | Group3 | no            | no       | no      |
 |---------------------------------------------------+--------+---------------+----------+---------|
 | (A:NORMAL(Group1), E: NOBODY, V:NOBODY)           | Group1 | yes           | yes      | yes     |
 |                                                   | Group2 | no            | no       | no      |
 |                                                   | Group3 | no            | no       | no      |
 |---------------------------------------------------+--------+---------------+----------+---------|

 */

/**
 * The Class AccessListsDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AccessListsDTO implements IsSerializable {

  /** The admins. */
  private GroupListDTO admins;

  /** The editors. */
  private GroupListDTO editors;

  /** The viewers. */
  private GroupListDTO viewers;

  /**
   * Instantiates a new access lists dto.
   */
  public AccessListsDTO() {
    this(null, null, null);
  }

  /**
   * Instantiates a new access lists dto.
   * 
   * @param admins
   *          the admins
   * @param editors
   *          the editors
   * @param viewers
   *          the viewers
   */
  public AccessListsDTO(final GroupListDTO admins, final GroupListDTO editors, final GroupListDTO viewers) {
    this.admins = admins;
    this.editors = editors;
    this.viewers = viewers;
  }

  /**
   * Gets the admins.
   * 
   * @return the admins
   */
  public GroupListDTO getAdmins() {
    return admins;
  }

  /**
   * Gets the editors.
   * 
   * @return the editors
   */
  public GroupListDTO getEditors() {
    return editors;
  }

  /**
   * Gets the viewers.
   * 
   * @return the viewers
   */
  public GroupListDTO getViewers() {
    return viewers;
  }

  /**
   * Sets the admins.
   * 
   * @param admins
   *          the new admins
   */
  public void setAdmins(final GroupListDTO admins) {
    this.admins = admins;
  }

  /**
   * Sets the editors.
   * 
   * @param editors
   *          the new editors
   */
  public void setEditors(final GroupListDTO editors) {
    this.editors = editors;
  }

  /**
   * Sets the viewers.
   * 
   * @param viewers
   *          the new viewers
   */
  public void setViewers(final GroupListDTO viewers) {
    this.viewers = viewers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AccessListDTO[admins :" + admins + "; editors: " + editors + "; viewers: " + viewers + "]";
  }

}
