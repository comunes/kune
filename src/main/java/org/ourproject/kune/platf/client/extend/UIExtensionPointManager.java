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

public class UIExtensionPointManager {

    private final HashMap<String, UIExtensible> uiExtPointsNew;

    public UIExtensionPointManager() {
        uiExtPointsNew = new HashMap<String, UIExtensible>();
    }

    public void registerUIExtensionPoint(final String id, final UIExtensible extensionPoint) {
        uiExtPointsNew.put(id, extensionPoint);
    }

    public void attachToExtensible(final String id, final View viewToAttach) {
        UIExtensible extPoint = getExtensible(id);
        extPoint.attach(id, (Widget) viewToAttach);
    }

    public void detachFromExtensible(final String id, final View viewToAttach) {
        UIExtensible extPoint = getExtensible(id);
        extPoint.detach(id, (Widget) viewToAttach);
    }

    public void registerUIExtensionPoints(final HashMap<String, UIExtensible> extensionPoints) {
        uiExtPointsNew.putAll(extensionPoints);
    }

    private UIExtensible getExtensible(final String id) {
        UIExtensible extPoint = this.uiExtPointsNew.get(id);
        return extPoint;
    }

    /**
     * 
     * Detach all widtgets from a ExtPoint
     * 
     * @param id
     *                id of the ExtensionPoint
     */
    public void detachAll(final String id) {
        UIExtensible extPoint = getExtensible(id);
        extPoint.detachAll(id);
    }

}
