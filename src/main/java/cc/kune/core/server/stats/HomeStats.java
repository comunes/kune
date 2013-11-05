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
package cc.kune.core.server.stats;

import java.util.List;

import cc.kune.domain.Content;
import cc.kune.domain.Group;

public class HomeStats {

  private List<Content> lastContentsOfMyGroups;
  private List<Group> lastGroups;
  private List<Content> lastPublishedContents;
  private Long totalGroups;
  private Long totalUsers;

  public HomeStats() {
  }

  public List<Content> getLastContentsOfMyGroups() {
    return lastContentsOfMyGroups;
  }

  public List<Group> getLastGroups() {
    return lastGroups;
  }

  public List<Content> getLastPublishedContents() {
    return lastPublishedContents;
  }

  public Long getTotalGroups() {
    return totalGroups;
  }

  public Long getTotalUsers() {
    return totalUsers;
  }

  public void setLastContentsOfMyGroups(final List<Content> lastContentsOfMyGroups) {
    this.lastContentsOfMyGroups = lastContentsOfMyGroups;
  }

  public void setLastGroups(final List<Group> lastGroups) {
    this.lastGroups = lastGroups;
  }

  public void setLastPublishedContents(final List<Content> lastPublishedContents) {
    this.lastPublishedContents = lastPublishedContents;
  }

  public void setTotalGroups(final Long totalGroups) {
    this.totalGroups = totalGroups;
  }

  public void setTotalUsers(final Long totalUsers) {
    this.totalUsers = totalUsers;
  }

}
