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
package org.ourproject.kune.platf.client.actions;


import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener0;

public class ActionToolbarMenuCheckItemDescriptor<T> extends ActionToolbarMenuDescriptor<T> {

    private final ActionCheckedCondition mustBeChecked;

    public ActionToolbarMenuCheckItemDescriptor(final AccessRolDTO accessRol,
            final ActionToolbarPosition toolbarPosition, final Listener0 onPerformCall,
            final ActionCheckedCondition mustBeChecked) {
        super(accessRol, toolbarPosition, onPerformCall);
        this.mustBeChecked = mustBeChecked;
    }

    public ActionCheckedCondition getMustBeChecked() {
        return mustBeChecked;
    }

}
