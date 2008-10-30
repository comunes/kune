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
 */package org.ourproject.kune.platf.client.actions.toolbar;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class ActionToolbarPanel<T> implements ActionToolbarView<T> {

    public enum Position {
        content, context
    }

    private final HashMap<String, Menu> toolbarMenus;
    private final HashMap<String, ToolbarButton> toolbarButtons;
    private final HashMap<String, Item> menuItems;
    private final Provider<ActionManager> actionManagerProvider;
    private final Position position;
    private final SimpleToolbar topbar;
    private final SimpleToolbar bottombar;
    private final WorkspaceSkeleton ws;

    public ActionToolbarPanel(final Position position, final Provider<ActionManager> actionManagerProvider,
            final WorkspaceSkeleton ws) {
        this.ws = ws;
        topbar = new SimpleToolbar();
        bottombar = new SimpleToolbar();
        this.position = position;
        this.actionManagerProvider = actionManagerProvider;

        toolbarMenus = new HashMap<String, Menu>();
        toolbarButtons = new HashMap<String, ToolbarButton>();
        menuItems = new HashMap<String, Item>();
    }

    public void addButtonAction(final ActionItem<T> actionItem, final boolean enable) {
        final ActionToolbarButtonDescriptor<T> action = (ActionToolbarButtonDescriptor<T>) actionItem.getAction();
        final ActionToolbarPosition pos = action.getActionPosition();
        final ToolbarButton button = new ToolbarButton();
        final String text = action.getText();
        final String iconUrl = action.getIconUrl();
        if (text != null) {
            button.setText(text);
        }
        final String id = action.getId();
        if (id != null) {
            button.setId(id);
        }
        button.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                actionManagerProvider.get().doAction(actionItem);
            }
        });
        if (iconUrl != null) {
            button.setIcon(iconUrl);
        }
        button.setTooltip(action.getToolTip());

        setEnableButton(button, enable);
        final SimpleToolbar toolbar = getToolbar(pos);
        if (action.hasLeftSeparator()) {
            add(toolbar, action.getLeftSeparator());
        }
        toolbar.add(button);
        if (action.hasRightSeparator()) {
            add(toolbar, action.getRightSeparator());
        }
        toolbarButtons.put(genButtonKey(pos, text), button);
    }

    public void addMenuAction(final ActionItem<T> actionItem, final boolean enable) {
        final ActionToolbarMenuDescriptor<T> action = (ActionToolbarMenuDescriptor<T>) actionItem.getAction();
        final String menuTitle = action.getParentMenuTitle();
        final String menuSubTitle = action.getParentSubMenuTitle();
        final ActionToolbarPosition pos = action.getActionPosition();
        final String itemKey = genMenuKey(pos, menuTitle, menuSubTitle, action.getText());
        Item item = menuItems.get(itemKey);
        if (item == null) {
            item = createToolbarMenu(pos, menuTitle, menuSubTitle, actionItem, action.getId());
            menuItems.put(itemKey, item);
        }
        setEnableMenuItem(item, enable);
    }

    public void attach() {
        if (!topbar.isAttached()) {
            switch (position) {
            case content:
                ws.getEntityWorkspace().getContentTopBar().add(topbar);
                ws.getEntityWorkspace().getContentBottomBar().add(bottombar);
                break;
            case context:
            default:
                ws.getEntityWorkspace().getContextTopBar().add(topbar);
                ws.getEntityWorkspace().getContextBottomBar().add(bottombar);
            }
        }
    }

    public void clear() {
        toolbarMenus.clear();
        menuItems.clear();
        toolbarButtons.clear();
        topbar.removeAll();
        bottombar.removeAll();
        getToolbar(ActionToolbarPosition.topbar).removeAll();
        getToolbar(ActionToolbarPosition.bottombar).removeAll();
    }

    public void detach() {
        if (topbar.isAttached()) {
            switch (position) {
            case content:
                ws.getEntityWorkspace().getContentTopBar().remove(topbar);
                ws.getEntityWorkspace().getContentBottomBar().remove(bottombar);
                break;
            case context:
            default:
                ws.getEntityWorkspace().getContextTopBar().remove(topbar);
                ws.getEntityWorkspace().getContextBottomBar().remove(bottombar);
            }
        }
    }

    public void disableAllMenuItems() {
        for (final Item item : menuItems.values()) {
            item.disable();
        }
    }

    public void setButtonEnable(final ActionDescriptor<T> action, final boolean enable) {
        final ActionToolbarPosition pos = ((ActionToolbarDescriptor<T>) action).getActionPosition();
        final ToolbarButton button = toolbarButtons.get(genButtonKey(pos, action.getText()));
        if (button != null) {
            setEnableButton(button, enable);
        } else {
            Log.error("Tryng to enable/disable a non existent toolbar button");
        }
    }

    public void setMenuEnable(final ActionDescriptor<T> action, final boolean enable) {
        final Item item = menuItems.get(genMenuKey((ActionToolbarMenuDescriptor<T>) action));
        if (item != null) {
            setEnableMenuItem(item, enable);
        } else {
            Log.error("Tryng to enable/disable a non existent toolbar menu item");
        }
    }

    private void add(final ActionToolbarPosition toolbar, final Widget widget) {
        getToolbar(toolbar).add(widget);
    }

    private Widget add(final SimpleToolbar toolbar, final ActionToolbarButtonSeparator separator) {
        switch (separator) {
        case fill:
            return toolbar.addFill();
        case separator:
            return toolbar.addSeparator();
        case spacer:
        default:
            return toolbar.addSpacer();
        }
    }

    private Item createToolbarMenu(final ActionToolbarPosition toolBarPos, final String menuTitle,
            final String menuSubTitle, final ActionItem<T> actionItem, String id) {
        final ActionToolbarMenuDescriptor<T> action = (ActionToolbarMenuDescriptor<T>) actionItem.getAction();
        final Item item = new Item(action.getText(), new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                actionManagerProvider.get().doAction(actionItem);
            }
        });
        item.setIcon(action.getIconUrl());
        if (id != null) {
            item.setId(id);
        }

        final String menuKey = genMenuKey(toolBarPos, menuTitle, null, null);
        final String subMenuKey = genMenuKey(toolBarPos, menuTitle, menuSubTitle, null);
        Menu menu = toolbarMenus.get(menuKey);
        Menu subMenu = toolbarMenus.get(subMenuKey);
        if (menuSubTitle != null) {
            if (subMenu == null) {
                subMenu = new Menu();
                final MenuItem subMenuItem = new MenuItem(menuSubTitle, subMenu);
                if (menu == null) {
                    menu = createToolbarMenu(toolBarPos, action.getParentMenuIconUrl(), menuTitle, menuKey);
                }
                menu.addItem(subMenuItem);
                toolbarMenus.put(subMenuKey, subMenu);
            }
            subMenu.addItem(item);

        } else {
            // Menu action without submenu
            if (menu == null) {
                menu = createToolbarMenu(toolBarPos, action.getParentMenuIconUrl(), menuTitle, menuKey);
            }
            menu.addItem(item);
        }
        return item;
    }

    private Menu createToolbarMenu(final ActionToolbarPosition barPosition, final String iconUrl,
            final String menuTitle, final String menuKey) {
        final Menu menu = new Menu();
        final ToolbarButton toolbarMenu = new ToolbarButton(menuTitle);
        toolbarMenu.setMenu(menu);
        if (iconUrl != null) {
            toolbarMenu.setIcon(iconUrl);
        }
        toolbarMenus.put(menuKey, menu);
        add(barPosition, toolbarMenu);
        return menu;
    }

    private String genButtonKey(final ActionToolbarPosition pos, final String actionText) {
        final String basePart = "km-ctx-btn-" + pos.toString().substring(0, 2) + "-" + actionText;
        return basePart;
    }

    private String genMenuKey(final ActionToolbarMenuDescriptor<T> action) {
        return genMenuKey(action.getActionPosition(), action.getParentMenuTitle(), action.getParentSubMenuTitle(),
                action.getText());
    }

    private String genMenuKey(final ActionToolbarPosition pos, final String menuTitle, final String menuSubTitle,
            final String actionText) {

        final String basePart = "km-ctx-menu-" + pos.toString().substring(0, 2) + "-" + menuTitle;
        final String subMenuPart = menuSubTitle != null ? "-subm-" + menuSubTitle : "";
        final String itemPart = actionText != null ? "-item-" + actionText : "";
        return basePart + subMenuPart + itemPart;
    }

    private SimpleToolbar getToolbar(final ActionToolbarPosition pos) {
        switch (pos) {
        case bottombar:
            return bottombar;
        case topbar:
        default:
            return topbar;
        }
    }

    private void setEnableButton(final ToolbarButton button, final boolean enable) {
        if (enable) {
            button.enable();
        } else {
            button.disable();
        }
    }

    private void setEnableMenuItem(final Item item, final boolean enable) {
        if (enable) {
            item.enable();
        } else {
            item.disable();
        }
    }
}
