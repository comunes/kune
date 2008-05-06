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

import org.ourproject.kune.platf.client.View;

public class UIExtensionPair {
    private final String id;
    private final View view;

    /**
     * 
     * @param id
     *                id of the ExtensionPoint
     * @param view
     *                view (a widget) to attach/detach to the ExtensionPoint
     */
    public UIExtensionPair(final String id, final View view) {
        this.id = id;
        this.view = view;
    }

    /**
     * 
     * @return the id of the ExtensionPoint
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return the view to attach/dettach to the EP
     */
    public View getView() {
        return view;
    }

}
