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
    public static final String ORGANIZATION = "ORGANIZATION";
    public static final String COMMUNITY = "COMMUNITY";
    public static final String PROJECT = "PROJECT";
    public static final String ORPHANED_PROJECT = "ORPHANED_PROJECT";
    public static final String PERSONAL = "PERSONAL";

    private Long id;
    private String shortName;
    private String longName;
    private String publicDesc;
    private String type;

    private String defaultToolName;
    private Long defaultFolderId;
    private Long defaultContentId;

    private LicenseDTO defaultLicense;

    private String workspaceTheme;

    public GroupDTO() {
        this(null, null, null, ORGANIZATION);
    }

    public GroupDTO(final String shortName, final String longName, final String publicDesc, final String type) {
        this.shortName = shortName;
        this.longName = longName;
        this.publicDesc = publicDesc;
        this.type = type;
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

    public String getType() {
        return type;
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

    public void setType(final String type) {
        this.type = type;
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

    public LicenseDTO getDefaultLicense() {
        return defaultLicense;
    }

    public void setDefaultLicense(final LicenseDTO defaultLicense) {
        this.defaultLicense = defaultLicense;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (defaultContentId == null ? 0 : defaultContentId.hashCode());
        result = prime * result + (defaultFolderId == null ? 0 : defaultFolderId.hashCode());
        result = prime * result + (defaultLicense == null ? 0 : defaultLicense.hashCode());
        result = prime * result + (defaultToolName == null ? 0 : defaultToolName.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (longName == null ? 0 : longName.hashCode());
        result = prime * result + (publicDesc == null ? 0 : publicDesc.hashCode());
        result = prime * result + (shortName == null ? 0 : shortName.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        ;
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
        if (defaultLicense == null) {
            if (other.defaultLicense != null) {
                return false;
            }
        } else if (!defaultLicense.equals(other.defaultLicense)) {
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

    public String getWorkspaceTheme() {
        return workspaceTheme;
    }

    public void setWorkspaceTheme(final String workspaceTheme) {
        this.workspaceTheme = workspaceTheme;
    }

}
