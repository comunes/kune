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
package org.ourproject.kune.platf.client.actions.toolbar;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.FlowToolbar;
import org.ourproject.kune.platf.client.ui.SimpleToolbar;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class ActionToolbarPanel<T> implements ActionToolbarView<T> {

    private final HashMap<String, Menu> menus;
    private final HashMap<String, Item> menuItems;
    private final HashMap<String, ToolbarButton> buttons;
    private final Provider<ActionManager> actionManagerProvider;
    protected final AbstractToolbar toolbar;

    public ActionToolbarPanel(final Provider<ActionManager> actionManagerProvider) {
        this(actionManagerProvider, false);
    }

    public ActionToolbarPanel(final Provider<ActionManager> actionManagerProvider, boolean flow) {
        if (flow) {
            toolbar = new FlowToolbar();
        } else {
            toolbar = new SimpleToolbar();
        }
        // toolbar.setWidth("100%");
        this.actionManagerProvider = actionManagerProvider;

        menus = new HashMap<String, Menu>();
        menuItems = new HashMap<String, Item>();
        buttons = new HashMap<String, ToolbarButton>();
    }

    public ActionToolbarButton<T> addButtonAction(final ActionItem<T> actionItem, final boolean enable,
            final boolean isPushButton, final boolean pressed) {
        final ActionToolbarButtonDescriptor<T> action = (ActionToolbarButtonDescriptor<T>) actionItem.getAction();
        String id = generateId(action);
        ActionToolbarButton<T> button = new ActionToolbarButton<T>(action, id, enable, isPushButton, pressed,
                new Listener0() {
                    public void onEvent() {
                        actionManagerProvider.get().doAction(actionItem);
                    }
                });
        if (action.hasLeftSeparator()) {
            add(toolbar, action.getLeftSeparator());
        }
        addFloatStyle(button);
        toolbar.add(button);
        if (action.hasRightSeparator()) {
            add(toolbar, action.getRightSeparator());
        }
        buttons.put(genButtonKey(id), button);
        return button;
    }

    public void addMenuAction(final ActionItem<T> actionItem, final boolean enable) {
        final ActionToolbarMenuDescriptor<T> action = (ActionToolbarMenuDescriptor<T>) actionItem.getAction();
        final String menuTitle = action.getParentMenuTitle();
        final String menuSubTitle = action.getParentSubMenuTitle();
        final String menuTooltip = action.getParentMenuTooltip();
        final ActionToolbarPosition pos = action.getActionPosition();
        final String itemKey = genMenuKey(pos, menuTitle, action.getParentMenuTooltip(), menuSubTitle, action.getText());
        Item item = menuItems.get(itemKey);
        if (item == null) {
            item = createToolbarMenu(pos, menuTitle, menuTooltip, menuSubTitle, actionItem, generateId(action));
            menuItems.put(itemKey, item);
        }
        setEnableMenuItem(item, enable);
    }

    public void attach() {
    }

    public void clear() {
        menus.clear();
        menuItems.clear();
        buttons.clear();
        toolbar.removeAll();
    }

    public void detach() {
    }

    public int getLeftPosition(ActionDescriptor<T> action) {
        final ToolbarButton button = findButton(action);
        if (button != null) {
            return button.getAbsoluteLeft();
        }
        return 0;
    }

    public AbstractToolbar getToolbar() {
        return toolbar;
    }

    public int getTopPosition(ActionDescriptor<T> action) {
        final ToolbarButton button = findButton(action);
        if (button != null) {
            return button.getAbsoluteTop();
        }
        return 0;
    }

    public void hideAllMenus() {
        for (Menu menu : menus.values()) {
            menu.hide(true);
        }
    }

    public void setButtonEnable(final ActionDescriptor<T> action, final boolean enable) {
        final ToolbarButton button = findButton(action);
        if (button != null) {
            setEnableButton(button, enable);
        } else {
            Log.error("Tryng to enable/disable a non existent toolbar button");
        }
    }

    public void setCleanStyle() {
        toolbar.setCleanStyle();
    }

    public void setNormalStyle() {
        toolbar.setNormalStyle();
    }

    public void setParentMenuTitle(ActionToolbarMenuDescriptor<T> action, String origTitle, String origTooltip,
            String newTitle) {
        final String menuKey = genMenuKey(action.getActionPosition(), origTitle, origTooltip, null, null);
        Menu menu = menus.get(menuKey);
        if (menu != null) {
            menu.setTitle(newTitle);
        } else {
            Log.error("Tryng to rename a non existent menu");
        }
    }

    public void setPushButtonPressed(final ActionDescriptor<T> action, final boolean pressed) {
        final ToolbarButton button = findButton(action);
        if (button != null) {
            if (button.isEnableToggle()) {
                // Log.debug("Setting button pressed: " + pressed);
                button.toggle(pressed);
            } else {
                Log.error("Tryng to set pressed a toolbar button not of type push button");
            }
        } else {
            Log.error("Tryng to set pressed a non existent toolbar push button");
        }
    }

    private Widget add(final AbstractToolbar toolbar, final ActionToolbarButtonSeparator separator) {
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

    private void add(final Widget widget) {
        addFloatStyle(widget);
        toolbar.add(widget);
    }

    private void addFloatStyle(Widget widget) {
        if (toolbar instanceof FlowToolbar) {
            widget.addStyleName("kune-floatleft");
        }
    }

    private void addSeparator(Menu menu, boolean separator) {
        if (separator) {
            menu.addSeparator();
        }
    }

    private Item createToolbarMenu(final ActionToolbarPosition toolBarPos, final String menuTitle,
            final String menuTooltip, final String menuSubTitle, final ActionItem<T> actionItem, String id) {
        final ActionToolbarMenuDescriptor<T> action = (ActionToolbarMenuDescriptor<T>) actionItem.getAction();
        final Item item;
        if (action instanceof ActionToolbarMenuRadioDescriptor) {
            CheckItem checkItem = new CheckItem();
            ActionToolbarMenuRadioDescriptor<T> radioDescriptor = (ActionToolbarMenuRadioDescriptor<T>) action;
            checkItem.setGroup(radioDescriptor.getGroup());
            checkItem.setChecked(radioDescriptor.mustBeChecked());
            item = checkItem;
        } else if (action instanceof ActionToolbarMenuCheckItemDescriptor) {
            CheckItem checkItem = new CheckItem();
            ActionToolbarMenuCheckItemDescriptor<T> checkItemDescriptor = (ActionToolbarMenuCheckItemDescriptor<T>) action;
            checkItem.setChecked(checkItemDescriptor.getMustBeChecked().mustBeChecked());
            item = checkItem;
        } else {
            item = new Item();
        }
        item.setText(genMenuItemText(action));
        BaseItemListenerAdapter clickListener = new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                actionManagerProvider.get().doAction(actionItem);
            }
        };
        item.addListener(clickListener);
        String iconCls = action.getIconCls();
        String iconUrl = action.getIconUrl();
        if (iconCls != null) {
            item.setIconCls(iconCls);
        }
        if (iconUrl != null) {
            item.setIcon(iconUrl);
        }
        // ToolTip tip = new ToolTip();
        // tip.setHtml(action.getToolTip());
        // tip.setShowDelay(5000);
        item.setTitle(action.getToolTip());
        if (id != null) {
            item.setId(id);
        }

        final String menuKey = genMenuKey(toolBarPos, menuTitle, menuTooltip, null, null);
        final String subMenuKey = genMenuKey(toolBarPos, menuTitle, menuTooltip, menuSubTitle, null);
        Menu menu = menus.get(menuKey);
        Menu subMenu = menus.get(subMenuKey);
        if (menuSubTitle != null) {
            if (subMenu == null) {
                subMenu = new Menu();
                final MenuItem subMenuItem = new MenuItem(menuSubTitle, subMenu);
                if (menu == null) {
                    menu = createToolbarMenu(toolBarPos, action.getParentMenuIconUrl(), action.getParentMenuIconCls(),
                            menuTitle, menuKey, "", action.getPosition());
                }
                menu.addItem(subMenuItem);
                menus.put(subMenuKey, subMenu);
            }
            addSeparator(subMenu, action.hasTopSeparator());
            subMenu.addItem(item);
            addSeparator(subMenu, action.hasBottomSeparator());
        } else {
            // Menu action without submenu
            if (menu == null) {
                menu = createToolbarMenu(toolBarPos, action.getParentMenuIconUrl(), action.getParentMenuIconCls(),
                        menuTitle, menuKey, action.getParentMenuTooltip(), action.getPosition());
            }
            addSeparator(menu, action.hasTopSeparator());
            menu.addItem(item);
            addSeparator(menu, action.hasBottomSeparator());
        }
        return item;
    }

    private Menu createToolbarMenu(final ActionToolbarPosition barPosition, final String iconUrl, final String iconCls,
            final String menuTitle, final String menuKey, final String menuTooltip, int position) {
        final Menu menu = new Menu();
        menu.setShadow(true);
        final ToolbarButton toolbarMenu = new ToolbarButton(menuTitle);
        toolbarMenu.setMenu(menu);
        if (iconUrl != null) {
            toolbarMenu.setIcon(iconUrl);
        }
        if (iconCls != null) {
            toolbarMenu.setIconCls(iconCls);
        }
        if (menuTooltip != null) {
            toolbarMenu.setTooltip(menuTooltip);
        }
        menus.put(menuKey, menu);
        add(toolbarMenu);
        return menu;
    }

    private ToolbarButton findButton(final ActionDescriptor<T> action) {
        final ToolbarButton button = buttons.get(genButtonKey(action.getId()));
        return button;
    }

    private String genButtonKey(final String id) {
        final String basePart = "km-act-btn-" + id;
        return basePart;
    }

    private String generateId(final ActionDescriptor<T> action) {
        String id = Ext.generateId();
        action.setId(id);
        return id;
    }

    private String genMenuItemText(final ActionToolbarMenuDescriptor<T> action) {
        // HorizontalPanel hp = new HorizontalPanel();
        // Label title = new Label(action.getText());
        // hp.add(title);
        // hp.setCellWidth(title, "100%");
        // hp.add(new Label(action.getShortcutToS(i 18n)));
        // return hp.getElement().getInnerHTML();
        return action.getText() + action.getShortcutToS();
    }

    private String genMenuKey(final ActionToolbarPosition pos, final String menuTitle, final String menuTooltip,
            final String menuSubTitle, final String actionText) {

        final String basePart = "km-atp-menu-" + pos.toString().substring(0, 2) + "-" + menuTitle + "-" + menuTooltip;
        final String subMenuPart = menuSubTitle != null ? "-subm-" + menuSubTitle : "";
        final String itemPart = actionText != null ? "-item-" + actionText : "";
        return basePart + subMenuPart + itemPart;
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
