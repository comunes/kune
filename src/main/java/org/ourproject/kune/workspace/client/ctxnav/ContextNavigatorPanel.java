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

package org.ourproject.kune.workspace.client.ctxnav;

import java.util.ArrayList;
import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import com.gwtext.client.widgets.tree.TreeEditor;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

public class ContextNavigatorPanel implements ContextNavigatorView {

    private TreePanel treePanel;
    private TreeEditor treeEditor;
    private final HashMap<String, Menu> contextMenus;
    private final HashMap<String, Menu> toolbarMenus;
    private final WorkspaceSkeleton ws;
    private final ContextNavigatorPresenter presenter;
    private final ArrayList<ToolbarButton> buttons;
    private final HashMap<String, Item> menuItems;

    public ContextNavigatorPanel(final ContextNavigatorPresenter presenter, final I18nTranslationService i18n,
	    final WorkspaceSkeleton ws) {
	this.presenter = presenter;
	this.ws = ws;

	contextMenus = new HashMap<String, Menu>();
	toolbarMenus = new HashMap<String, Menu>();
	buttons = new ArrayList<ToolbarButton>();
	menuItems = new HashMap<String, Item>();
    }

    public void addButtonAction(final ActionDescriptor<StateToken> action) {
	final ActionPosition pos = action.getActionPosition();
	final ToolbarButton button = new ToolbarButton();
	final String text = action.getText();
	if (text != null) {
	    button.setText(text);
	}
	button.addListener(new ButtonListenerAdapter() {
	    @Override
	    public void onClick(final Button button, final EventObject e) {
		doAction(action, presenter.getCurrentStateToken());
	    }
	});
	button.setIcon(action.getIconUrl());
	button.setTooltip(action.getToolTip());
	getToolbar(pos).add(button);
	buttons.add(button);
    }

    public void addItem(final ContextNavigatorItem item) {
	final String nodeId = item.getId();
	if (treePanel.getNodeById(nodeId) == null) {
	    final TreeNode child = new TreeNode(item.getText());
	    child.setId(nodeId);
	    final String icon = item.getIconUrl();
	    if (icon != null) {
		child.setIcon(icon);
	    }
	    child.setHref("#" + item.getStateToken().toString());
	    child.setAllowDrag(item.isDraggable());
	    child.setAllowDrop(item.isDroppable());
	    switch (item.getContentStatus()) {
	    case publishedOnline:
		child.enable();
		break;
	    case inTheDustbin:
	    case rejected:
		child.setCls("k-linethrough");
	    case editingInProgress:
	    case submittedForEvaluation:
		child.disable();
		break;
	    }
	    createItemMenu(nodeId, item.getActionCollection(), item.getStateToken());
	    final TreeNode parent = treePanel.getNodeById(item.getParentId());
	    if (parent != null) {
		// Log.info("Adding child node: " + nodeId + " to folder: " +
		// item.getParentId());
		child.addListener(new TreeNodeListenerAdapter() {
		    @Override
		    public void onTextChange(final Node node, final String text, final String oldText) {
			presenter.onItemRename(getToken(node), text, oldText);
		    }
		});
		if (!item.getStateToken().hasAll()) {
		    // is a container
		    child.setExpandable(true);
		    child.addListener(new TreeNodeListenerAdapter() {
			public void onExpand(final Node node) {
			    treePanel.getNodeById(node.getId()).select();
			    presenter.gotoToken(getToken(node));
			}
		    });
		} else {
		    // is a document
		    child.setLeaf(true);
		}
		parent.appendChild(child);
	    } else {
		Log.error("Error building file tree, parent folder not found");
	    }
	} else {
	    // the node already created
	}

    }

    public void addMenuAction(final ActionDescriptor<StateToken> action, final boolean enable) {
	final String menuTitle = action.getParentMenuTitle();
	final String menuSubTitle = action.getParentSubMenuTitle();
	final ActionPosition pos = action.getActionPosition();
	final String itemKey = genMenuKey(pos, menuTitle, menuSubTitle, action.getText());
	Item item = menuItems.get(itemKey);
	if (item == null) {
	    item = createMenuItem(pos, menuTitle, menuSubTitle, action);
	    menuItems.put(itemKey, item);
	} else {
	}
	if (enable) {
	    item.enable();
	} else {
	    item.disable();
	}
	doLayoutIfNeeded(pos);
    }

    public void clear() {
	if (treePanel != null) {
	    treePanel.clear();
	}
	ws.getEntityWorkspace().getContextTopBar().removeAll();
	ws.getEntityWorkspace().getContextBottomBar().removeAll();
	contextMenus.clear();
	toolbarMenus.clear();
	menuItems.clear();
	buttons.clear();
    }

