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

    private LicenseDTO defaultLicense;

    public GroupDTO() {
	this(null, null, null, null, TYPE_ORGANIZATION);
    }

    public GroupDTO(final String shortName, final String longName, final String publicDesc,
	    final String defaultLicenseShortName, final int type) {
	this.shortName = shortName;
	this.longName = longName;
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

    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((defaultContentId == null) ? 0 : defaultContentId.hashCode());
	result = prime * result + ((defaultFolderId == null) ? 0 : defaultFolderId.hashCode());
	result = prime * result + ((defaultLicenseShortName == null) ? 0 : defaultLicenseShortName.hashCode());
	result = prime * result + ((defaultToolName == null) ? 0 : defaultToolName.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((longName == null) ? 0 : longName.hashCode());
	result = prime * result + ((publicDesc == null) ? 0 : publicDesc.hashCode());
	result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
	result = prime * result + type;
	return result;
    }

    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	final GroupDTO other = (GroupDTO) obj;
	if (defaultContentId == null) {
	    if (other.defaultContentId != null) {
		return false;
	    }
	} else if (!defaultContentId.equals(other.defaultContentId)) {
	    return false;
	}
	if (defaultFolderId == null) {
	    if (other.defaultFolderId != null) {
		return false;
	    }
	} else if (!defaultFolderId.equals(other.defaultFolderId)) {
	    return false;
	}
	if (defaultLicenseShortName == null) {
	    if (other.defaultLicenseShortName != null) {
		return false;
	    }
	} else if (!defaultLicenseShortName.equals(other.defaultLicenseShortName)) {
	    return false;
	}
	if (defaultToolName == null) {
	    if (other.defaultToolName != null) {
		return false;
	    }
	} else if (!defaultToolName.equals(other.defaultToolName)) {
	    return false;
	}
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
	    return false;
	}
	if (longName == null) {
	    if (other.longName != null) {
		return false;
	    }
	} else if (!longName.equals(other.longName)) {
	    return false;
	}
	if (publicDesc == null) {
	    if (other.publicDesc != null) {
		return false;
	    }
	} else if (!publicDesc.equals(other.publicDesc)) {
	    return false;
	}
	if (shortName == null) {
	    if (other.shortName != null) {
		return false;
	    }
	} else if (!shortName.equals(other.shortName)) {
	    return false;
	}
	if (type != other.type) {
	    return false;
	}
	return true;
    }

    public void setDefaultLicense(LicenseDTO defaultLicense) {
	this.defaultLicense = defaultLicense;
    }
}
