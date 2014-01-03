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
package cc.kune.core.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UserBuddiesDataDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserBuddiesDataDTO implements IsSerializable {

  /** The Constant NO_BUDDIES. */
  public static final UserBuddiesDataDTO NO_BUDDIES = new UserBuddiesDataDTO();

  /** The buddies. */
  private List<UserSimpleDTO> buddies;

  /** The other ext buddies. */
  private int otherExtBuddies;

  /**
   * Instantiates a new user buddies data dto.
   */
  public UserBuddiesDataDTO() {
    buddies = new ArrayList<UserSimpleDTO>();
    otherExtBuddies = 0;
  }

  /**
   * Contains.
   * 
   * @param shortName
   *          the short name
   * @return true, if successful
   */
  public boolean contains(final String shortName) {
    for (final UserSimpleDTO buddie : buddies) {
      if (buddie.getShortName().equals(shortName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the buddies.
   * 
   * @return the buddies
   */
  public List<UserSimpleDTO> getBuddies() {
    return buddies;
  }

  /**
   * Gets the other ext buddies.
   * 
   * @return the other ext buddies
   */
  public int getOtherExtBuddies() {
    return otherExtBuddies;
  }

  /**
   * Sets the buddies.
   * 
   * @param buddies
   *          the new buddies
   */
  public void setBuddies(final List<UserSimpleDTO> buddies) {
    this.buddies = buddies;
  }

  /**
   * Sets the other ext buddies.
   * 
   * @param otherExternalBuddies
   *          the new other ext buddies
   */
  public void setOtherExtBuddies(final int otherExternalBuddies) {
    this.otherExtBuddies = otherExternalBuddies;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserBuddiesDataDTO[ext: " + otherExtBuddies + " / int: " + buddies + "]";
  }
}
