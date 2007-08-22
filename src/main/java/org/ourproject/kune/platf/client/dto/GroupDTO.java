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

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {
    public static final int TYPE_ORGANIZATION = 0;
    public static final int TYPE_COMNUNITY = 1;
    public static final int TYPE_PROJECT = 2;
    public static final int TYPE_PERSONAL = 3;

    private Long id;
    private String shortName;
    private String longName;
    private String publicDesc;
    private String defaultLicenseShortName;
    private int type;

    private String defaultToolName;
    private Long defaultFolderId;
    private Long defaultContentId;

    public GroupDTO() {
	this(null, null, null, null, TYPE_ORGANIZATION);
    }

    public GroupDTO(final String name, final String shortName, final String publicDesc,
	    final String defaultLicenseShortName, final int type) {
	this.longName = name;
	this.shortName = shortName;
	this.publicDesc = publicDesc;
	this.defaultLicenseShortName = defaultLicenseShortName;
	this.type = type;
    }

    public String getDefaultLicense() {
	return defaultLicenseShortName;
    }

    public Long getId() {
	return id;
    }

    public String getLongName() {
	return longName;
    }

    public String getPublicDesc() {
	return publicDesc;
    }

    public String getShortName() {
	return shortName;
    }

    public int getType() {
	return type;
    }

    public void setDefaultLicense(final String defaultLicense) {
	this.defaultLicenseShortName = defaultLicense;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setLongName(final String name) {
	this.longName = name;
    }

    public void setPublicDesc(final String publicDesc) {
	this.publicDesc = publicDesc;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

    public void setType(final int type) {
	this.type = type;
    }

    public String getDefaultLicenseShortName() {
	return defaultLicenseShortName;
    }

    public void setDefaultLicenseShortName(final String defaultLicenseShortName) {
	this.defaultLicenseShortName = defaultLicenseShortName;
    }

    public Long getDefaultFolderId() {
	return defaultFolderId;
    }

    public void setDefaultFolderId(final Long defaultFolderId) {
	this.defaultFolderId = defaultFolderId;
    }

    public Long getDefaultContentId() {
	return defaultContentId;
    }

    public void setDefaultContentId(final Long defaultContentId) {
	this.defaultContentId = defaultContentId;
    }

    public String getDefaultToolName() {
	return defaultToolName;
    }

    public void setDefaultToolName(final String defaultToolName) {
	this.defaultToolName = defaultToolName;
    }
}
