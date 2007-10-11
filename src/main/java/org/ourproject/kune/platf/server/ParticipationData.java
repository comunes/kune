/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.users.Link;

public class ParticipationData {
    private List<Link> groupsIsAdmin;
    private List<Link> groupsIsCollab;

    public ParticipationData() {
	this(null, null);
    }

    public ParticipationData(final List<Link> groupsIsAdmin, final List<Link> groupsIsCollab) {
	this.groupsIsAdmin = groupsIsAdmin;
	this.groupsIsCollab = groupsIsCollab;
    }

    public List<Link> getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<Link> groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

    public List<Link> getGroupsIsCollab() {
	return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<Link> groupsIsCollab) {
	this.groupsIsCollab = groupsIsCollab;
    }

}
