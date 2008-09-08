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

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.Toolbar;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.provider.Provider;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Editor;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.EditorListenerAdapter;
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
    private final Provider<ActionManager> actionManagerProvider;
    private final StateManager stateManager;

    public ContextNavigatorPanel(final ContextNavigatorPresenter presenter, final I18nTranslationService i18n,
	    final StateManager stateManager, final WorkspaceSkeleton ws,
	    final Provider<ActionManager> actionManagerProvider) {
	this.stateManager = stateManager;
	this.ws = ws;
	this.actionManagerProvider = actionManagerProvider;

	contextMenus = new HashMap<String, Menu>();
	toolbarMenus = new HashMap<String, Menu>();
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
	    case publicVisible:
		child.enable();
		break;
	    case markForDelection:
		child.setCls("k-linethrough");
	    case nonPublicVisible:
		child.disable();
		break;
	    }
	    contextMenus.put(nodeId, createItemMenu(item.getActionCollection(), item.getStateToken()));
	    final TreeNode parent = treePanel.getNodeById(item.getParentId());
	    if (parent != null) {
		Log.info("Adding child node: " + nodeId + " to folder: " + item.getParentId());
		if (!item.getStateToken().hasAll()) {
		    child.setExpandable(true);
		    child.addListener(new TreeNodeListenerAdapter() {
			public void onExpand(final Node node) {
			    treePanel.getNodeById(node.getId()).select();
			    stateManager.gotoToken(node.getAttribute("href").substring(1));
			}
		    });
		} else {
		    child.setLeaf(true);
		}
		parent.appendChild(child);
	    } else {
		Log.error("Error building file tree, parent folder not found");
	    }
	} else {
	    // the node already created
	    if (contextMenus.get(nodeId) == null && item.getActionCollection() != null) {
		contextMenus.put(nodeId, createItemMenu(item.getActionCollection(), item.getStateToken()));
	    }
	}

    }

    public void clear() {
	if (treePanel != null) {
	    treePanel.clear();
	}
	contextMenus.clear();
	toolbarMenus.clear();
    }

    public void editItem(final String id) {
	treeEditor.startEdit(getNode(id));
    }

    public void selectItem(final String id) {
	final TreeNode item = getNode(id);
	if (item != null) {
	    item.select();
	    item.ensureVisible();
	    if (item.getChildNodes().length > 0) {
		item.expand();
	    }
	} else {
	    Log.error("Error building file tree, current token not found");
	}
    }

    public void setBottomActions(final StateToken stateToken, final ActionCollection<StateToken> actions) {
	final Toolbar toolBar = ws.getEntityWorkspace().getContextBottomBar();
	setToolbarActions(toolBar, stateToken, actions);
    }

    public void setRootItem(final String id, final String text, final StateToken stateToken) {
	if (treePanel == null || treePanel.getNodeById(id) == null) {
	    createTreePanel(id);
	}
    }

    public void setTopActions(final StateToken stateToken, final ActionCollection<StateToken> actions) {
	final Toolbar toolBar = ws.getEntityWorkspace().getContextTopBar();
	setToolbarActions(toolBar, stateToken, actions);
    }

    private Menu createItemMenu(final ActionCollection<StateToken> actionCollection, final StateToken stateToken) {
	if (actionCollection != null) {
	    final Menu menu = new Menu();
	    // Remove if when retrieved rights of siblings
	    for (final ActionDescriptor<StateToken> action : actionCollection) {
		final Item item = new Item(action.getText());
		item.setIcon(action.getIconUrl());
		menu.addItem(item);
		item.addListener(new BaseItemListenerAdapter() {
		    public void onClick(final BaseItem item, final EventObject e) {
			DeferredCommand.addCommand(new Command() {
			    public void execute() {
				actionManagerProvider.get().doAction(action, stateToken);
			    }
			});
		    }
		});
	    }
	    return menu;
	}
	return null;
    }

    private Menu createToolbarMenu(final Toolbar bar, final String iconUrl, final String menuTitle) {
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
	toolbarMenus.put(menuTitle, menu);
	bar.add(toolbarMenu);
	return menu;
    }

    private void createTreePanel(final String rootId) {
	if (treePanel != null) {
	    clear();
	}
	treePanel = new TreePanel();
	treePanel.setAnimate(true);
	treePanel.setBorder(false);
	treePanel.setRootVisible(false);
	treePanel.setUseArrows(true);
	final TreeNode root = new TreeNode();
	root.setAllowDrag(false);
	root.setExpanded(true);
	root.setId(rootId);
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
	treeEditor.addListener(new EditorListenerAdapter() {
	    // public boolean doBeforeComplete(final Editor source, final Object
	    // value, final Object startValue) {
	    // return false;
	    // }

	    public void onComplete(final Editor source, final Object value, final Object startValue) {
		// TODO Auto-generated method stub
	    }
	});
	final Panel panel = new Panel();
	panel.setBorder(false);
	panel.add(treePanel);
	ws.getEntityWorkspace().setContext(panel);
    }

    private void doAction(final ActionDescriptor<StateToken> action, final StateToken stateToken) {
	actionManagerProvider.get().doAction(action, stateToken);
    }

    private TreeNode getNode(final String id) {
	final TreeNode node = treePanel.getNodeById(id);
	if (node == null) {
	    Log.error("Id: " + id + " not found in context navigator");
	}
	return node;
    }

    private void setToolbarActions(final Toolbar toolBar, final StateToken stateToken,
	    final ActionCollection<StateToken> actions) {
	toolBar.removeAll();
	for (final ActionDescriptor<StateToken> action : actions) {
	    Log.info("Procesing action: " + action.getText());
	    if (!action.isMenuAction()) {
		final ToolbarButton button = new ToolbarButton();
		final String text = action.getText();
		if (text != null) {
		    button.setText(text);
		}
		button.addListener(new ButtonListenerAdapter() {
		    @Override
		    public void onClick(final Button button, final EventObject e) {
			doAction(action, stateToken);
		    }
		});
		button.setIcon(action.getIconUrl());
		button.setTooltip(action.getToolTip());
		toolBar.add(button);
	    } else {
		// Menu action
		final Item item = new Item(action.getText(), new BaseItemListenerAdapter() {
		    @Override
		    public void onClick(BaseItem item, EventObject e) {
			doAction(action, stateToken);
		    }
		});
		item.setIcon(action.getIconUrl());

		final String menuTitle = action.getParentMenuTitle();
		final String menuSubTitle = action.getParentSubMenuTitle();
		final String subMenuKey = menuTitle + "-" + menuSubTitle;
		Menu menu = toolbarMenus.get(menuTitle);
		Menu subMenu = toolbarMenus.get(subMenuKey);
		if (menuSubTitle != null) {
		    if (subMenu == null) {
			subMenu = new Menu();
			final MenuItem subMenuItem = new MenuItem(menuSubTitle, subMenu);
			if (menu == null) {
			    menu = createToolbarMenu(toolBar, action.getParentMenuIconUrl(), menuTitle);
			}
			menu.addItem(subMenuItem);
			toolBar.doLayoutIfNeeded();
			toolbarMenus.put(subMenuKey, subMenu);
		    }
		    subMenu.addItem(item);
		} else {
		    // Menu action without submenu
		    if (menu == null) {
			menu = createToolbarMenu(toolBar, action.getParentMenuIconUrl(), menuTitle);
		    }
		    menu.addItem(item);
		    toolBar.doLayoutIfNeeded();
		}
	    }
	}
    }
}
