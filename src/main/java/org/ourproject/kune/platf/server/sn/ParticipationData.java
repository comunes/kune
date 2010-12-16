/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.sn;

import java.util.List;

import cc.kune.domain.Group;

public class ParticipationData {
    private List<Group> groupsIsAdmin;
    private List<Group> groupsIsCollab;

    public ParticipationData() {
        this(null, null);
    }

    public ParticipationData(final List<Group> groupsIsAdmin, final List<Group> groupsIsCollab) {
        this.groupsIsAdmin = groupsIsAdmin;
        this.groupsIsCollab = groupsIsCollab;
    }

    public List<Group> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public List<Group> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public void setGroupsIsAdmin(final List<Group> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public void setGroupsIsCollab(final List<Group> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

}
