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

    private final HashMap<String, UIExtensionPoint> uiExtPoints;

    public UIExtensionPointManager() {
        uiExtPoints = new HashMap<String, UIExtensionPoint>();
    }

    public void addUIExtensionPoint(final UIExtensionPoint extPoint) {
        uiExtPoints.put(extPoint.getId(), extPoint);
    }

    public void attachToExtensionPoint(final String id, final View viewToAttach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().add((Widget) viewToAttach);
    }

    public void insertToExtensionPoint(final String id, final View viewToAttach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().add((Widget) viewToAttach);
    }

    public void detachFromExtensionPoint(final String id, final View viewToDetach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().remove((Widget) viewToDetach);
    }

    private UIExtensionPoint getExtPoint(final String id) {
        UIExtensionPoint extPoint = this.uiExtPoints.get(id);
        return extPoint;
    }

    public void clearExtensionPoint(final String id) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().clear();
    }

    public void addUIExtensionPoints(final HashMap<String, UIExtensionPoint> extensionPoints) {
        uiExtPoints.putAll(extensionPoints);
    }

}
