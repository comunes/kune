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
package org.ourproject.kune.platf.client.extend;

import java.util.HashMap;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Widget;

public class ExtensibleWidgetsManager {

    private final HashMap<String, ExtensibleWidget> extWidgets;

    public ExtensibleWidgetsManager() {
        extWidgets = new HashMap<String, ExtensibleWidget>();
    }

    public void registerExtensibleWidget(final String id, final ExtensibleWidget extWidget) {
        extWidgets.put(id, extWidget);
    }

    public void attachToExtensible(final String id, final View viewToAttach) {
        ExtensibleWidget extPoint = getExtensible(id);
        extPoint.attach(id, (Widget) viewToAttach);
    }

    public void detachFromExtensible(final String id, final View viewToAttach) {
        ExtensibleWidget extPoint = getExtensible(id);
        extPoint.detach(id, (Widget) viewToAttach);
    }

    public void registerExtensibleWidgets(final HashMap<String, ExtensibleWidget> extWidgets) {
        extWidgets.putAll(extWidgets);
    }

    private ExtensibleWidget getExtensible(final String id) {
        ExtensibleWidget extWidget = this.extWidgets.get(id);
        return extWidget;
    }

    /**
     * 
     * Detach all widtgets from a extensible widget
     * 
     * @param id
     *                id of the extensible widget
     */
    public void detachAll(final String id) {
        ExtensibleWidget extWidget = getExtensible(id);
        extWidget.detachAll(id);
    }

}
