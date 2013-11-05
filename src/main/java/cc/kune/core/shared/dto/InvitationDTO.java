/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public class InvitationDTO implements IsSerializable {
  private Long date;
  private String description;
  private UserSimpleDTO fromUser;
  private String hash;
  private String invitedToToken;
  private String name;
  private InvitationType type;

  public InvitationDTO() {
    this(null, null, null, null, null, null, null);
  }

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

  public Long getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public UserSimpleDTO getFromUser() {
    return fromUser;
  }

  public String getHash() {
    return hash;
  }

  public String getInvitedToToken() {
    return invitedToToken;
  }

  public String getName() {
    return name;
  }

  public InvitationType getType() {
    return type;
  }

  public void setDate(final Long date) {
    this.date = date;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setFromUser(final UserSimpleDTO fromUser) {
    this.fromUser = fromUser;
  }

  public void setHash(final String hash) {
    this.hash = hash;
  }

  public void setInvitedToToken(final String invitedToToken) {
    this.invitedToToken = invitedToToken;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setType(final InvitationType type) {
    this.type = type;
  }

}
