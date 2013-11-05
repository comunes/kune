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
package cc.kune.core.server;

import java.io.Serializable;

import com.google.inject.servlet.SessionScoped;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSession.
 *
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SessionScoped
public class UserSession implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4665523798343705868L;

  /** The user hash. */
  private String userHash;
  
  /** The user id. */
  private Long userId;

  /**
   * Instantiates a new user session.
   */
  public UserSession() {
  }

  /**
   * Gets the hash.
   *
   * @return the hash
   */
  public String getHash() {
    return userHash;
  }

  /**
   * Gets the user id.
   *
   * @return the user id
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets the hash.
   *
   * @param hash the new hash
   */
  public void setHash(final String hash) {
    this.userHash = hash;
  }

  /**
   * Sets the user id.
   *
   * @param userId the new user id
   */
  public void setUserId(final Long userId) {
    this.userId = userId;
  }

}
