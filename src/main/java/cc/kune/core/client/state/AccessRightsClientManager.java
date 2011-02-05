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
package cc.kune.core.client.state;

import cc.kune.core.shared.domain.utils.AccessRights;

import com.calclab.suco.client.events.Listener2;

public class AccessRightsClientManager {
    private final AccessRights previousRights;

    // private final Event2<AccessRights, AccessRights> onRightsChanged;

    public AccessRightsClientManager(final StateManager stateManager) {
        this.previousRights = null;
        // this.onRightsChanged = new Event2<AccessRights , AccessRights
        // >("onRightsChanged");
        // stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
        // public void onEvent(final StateAbstractDTO newState) {
        // final AccessRights rights = newState.getGroupRights();
        // if (!rights.equals(previousRights)) {
        // onRightsChanged.fire(previousRights, rights);
        // previousRights = rights;
        // }
        // }
        // });
    }

    public void onRightsChanged(final Listener2<AccessRights, AccessRights> listener) {
        // onRightsChanged.add(listener);
    }
}
