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
package cc.kune.domain;

import java.util.Set;

public class ParticipationData {
  private Set<Group> groupsIsAdmin;
  private Set<Group> groupsIsCollab;

  public ParticipationData() {
    this(null, null);
  }

  public ParticipationData(final Set<Group> groupsIsAdmin, final Set<Group> groupsIsCollab) {
    this.groupsIsAdmin = groupsIsAdmin;
    this.groupsIsCollab = groupsIsCollab;
  }

  public Set<Group> getGroupsIsAdmin() {
    return groupsIsAdmin;
  }

  public Set<Group> getGroupsIsCollab() {
    return groupsIsCollab;
  }

  public void setGroupsIsAdmin(final Set<Group> groupsIsAdmin) {
    this.groupsIsAdmin = groupsIsAdmin;
  }

  public void setGroupsIsCollab(final Set<Group> groupsIsCollab) {
    this.groupsIsCollab = groupsIsCollab;
  }

}
