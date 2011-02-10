/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.oldsn;

public class UIStatus {

    private boolean visible;
    private boolean enabled;

    public UIStatus(final boolean visible, final boolean enabled) {
        this.visible = visible;
        this.enabled = enabled;
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
        final UIStatus other = (UIStatus) obj;
        if (enabled != other.enabled) {
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
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + (visible ? 1231 : 1237);
        return result;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "(v: " + visible + ", e:" + enabled + ")";
    }
}
