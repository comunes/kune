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

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {

    private Long id;
    private String shortName;
    private String longName;
    private GroupType groupType;
    private ContentSimpleDTO defaultContent;
    private ContentSimpleDTO groupFullLogo;
    private LicenseDTO defaultLicense;
    private String workspaceTheme;
    private boolean hasLogo;
    private StateToken stateToken;
    AdmissionTypeDTO admissionType;

    public GroupDTO() {
        this(null, null, GroupType.ORGANIZATION);
    }

    public GroupDTO(final String shortName, final String longName, final GroupType type) {
        this.shortName = shortName;
        this.longName = longName;
        this.groupType = type;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final GroupDTO other = (GroupDTO) obj;
        if (shortName == null) {
            if (other.shortName != null) {
                return false;
            }
        } else if (!shortName.equals(other.shortName)) {
            return false;
        }
        return true;
    }

    public AdmissionTypeDTO getAdmissionType() {
        return admissionType;
    }

    public ContentSimpleDTO getDefaultContent() {
        return defaultContent;
    }

    public LicenseDTO getDefaultLicense() {
        return defaultLicense;
    }

    public ContentSimpleDTO getGroupFullLogo() {
        return groupFullLogo;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public boolean getHasLogo() {
        return hasLogo;
    }

    public Long getId() {
        return id;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public StateToken getStateToken() {
        if (stateToken == null) {
            stateToken = new StateToken(shortName);
        }
        return stateToken;
    }

    public String getWorkspaceTheme() {
        return workspaceTheme;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (defaultLicense == null ? 0 : defaultLicense.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (longName == null ? 0 : longName.hashCode());
        result = prime * result + (shortName == null ? 0 : shortName.hashCode());
        result = prime * result + (groupType == null ? 0 : groupType.hashCode());
        return result;
    }

    public boolean hasLogo() {
        return getHasLogo();
    }

    public boolean isPersonal() {
        return groupType.equals(GroupType.PERSONAL);
    }

    public void setAdmissionType(AdmissionTypeDTO admissionType) {
        this.admissionType = admissionType;
    }

    public void setDefaultContent(final ContentSimpleDTO defaultContent) {
        this.defaultContent = defaultContent;
    }

    public void setDefaultLicense(final LicenseDTO defaultLicense) {
        this.defaultLicense = defaultLicense;
    }

    public void setGroupFullLogo(final ContentSimpleDTO groupFullLogo) {
        this.groupFullLogo = groupFullLogo;
    }

    public void setGroupType(final GroupType groupType) {
        this.groupType = groupType;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLongName(final String name) {
        this.longName = name;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setWorkspaceTheme(final String workspaceTheme) {
        this.workspaceTheme = workspaceTheme;
    }

    @Override
    public String toString() {
        return "GroupDTO[" + shortName + "]";
    }
}
