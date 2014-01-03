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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UserBuddiesPresenceDataDTO is used to get presence info of our
 * buddies.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserBuddiesPresenceDataDTO implements IsSerializable {

  /** The Constant NO_BUDDIES. */
  public static final UserBuddiesPresenceDataDTO NO_BUDDIES = new UserBuddiesPresenceDataDTO();

  /** The map where is stored the info. */
  private Map<String, Long> map;

  /**
   * Instantiates a new user buddies presence data dto.
   */
  public UserBuddiesPresenceDataDTO() {
    map = new HashMap<String, Long>();
  }

  /**
   * Gets the last connected time of a username.
   * 
   * @param username
   *          the username
   * @return the last connnected date
   */
  public Long get(final String username) {
    return map.get(username);
  }

  /**
   * Put.
   * 
   * @param username
   *          the username
   * @param lastConnected
   *          the last connected time
   */
  public void put(final String username, final Long lastConnected) {
    map.put(username, lastConnected);
  }

  /**
   * Sets the map.
   * 
   * @param map
   *          the map
   */
  public void setMap(final Map<String, Long> map) {
    this.map = map;
  }

  /**
   * Size.
   * 
   * @return the int
   */
  public int size() {
    return map.size();
  }

}
