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

package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParticipationDataDTO implements IsSerializable {

    private List<LinkDTO> groupsIsAdmin;
    private List<LinkDTO> groupsIsCollab;

    public ParticipationDataDTO() {
        this(null, null);
    }

    public ParticipationDataDTO(final List<LinkDTO> groupsIsAdmin, final List<LinkDTO> groupsIsCollab) {
        this.groupsIsAdmin = groupsIsAdmin;
        this.groupsIsCollab = groupsIsCollab;
    }

    public List<LinkDTO> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<LinkDTO> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public List<LinkDTO> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<LinkDTO> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

}
