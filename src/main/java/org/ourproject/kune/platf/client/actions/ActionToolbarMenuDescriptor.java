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
 \*/
package org.ourproject.kune.platf.client.actions;


import cc.kune.core.shared.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class ActionToolbarMenuDescriptor<T> extends ActionToolbarDescriptor<T> {

    private String parentMenuTitle;
    private String parentMenuIconUrl;
    private String parentMenuIconCls;
    private String parentSubMenuTitle;
    private String parentMenuTooltip;
    private boolean topSeparator = false;
    private boolean bottomSeparator = false;

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarMenuDescriptor(final ActionDescriptor<T> copy) {
        super(copy);
    }

    public String getParentMenuIconCls() {
        return parentMenuIconCls;
    }

    public String getParentMenuIconUrl() {
        return parentMenuIconUrl;
    }

    public String getParentMenuTitle() {
        return parentMenuTitle;
    }

    public String getParentMenuTooltip() {
        return parentMenuTooltip;
    }

    public String getParentSubMenuTitle() {
        return parentSubMenuTitle;
    }

    public boolean hasBottomSeparator() {
        return bottomSeparator;
    }

    public boolean hasTopSeparator() {
        return topSeparator;
    }

    public void setBottomSeparator(final boolean bottomSeparator) {
        this.bottomSeparator = bottomSeparator;
    }

    public void setParentMenuIconCls(final String parentMenuIconCls) {
        this.parentMenuIconCls = parentMenuIconCls;
    }

    public void setParentMenuIconUrl(final String parentMenuIconUrl) {
        this.parentMenuIconUrl = parentMenuIconUrl;
    }

    public void setParentMenuTitle(final String parentMenuTitle) {
        this.parentMenuTitle = parentMenuTitle;
    }

    public void setParentMenuTooltip(final String parentMenuTooltip) {
        this.parentMenuTooltip = parentMenuTooltip;
    }

    public void setParentSubMenuTitle(final String parentSubMenuTitle) {
        if (parentMenuTitle == null) {
            Log.warn("Please set parentMenuTitle before");
        }
        this.parentSubMenuTitle = parentSubMenuTitle;
    }

    public void setTopSeparator(final boolean topSeparator) {
        this.topSeparator = topSeparator;
    }
}
