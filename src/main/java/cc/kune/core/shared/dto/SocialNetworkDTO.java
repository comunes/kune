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
/**
 * The Class SocialNetworkDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkDTO implements IsSerializable {

  /** The access lists. */
  AccessListsDTO accessLists;

  /** The pending collaborators. */
  GroupListDTO pendingCollaborators;

  /**
   * Instantiates a new social network dto.
   */
  public SocialNetworkDTO() {
    accessLists = new AccessListsDTO();
    pendingCollaborators = new GroupListDTO();
  }

  /**
   * Gets the access lists.
   * 
   * @return the access lists
   */
  public AccessListsDTO getAccessLists() {
    return accessLists;
  }

  /**
   * Gets the pending collaborators.
   * 
   * @return the pending collaborators
   */
  public GroupListDTO getPendingCollaborators() {
    return pendingCollaborators;
  }

  /**
   * Sets the access lists.
   * 
   * @param accessLists
   *          the new access lists
   */
  public void setAccessLists(final AccessListsDTO accessLists) {
    this.accessLists = accessLists;
  }

  /**
   * Sets the pending collaborators.
   * 
   * @param pendingCollaborators
   *          the new pending collaborators
   */
  public void setPendingCollaborators(final GroupListDTO pendingCollaborators) {
    this.pendingCollaborators = pendingCollaborators;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SocialNetworkDTO[accessList: " + accessLists + "; pendingsCollabs: " + pendingCollaborators
        + "]";
  }

}