    public void disableAllMenuItems() {
	for (final Item item : menuItems.values()) {
	    item.disable();
	}
	doLayoutIfNeeded();
    }

    public void editItem(final String id) {
	treeEditor.startEdit(getNode(id));
    }

    public void removeAllButtons() {
	for (final ToolbarButton button : buttons) {
	    button.removeFromParent();
	}
	buttons.clear();
	doLayoutIfNeeded();
    }

    public void selectItem(final String id) {
	final TreeNode item = getNode(id);
	if (item != null) {
	    item.select();
	    // Errore item.ensureVisible();
	    if (item.getChildNodes().length > 0) {
		item.expand();
	    }
	} else {
	    Log.error("Error building file tree, current token not found");
	}
    }

    public void setItemText(final String genId, final String text) {
	final TreeNode node = getNode(genId);
	node.setText(text);
    }

    public void setRootItem(final String id, final String text, final StateToken stateToken) {
	if (treePanel == null || treePanel.getNodeById(id) == null) {
	    createTreePanel(id, text, stateToken);
	}
    }

    private void add(final ActionPosition toolbar, final Widget widget) {
	getToolbar(toolbar).add(widget);
    }

    private void createItemMenu(final String nodeId, final ActionCollection<StateToken> actionCollection,
	    final StateToken stateToken) {
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		Menu menu = null;
		if (actionCollection != null) {
		    menu = new Menu();
		    // Remove if when retrieved rights of siblings
		    for (final ActionDescriptor<StateToken> action : actionCollection) {
			final Item item = new Item(action.getText());
			item.setIcon(action.getIconUrl());
			menu.addItem(item);
			item.addListener(new BaseItemListenerAdapter() {
			    public void onClick(final BaseItem item, final EventObject e) {
				DeferredCommand.addCommand(new Command() {
				    public void execute() {
					doAction(action, stateToken);
				    }
				});
			    }
			});
		    }
		}
		contextMenus.put(nodeId, menu);
	    }
	});
    }

    private Item createMenuItem(final ActionPosition toolBarPos, final String menuTitle, final String menuSubTitle,
	    final ActionDescriptor<StateToken> action) {
	final Item item = new Item(action.getText(), new BaseItemListenerAdapter() {
	    @Override
	    public void onClick(BaseItem item, EventObject e) {
		doAction(action, presenter.getCurrentStateToken());
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

    private void createTreePanel(final String rootId, final String text, final StateToken stateToken) {
	if (treePanel != null) {
	    clear();
	}
	treePanel = new TreePanel();
	treePanel.setAnimate(true);
	treePanel.setBorder(false);
	treePanel.setRootVisible(true);
	treePanel.setUseArrows(true);
	final TreeNode root = new TreeNode();
	root.setAllowDrag(false);
	root.setExpanded(true);
	root.setId(rootId);
	root.setText(text);
	root.setHref("#" + stateToken);
	root.expand();
	treePanel.addListener(new TreePanelListenerAdapter() {
	    public void onContextMenu(final TreeNode node, final EventObject e) {
		final Menu menu = contextMenus.get(node.getId());
		if (menu != null) {
		    menu.showAt(e.getXY());
		} else {
		    Log.info("Empty item menu");
		}
	    }
	});
	treePanel.setLines(false);
	treePanel.setEnableDD(true);
	treePanel.setRootNode(root);
	final TextField field = new TextField();
	treeEditor = new TreeEditor(treePanel, field);
	// final Panel panel = new Panel();
	// panel.setBorder(false);
	// panel.add(treePanel);
	ws.getEntityWorkspace().setContext(treePanel);
    }

    private void doAction(final ActionDescriptor<StateToken> action, final StateToken stateToken) {
	presenter.doAction(action, stateToken);
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

    private TreeNode getNode(final String id) {
	final TreeNode node = treePanel.getNodeById(id);
	if (node == null) {
	    Log.error("Id: " + id + " not found in context navigator");
	}
	return node;
    }

    private String getToken(final Node node) {
	return node.getAttribute("href").substring(1);
    }

    private Toolbar getToolbar(final ActionPosition pos) {
	switch (pos) {
	case bootombarAndItemMenu:
	case bottombar:
	    return ws.getEntityWorkspace().getContextBottomBar();
	case topbar:
	case topbarAndItemMenu:
	default:
	    return ws.getEntityWorkspace().getContextTopBar();
	}
    }
}
