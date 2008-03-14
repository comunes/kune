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

public class GroupListDTO implements IsSerializable {
    public static final String EVERYONE = "EVERYONE";
    public static final String NOBODY = "NOBODY";
    public static final String NORMAL = "NORMAL";
    private List<GroupDTO> list;
    private String mode;

    public GroupListDTO() {
        this(null);
    }

    public GroupListDTO(final List<GroupDTO> list) {
        this.list = list;
    }

    public List<GroupDTO> getList() {
        return list;
    }

    public void setList(final List<GroupDTO> list) {
        this.list = list;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

}
