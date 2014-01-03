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

import cc.kune.core.shared.domain.InvitationType;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class InvitationDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InvitationDTO implements IsSerializable {

  /** The date. */
  private Long date;

  /** The description. */
  private String description;

  /** The from user. */
  private UserSimpleDTO fromUser;

  /** The hash. */
  private String hash;

  /** The invited to token. */
  private String invitedToToken;

  /** The name. */
  private String name;

  /** The type. */
  private InvitationType type;

  /**
   * Instantiates a new invitation dto.
   */
  public InvitationDTO() {
    this(null, null, null, null, null, null, null);
  }

  /**
   * Instantiates a new invitation dto.
   * 
   * @param date
   *          the date
   * @param fromUser
   *          the from user
   * @param hash
   *          the hash
   * @param invitedToToken
   *          the invited to token
   * @param type
   *          the type
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public InvitationDTO(final Long date, final UserSimpleDTO fromUser, final String hash,
      final String invitedToToken, final InvitationType type, final String name, final String description) {
    this.date = date;
    this.fromUser = fromUser;
    this.hash = hash;
    this.invitedToToken = invitedToToken;
    this.type = type;
    this.name = name;
    this.description = description;
  }

  /**
   * Gets the date.
   * 
   * @return the date
   */
  public Long getDate() {
    return date;
  }

  /**
   * Gets the description.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the from user.
   * 
   * @return the from user
   */
  public UserSimpleDTO getFromUser() {
    return fromUser;
  }

  /**
   * Gets the hash.
   * 
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * Gets the invited to token.
   * 
   * @return the invited to token
   */
  public String getInvitedToToken() {
    return invitedToToken;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public InvitationType getType() {
    return type;
  }

  /**
   * Sets the date.
   * 
   * @param date
   *          the new date
   */
  public void setDate(final Long date) {
    this.date = date;
  }

  /**
   * Sets the description.
   * 
   * @param description
   *          the new description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Sets the from user.
   * 
   * @param fromUser
   *          the new from user
   */
  public void setFromUser(final UserSimpleDTO fromUser) {
    this.fromUser = fromUser;
  }

  /**
   * Sets the hash.
   * 
   * @param hash
   *          the new hash
   */
  public void setHash(final String hash) {
    this.hash = hash;
  }

  /**
   * Sets the invited to token.
   * 
   * @param invitedToToken
   *          the new invited to token
   */
  public void setInvitedToToken(final String invitedToToken) {
    this.invitedToToken = invitedToToken;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          the new type
   */
  public void setType(final InvitationType type) {
    this.type = type;
  }

}
