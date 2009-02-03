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
 \*/
package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class ActionToolbarButtonDescriptor<T> extends ActionToolbarDescriptor<T> {

    private ActionToolbarButtonSeparator leftSeparator;
    private ActionToolbarButtonSeparator rightSeparator;

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionAddCondition<T> addCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, addCondition);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall,
            final ActionAddCondition<T> addCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, addCondition);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarButtonSeparator getLeftSeparator() {
        return leftSeparator;
    }

    public ActionToolbarButtonSeparator getRightSeparator() {
        return rightSeparator;
    }

    public boolean hasLeftSeparator() {
        return leftSeparator != null;
    }

    public boolean hasRightSeparator() {
        return rightSeparator != null;
    }

    public void setLeftSeparator(final ActionToolbarButtonSeparator leftSeparator) {
        this.leftSeparator = leftSeparator;
    }

    public void setRightSeparator(final ActionToolbarButtonSeparator rightSeparator) {
        this.rightSeparator = rightSeparator;
    }
}
