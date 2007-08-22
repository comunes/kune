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

public class ContainerDTO implements IsSerializable {
    public static final String SEP = "/";
    private Long parentFolderId;
    private Long id;
    private String name;
    private String absolutePath;
    private String typeId;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.ContainerDTO>
     */
    private List childs;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.ContentDTO>
     */
    private List contents;

    public ContainerDTO() {
    }

    public Long getParentFolderId() {
	return parentFolderId;
    }

    public void setParentFolderId(final Long parentFolderId) {
	this.parentFolderId = parentFolderId;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getAbsolutePath() {
	return absolutePath;
    }

    public void setAbsolutePath(final String absolutePath) {
	this.absolutePath = absolutePath;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public List getChilds() {
	return childs;
    }

    public void setChilds(final List childs) {
	this.childs = childs;
    }

    public List getContents() {
	return contents;
    }

    public void setContents(final List contents) {
	this.contents = contents;
    }

    public String getTypeId() {
	return typeId;
    }

    public void setTypeId(final String typeId) {
	this.typeId = typeId;
    }

}
