/*
 *
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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.HashMap;

import org.ourproject.kune.platf.client.ui.IconHyperlink;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.VerticalPanel;

class ItemsPanel extends VerticalPanel {
    private final HashMap fileIcons;

    public ItemsPanel() {
	addStyleName("kune-NavigationBar");
	addStyleName("Items");
	fileIcons = new HashMap();
    }

    public void add(final String name, final String type, final String event) {
	GWT.log("Item: " + name + " type: " + type, null);
	add(new IconHyperlink(((AbstractImagePrototype) fileIcons.get(type)), name, event));
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
	fileIcons.put(typeName, image);
    }
}
