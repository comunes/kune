/*
 *
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

public class AccessListsDTO implements IsSerializable {

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List admin;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List edit;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List view;

    public AccessListsDTO() {
	this(null, null, null);
    }

    public AccessListsDTO(final List admin, final List edit, final List view) {
	this.admin = admin;
	this.edit = edit;
	this.view = view;
    }

    public List getAdmin() {
	return admin;
    }

    public void setAdmin(final List admin) {
	this.admin = admin;
    }

    public List getEdit() {
	return edit;
    }

    public void setEdit(final List edit) {
	this.edit = edit;
    }

    public List getView() {
	return view;
    }

    public void setView(final List view) {
	this.view = view;
    }

}
