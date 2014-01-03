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

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ParticipationDataDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ParticipationDataDTO implements IsSerializable {

  /** The groups is admin. */
  private List<GroupDTO> groupsIsAdmin;

  /** The groups is collab. */
  private List<GroupDTO> groupsIsCollab;

  /**
   * Instantiates a new participation data dto.
   */
  public ParticipationDataDTO() {
    this(null, null);
  }

  /**
   * Instantiates a new participation data dto.
   * 
   * @param groupsIsAdmin
   *          the groups is admin
   * @param groupsIsCollab
   *          the groups is collab
   */
  public ParticipationDataDTO(final List<GroupDTO> groupsIsAdmin, final List<GroupDTO> groupsIsCollab) {
    this.groupsIsAdmin = groupsIsAdmin;
    this.groupsIsCollab = groupsIsCollab;
  }

  /**
   * Gets the groups is admin.
   * 
   * @return the groups is admin
   */
  public List<GroupDTO> getGroupsIsAdmin() {
    return groupsIsAdmin;
  }

  /**
   * Gets the groups is collab.
   * 
   * @return the groups is collab
   */
  public List<GroupDTO> getGroupsIsCollab() {
    return groupsIsCollab;
  }

  /**
   * Sets the groups is admin.
   * 
   * @param groupsIsAdmin
   *          the new groups is admin
   */
  public void setGroupsIsAdmin(final List<GroupDTO> groupsIsAdmin) {
    this.groupsIsAdmin = groupsIsAdmin;
  }

  /**
   * Sets the groups is collab.
   * 
   * @param groupsIsCollab
   *          the new groups is collab
   */
  public void setGroupsIsCollab(final List<GroupDTO> groupsIsCollab) {
    this.groupsIsCollab = groupsIsCollab;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ParticipationDataDTO[admin in: " + groupsIsAdmin.toString() + ", collab in: "
        + groupsIsCollab.toString() + "]";
  }
}
