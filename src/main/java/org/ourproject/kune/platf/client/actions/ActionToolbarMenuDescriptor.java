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
 */package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.listener.Listener;

public class ActionToolbarMenuDescriptor<T> extends ActionToolbarDescriptor<T> {

    private String parentMenuTitle;
    private String parentMenuIconUrl;
    private String parentSubMenuTitle;

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public String getParentMenuIconUrl() {
        return parentMenuIconUrl;
    }

    public String getParentMenuTitle() {
        return parentMenuTitle;
    }

    public String getParentSubMenuTitle() {
        return parentSubMenuTitle;
    }

    public void setParentMenuIconUrl(final String parentMenuIconUrl) {
        this.parentMenuIconUrl = parentMenuIconUrl;
    }

    public void setParentMenuTitle(final String parentMenuTitle) {
        this.parentMenuTitle = parentMenuTitle;
    }

    public void setParentSubMenuTitle(final String parentSubMenuTitle) {
        if (parentMenuTitle == null) {
            Log.warn("Please set parentMenuTitle before");
        }
        this.parentSubMenuTitle = parentSubMenuTitle;
    }
}
