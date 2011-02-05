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
 */
package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Menu;

public abstract class AbstractMenuGui extends AbstractGuiItem {

    protected final Menu menu;

    public AbstractMenuGui(final GuiActionDescrip descriptor) {
        super(descriptor);
        menu = new Menu();
        menu.setShadow(true);
    }

    public void add(final BaseItem item) {
        menu.addItem(item);
    }

    public void addSeparator() {
        menu.addSeparator();
    }

    @Override
    public void configureItemFromProperties() {
        super.configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.removeAll();
                }
            }
        });
    }

    public void insert(final int position, final BaseItem item) {
        menu.insert(position, item);
    }

    public void show(final int x, final int y) {
        menu.showAt(x, y);
    }

    public void show(final String id) {
        menu.show(id);
    }

}