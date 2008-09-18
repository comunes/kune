package org.ourproject.kune.platf.client.actions.toolbar;

import java.util.ArrayList;
import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.provider.Provider;
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

public class ActionToolbarPanel implements ActionToolbarView {

    public enum Position {
	content, context
    }

    private final HashMap<String, Menu> toolbarMenus;
    private final ArrayList<Widget> removableToolbarItems;
    private final HashMap<String, Item> menuItems;
    private final WorkspaceSkeleton ws;
    private final Session session;
    private final Provider<ActionManager> actionManagerProvider;
    private final Position position;

    public ActionToolbarPanel(final Position position, final Session session,
	    final Provider<ActionManager> actionManagerProvider, final WorkspaceSkeleton ws) {
	this.position = position;
	this.session = session;
	this.actionManagerProvider = actionManagerProvider;
	this.ws = ws;
	toolbarMenus = new HashMap<String, Menu>();
	removableToolbarItems = new ArrayList<Widget>();
	menuItems = new HashMap<String, Item>();

    }

    public void addButtonAction(final ActionButtonDescriptor<StateToken> action) {
	final ActionPosition pos = action.getActionPosition();
	final ToolbarButton button = new ToolbarButton();
	final String text = action.getText();
	final String iconUrl = action.getIconUrl();
	if (text != null) {
	    button.setText(text);
	}
	button.addListener(new ButtonListenerAdapter() {
	    @Override
	    public void onClick(final Button button, final EventObject e) {
		actionManagerProvider.get().doAction(action, session.getCurrentState().getStateToken());
	    }
	});
	if (iconUrl != null) {
	    button.setIcon(iconUrl);
	}
	button.setTooltip(action.getToolTip());

	final Toolbar toolbar = getToolbar(pos);
	if (action.hasLeftSeparator()) {
	    removableToolbarItems.add(add(toolbar, action.getLeftSeparator()));
	}
	toolbar.add(button);
	if (action.hasRightSeparator()) {
	    removableToolbarItems.add(add(toolbar, action.getRightSeparator()));
	}
	removableToolbarItems.add(button);
    }

    public void addMenuAction(final ActionMenuDescriptor<StateToken> action, final boolean enable) {
	final String menuTitle = action.getParentMenuTitle();
	final String menuSubTitle = action.getParentSubMenuTitle();
	final ActionPosition pos = action.getActionPosition();
	final String itemKey = genMenuKey(pos, menuTitle, menuSubTitle, action.getText());
	Item item = menuItems.get(itemKey);
	if (item == null) {
	    item = createToolbarMenu(pos, menuTitle, menuSubTitle, action);
	    menuItems.put(itemKey, item);
	}
	if (enable) {
	    item.enable();
	} else {
	    item.disable();
	}
	doLayoutIfNeeded(pos);
    }

    public void clear() {
	toolbarMenus.clear();
	menuItems.clear();
	removableToolbarItems.clear();
	getToolbar(ActionPosition.topbar).removeAll();
	getToolbar(ActionPosition.bottombar).removeAll();
    }

    public void clearRemovableActions() {
	for (final Widget widget : removableToolbarItems) {
	    widget.removeFromParent();
	}
	removableToolbarItems.clear();
	doLayoutIfNeeded();
    }

    public void disableAllMenuItems() {
	for (final Item item : menuItems.values()) {
	    item.disable();
	}
	doLayoutIfNeeded();
    }

    private void add(final ActionPosition toolbar, final Widget widget) {
	getToolbar(toolbar).add(widget);
    }

    private Widget add(final Toolbar toolbar, final ActionButtonSeparator separator) {
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

    private Item createToolbarMenu(final ActionPosition toolBarPos, final String menuTitle, final String menuSubTitle,
	    final ActionMenuDescriptor<StateToken> action) {
	final Item item = new Item(action.getText(), new BaseItemListenerAdapter() {
	    @Override
	    public void onClick(BaseItem item, EventObject e) {
		actionManagerProvider.get().doAction(action, session.getCurrentState().getStateToken());
	    }
	});
	item.setIcon(action.getIconUrl());

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
	doLayoutIfNeeded(toolBarPos);
	return item;
    }

    private Menu createToolbarMenu(final ActionPosition barPosition, final String iconUrl, final String menuTitle,
	    final String menuKey) {
	final Menu menu = new Menu();
	final ToolbarButton toolbarMenu = new ToolbarButton(menuTitle);
	if (iconUrl != null) {
	    toolbarMenu.setIcon(iconUrl);
	}
	toolbarMenu.addListener(new ButtonListenerAdapter() {
	    @Override
	    public void onClick(final Button button, final EventObject e) {
		menu.showAt(e.getXY());
	    }
	});
	toolbarMenus.put(menuKey, menu);
	add(barPosition, toolbarMenu);
	return menu;
    }

    private void doLayoutIfNeeded() {
	doLayoutIfNeeded(ActionPosition.topbar);
	doLayoutIfNeeded(ActionPosition.bottombar);
    }

    private void doLayoutIfNeeded(final ActionPosition pos) {
	getToolbar(pos).doLayoutIfNeeded();
    }

    private String genMenuKey(final ActionPosition pos, final String menuTitle, final String menuSubTitle,
	    final String actionText) {
	final String basePart = "km-" + pos.toString() + "-" + menuTitle;
	final String subMenuPart = menuSubTitle != null ? "-subm-" + menuSubTitle : "";
	final String itemPart = actionText != null ? "-item-" + actionText : "";
	return basePart + subMenuPart + itemPart;
    }

    private Toolbar getToolbar(final ActionPosition pos) {
	switch (pos) {
	case bootombarAndItemMenu:
	case bottombar:
	    switch (position) {
	    case content:
		return ws.getEntityWorkspace().getContentBottomBar();
	    case context:
	    default:
		return ws.getEntityWorkspace().getContextBottomBar();
	    }
	case topbar:
	case topbarAndItemMenu:
	default:
	    switch (position) {
	    case content:
		return ws.getEntityWorkspace().getContentTopBar();
	    case context:
	    default:
		return ws.getEntityWorkspace().getContextTopBar();
	    }
	}
    }
}
