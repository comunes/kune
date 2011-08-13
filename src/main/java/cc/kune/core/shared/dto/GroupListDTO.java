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

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupListDTO implements IsSerializable {
  public static final String EVERYONE = "EVERYONE";
  public static final String NOBODY = "NOBODY";
  public static final String NORMAL = "NORMAL";
  private Set<GroupDTO> list;
  private String mode;

  public GroupListDTO() {
    this(null);
  }

  public GroupListDTO(final Set<GroupDTO> list) {
    this.list = list;
  }

  public boolean checkIfIncludes(final GroupDTO group, final String mode) {
    if (mode.equals(NOBODY)) {
      return false;
    }
    if (mode.equals(EVERYONE)) {
      return true;
    }
    boolean contains = list.contains(group);
    return contains;
  }

  public Set<GroupDTO> getList() {
    return list;
  }

  public String getMode() {
    return mode;
  }

  public boolean includes(final GroupDTO group) {
    return checkIfIncludes(group, mode);
  }

  public void setList(final Set<GroupDTO> list) {
    this.list = list;
  }

  public void setMode(final String mode) {
    this.mode = mode;
  }

  @Override
  public String toString() {
    return "GroupListDTO[(" + mode + "): " + list + "]";
  }
}
