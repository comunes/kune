/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
/*
 *
 * ((e)) emite: A pure gwt (Google Web Toolkit) xmpp (jabber) library
 *
 * (c) 2008-2009 The emite development team (see CREDITS for details)
 * This file is part of emite.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.gridmenu;

import java.util.Iterator;

import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class CustomMenu<T> {
    private final T param;
    private final Menu menu;

    /**
     * 
     * @param param
     *            the uniq id (for instance a group short name, a xmpp uri of
     *            the object asociated with this menu
     * 
     */
    public CustomMenu(final T param) {
        this.param = param;
        menu = new Menu();
    }

    public void addMenuItem(final MenuItem<T> menuItem) {
        final Item item = new Item(menuItem.getTitle());
        item.setIcon(menuItem.getIcon());
        menu.addItem(item);
        item.addListener(new BaseItemListenerAdapter() {
            public void onClick(final BaseItem item, final EventObject e) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() {
                        menuItem.fire(param);
                    }
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void addMenuItemList(final MenuItemCollection list) {
        for (final Iterator iterator = list.iterator(); iterator.hasNext();) {
            final MenuItem item = (MenuItem) iterator.next();
            addMenuItem(item);
        }
    }

    public void showMenu(final EventObject e) {
        if (menu.getItems().length > 0) {
            menu.showAt(e.getXY());
        }
    }

}
