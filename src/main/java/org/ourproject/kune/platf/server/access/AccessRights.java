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
package org.ourproject.kune.platf.server.access;

public class AccessRights {
    boolean isAdministrable;
    boolean isEditable;
    boolean isVisible;

    public AccessRights() {
        this(false, false, false);
    }

    public AccessRights(final boolean isAdministrable, final boolean isEditable, final boolean isVisible) {
        this.isAdministrable = isAdministrable;
        this.isEditable = isEditable;
        this.isVisible = isVisible;
    }

    public boolean isAdministrable() {
        return isAdministrable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setAdministrable(final boolean isAdministrable) {
        this.isAdministrable = isAdministrable;
    }

    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setVisible(final boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String toString() {
        return "AccessRights[a: " + isAdministrable + ", e: " + isEditable + ", v: " + isVisible + "]";
    }
}
