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
package cc.kune.domain;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class UserBuddiesData.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserBuddiesData {

  /** The Constant EMPTY. */
  public static final UserBuddiesData EMPTY = new UserBuddiesData();

  /** The buddies. */
  private List<User> buddies;

  /** The other ext buddies. */
  private int otherExtBuddies;

  /**
   * Instantiates a new user buddies data.
   */
  public UserBuddiesData() {
    otherExtBuddies = 0;
    buddies = new ArrayList<User>();
  }

  /**
   * Contains.
   * 
   * @param shortName
   *          the short name
   * @return true, if successful
   */
  public boolean contains(final String shortName) {
    for (final User buddie : buddies) {
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
  public List<User> getBuddies() {
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
  public void setBuddies(final List<User> buddies) {
    this.buddies = buddies;
  }

  /**
   * Sets the other ext buddies.
   * 
   * @param otherExtBuddies
   *          the new other ext buddies
   */
  public void setOtherExtBuddies(final int otherExtBuddies) {
    this.otherExtBuddies = otherExtBuddies;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserBuddiesData[ext: " + otherExtBuddies + " / int: " + buddies + "]";
  }
}
