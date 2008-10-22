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

public class ContainerDTO implements IsSerializable {
    private Long id;
    private String name;
    private String typeId;
    private StateToken stateToken;
    private Long parentFolderId;
    private ContainerSimpleDTO[] absolutePath;
    private List<ContainerSimpleDTO> childs;
    private List<ContentSimpleDTO> contents;

    public ContainerDTO() {
    }

    public ContainerSimpleDTO[] getAbsolutePath() {
        return absolutePath;
    }

    public List<ContainerSimpleDTO> getChilds() {
        return childs;
    }

    public List<ContentSimpleDTO> getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentFolderId() {
        return parentFolderId;
    }

    public StateToken getStateToken() {
        return stateToken;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setAbsolutePath(final ContainerSimpleDTO[] absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setChilds(final List<ContainerSimpleDTO> childs) {
        this.childs = childs;
    }

    public void setContents(final List<ContentSimpleDTO> contents) {
        this.contents = contents;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParentFolderId(final Long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public void setStateToken(final StateToken stateToken) {
        this.stateToken = stateToken;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

}
