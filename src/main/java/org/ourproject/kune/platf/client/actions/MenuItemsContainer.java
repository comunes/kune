package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import com.calclab.suco.client.listener.Listener;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
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
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                Menu menu = null;
                if (actionCollection != null) {
                    menu = new Menu();
                    // Remove if when retrieved rights of siblings
                    for (final ActionItem<T> actionItem : actionCollection) {
                        final ActionDescriptor<T> action = actionItem.getAction();
                        if (actionItem.checkEnabling()) {
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

    public Menu get(String id) {
        return contextMenus.get(id);
    }
}
