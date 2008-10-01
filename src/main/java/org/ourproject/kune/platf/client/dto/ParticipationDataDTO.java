/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParticipationDataDTO implements IsSerializable {

    private List<GroupDTO> groupsIsAdmin;
    private List<GroupDTO> groupsIsCollab;

    public ParticipationDataDTO() {
	this(null, null);
    }

    public ParticipationDataDTO(final List<GroupDTO> groupsIsAdmin, final List<GroupDTO> groupsIsCollab) {
	this.groupsIsAdmin = groupsIsAdmin;
	this.groupsIsCollab = groupsIsCollab;
    }

    public List<GroupDTO> getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public List<GroupDTO> getGroupsIsCollab() {
	return groupsIsCollab;
    }

    public void setGroupsIsAdmin(final List<GroupDTO> groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

    public void setGroupsIsCollab(final List<GroupDTO> groupsIsCollab) {
	this.groupsIsCollab = groupsIsCollab;
    }

    @Override
    public String toString() {
	return "ParticipationDataDTO[admin in: " + groupsIsAdmin.toString() + ", collab in: "
		+ groupsIsCollab.toString() + "]";
    }
}
