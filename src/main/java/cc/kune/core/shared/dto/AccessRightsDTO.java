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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessRightsDTO implements IsSerializable {
    private boolean administrable;
    private boolean editable;
    private boolean visible;

    public AccessRightsDTO() {
        this(false, false, false);
    }

    public AccessRightsDTO(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
        this.administrable = isAdministrable;
        this.editable = isEditable;
        this.visible = isVisible;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccessRightsDTO other = (AccessRightsDTO) obj;
        if (administrable != other.administrable) {
            return false;
        }
        if (editable != other.editable) {
            return false;
        }
        if (visible != other.visible) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (administrable ? 1231 : 1237);
        result = prime * result + (editable ? 1231 : 1237);
        result = prime * result + (visible ? 1231 : 1237);
        return result;
    }

    public boolean isAdministrable() {
        return administrable;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAdministrable(final boolean administrable) {
        this.administrable = administrable;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "AccessRightsDTO[a: " + administrable + ", e: " + editable + ", v: " + visible + "]";
    }
}
