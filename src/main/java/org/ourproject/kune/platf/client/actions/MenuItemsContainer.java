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

import java.util.HashMap;

import com.calclab.suco.client.events.Listener;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class MenuItemsContainer<T> {
    private final HashMap<String, Menu> contextMenus;

    public MenuItemsContainer() {
        contextMenus = new HashMap<String, Menu>();
    }

    public void clear() {
        contextMenus.clear();
    }

    public void createItemMenu(final String id, final ActionItemCollection<T> actionCollection,
            final Listener<ActionItem<T>> listener) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                Menu menu = null;
                if (actionCollection != null) {
                    menu = new Menu();
                    // Remove if when retrieved rights of siblings
                    for (final ActionItem<T> actionItem : actionCollection) {
                        final ActionDescriptor<T> action = actionItem.getAction();
                        if (actionItem.mustBeEnabled()) {
                            final Item item = new Item(action.getText());
                            item.setIcon(action.getIconUrl());
                            menu.addItem(item);
                            item.addListener(new BaseItemListenerAdapter() {
                                @Override
                                public void onClick(final BaseItem item, final EventObject e) {
                                    listener.onEvent(actionItem);
                                }
                            });
                        }
                    }
                }
                contextMenus.put(id, menu);
            }
        });
    }

    public Menu get(final String id) {
        return contextMenus.get(id);
    }
}
