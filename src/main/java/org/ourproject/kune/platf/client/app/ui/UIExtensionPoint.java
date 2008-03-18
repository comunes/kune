/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.app.ui;

import com.google.gwt.user.client.ui.CellPanel;

public class UIExtensionPoint {
    // Not yet implemented EP:
    // public static final String CONTENT_TOOLBAR_RIGHT =
    // "ws.entity.content.toolbar.right";
    // public static final String CONTENT_BOTTOM_TOOLBAR_RIGHT =
    // "ws.entity.content.bottomtb.right";
    // public static final String CONTENT_BOTTOM_TOOLBAR_LEFT =
    // "ws.entity.content.bottomtb.left";
    public static final String CONTENT_TOOLBAR_LEFT = "ws.entity.content.toolbar.left";
    public static final String CONTENT_BOTTOM_ICONBAR = "ws.site.bottom.iconbar";

    private final String id;
    private final CellPanel panel;

    public UIExtensionPoint(final String id, final CellPanel panel) {
        this.id = id;
        this.panel = panel;
    }

    public String getId() {
        return id;
    }

    public CellPanel getPanel() {
        return panel;
    }

}
