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
package cc.kune.core.server.stats;

import java.util.List;

import cc.kune.domain.Content;
import cc.kune.domain.Group;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeStats.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HomeStats {

  /** The last contents of my groups. */
  private List<Content> lastContentsOfMyGroups;

  /** The last groups. */
  private List<Group> lastGroups;

  /** The last published contents. */
  private List<Content> lastPublishedContents;

  /** The total groups. */
  private Long totalGroups;

  /** The total users. */
  private Long totalUsers;

  /**
   * Instantiates a new home stats.
   */
  public HomeStats() {
  }

  /**
   * Gets the last contents of my groups.
   * 
   * @return the last contents of my groups
   */
  public List<Content> getLastContentsOfMyGroups() {
    return lastContentsOfMyGroups;
  }

  /**
   * Gets the last groups.
   * 
   * @return the last groups
   */
  public List<Group> getLastGroups() {
    return lastGroups;
  }

  /**
   * Gets the last published contents.
   * 
   * @return the last published contents
   */
  public List<Content> getLastPublishedContents() {
    return lastPublishedContents;
  }

  /**
   * Gets the total groups.
   * 
   * @return the total groups
   */
  public Long getTotalGroups() {
    return totalGroups;
  }

  /**
   * Gets the total users.
   * 
   * @return the total users
   */
  public Long getTotalUsers() {
    return totalUsers;
  }

  /**
   * Sets the last contents of my groups.
   * 
   * @param lastContentsOfMyGroups
   *          the new last contents of my groups
   */
  public void setLastContentsOfMyGroups(final List<Content> lastContentsOfMyGroups) {
    this.lastContentsOfMyGroups = lastContentsOfMyGroups;
  }

  /**
   * Sets the last groups.
   * 
   * @param lastGroups
   *          the new last groups
   */
  public void setLastGroups(final List<Group> lastGroups) {
    this.lastGroups = lastGroups;
  }

  /**
   * Sets the last published contents.
   * 
   * @param lastPublishedContents
   *          the new last published contents
   */
  public void setLastPublishedContents(final List<Content> lastPublishedContents) {
    this.lastPublishedContents = lastPublishedContents;
  }

  /**
   * Sets the total groups.
   * 
   * @param totalGroups
   *          the new total groups
   */
  public void setTotalGroups(final Long totalGroups) {
    this.totalGroups = totalGroups;
  }

  /**
   * Sets the total users.
   * 
   * @param totalUsers
   *          the new total users
   */
  public void setTotalUsers(final Long totalUsers) {
    this.totalUsers = totalUsers;
  }

}
