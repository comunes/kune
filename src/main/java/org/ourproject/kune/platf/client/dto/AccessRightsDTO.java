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

public class AccessRightsDTO implements IsSerializable {
    private boolean isAdministrable;
    private boolean isEditable;
    private boolean isVisible;

    public AccessRightsDTO(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
        this.isAdministrable = isAdministrable;
        this.isEditable = isEditable;
        this.isVisible = isVisible;
    }

    public AccessRightsDTO() {
        this(false, false, false);
    }

    public boolean isAdministrable() {
        return isAdministrable;
    }

    public void setAdministrable(final boolean isAdministrable) {
        this.isAdministrable = isAdministrable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(final boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String toString() {
        return "[" + isAdministrable + ", " + isEditable + ", " + isVisible + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final AccessRightsDTO other = (AccessRightsDTO) obj;
        if (isAdministrable != other.isAdministrable) {
            return false;
        }
        if (isEditable != other.isEditable) {
            return false;
        }
        if (isVisible != other.isVisible) {
            return false;
        }
        return true;
    }

}
