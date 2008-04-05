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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.HashMap;

import org.ourproject.kune.platf.client.ui.EditableClickListener;
import org.ourproject.kune.platf.client.ui.EditableIconLabel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.VerticalPanel;

class ItemsPanel extends VerticalPanel {
    private final HashMap<String, AbstractImagePrototype> fileIcons;
    private final ContextItemsPresenter presenter;

    public ItemsPanel(final ContextItemsPresenter presenter) {
        this.presenter = presenter;
        addStyleName("kune-NavigationBar");
        addStyleName("Items");
        fileIcons = new HashMap<String, AbstractImagePrototype>();
    }

    public void add(final String name, final String type, final String token, final boolean editable) {
        // IconHyperlink item = new IconHyperlink((AbstractImagePrototype)
        // fileIcons.get(type), name, event);
        EditableIconLabel item = new EditableIconLabel(fileIcons.get(type), name, token, true,
                new EditableClickListener() {
                    public void onEdited(String text) {
                        presenter.onTitleRename(text, token);
                    }
                });
        item.setEditable(editable);
        item.addStyleName("Items");
        item.addStyleName("kune-floatleft");
        add(item);
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
        fileIcons.put(typeName, image);
    }
}
