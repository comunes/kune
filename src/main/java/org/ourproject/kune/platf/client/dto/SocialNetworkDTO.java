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
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SocialNetworkDTO implements IsSerializable {

    AccessListsDTO accessLists;

    GroupListDTO pendingCollaborators;

    public SocialNetworkDTO() {
        accessLists = new AccessListsDTO();
        pendingCollaborators = new GroupListDTO();
    }

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public GroupListDTO getPendingCollaborators() {
        return pendingCollaborators;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        this.accessLists = accessLists;
    }

    public void setPendingCollaborators(final GroupListDTO pendingCollaborators) {
        this.pendingCollaborators = pendingCollaborators;
    }

    public String toString() {
        return "SocialNetworkDTO[accessList: " + accessLists + "; pendingsCollabs: " + pendingCollaborators + "]";
    }

}
