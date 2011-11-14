/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class HomeStatsDTO implements IsSerializable {

  private List<ContentSimpleDTO> lastContentsOfMyGroups;
  private List<GroupDTO> lastGroups;
  private List<ContentSimpleDTO> lastPublishedContents;
  private Long totalGroups;
  private Long totalUsers;

  public HomeStatsDTO() {
  }

  public List<ContentSimpleDTO> getLastContentsOfMyGroups() {
    return lastContentsOfMyGroups;
  }

  public List<GroupDTO> getLastGroups() {
    return lastGroups;
  }

  public List<ContentSimpleDTO> getLastPublishedContents() {
    return lastPublishedContents;
  }

  public Long getTotalGroups() {
    return totalGroups;
  }

  public Long getTotalUsers() {
    return totalUsers;
  }

  public void setLastContentsOfMyGroups(final List<ContentSimpleDTO> lastContentsOfMyGroups) {
    this.lastContentsOfMyGroups = lastContentsOfMyGroups;
  }

  public void setLastGroups(final List<GroupDTO> lastGroups) {
    this.lastGroups = lastGroups;
  }

  public void setLastPublishedContents(final List<ContentSimpleDTO> lastPublishedContents) {
    this.lastPublishedContents = lastPublishedContents;
  }

  public void setTotalGroups(final Long totalGroups) {
    this.totalGroups = totalGroups;
  }

  public void setTotalUsers(final Long totalUsers) {
    this.totalUsers = totalUsers;
  }

}
