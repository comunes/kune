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

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.Tree;
import com.gwtext.client.widgets.Editor;
import com.gwtext.client.widgets.event.EditorListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
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
    private final WorkspaceSkeleton ws;
    private boolean fireOnTextChange;
    private final ContextNavigatorPresenter presenter;
    private boolean isEditable;

    public ContextNavigatorPanel(final ContextNavigatorPresenter presenter, final I18nTranslationService i18n,
	    final WorkspaceSkeleton ws) {
	this.presenter = presenter;
	this.ws = ws;

	contextMenus = new HashMap<String, Menu>();
	fireOnTextChange = true;
	isEditable = false;
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
			if (fireOnTextChange) {
			    presenter.onItemRename(getToken(node), text, oldText);
			}
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

    public void clear() {
	if (treePanel != null) {
	    treePanel.clear();
	    treePanel = null;
	}
	contextMenus.clear();
    }

    public void editItem(final String id) {
	treeEditor.startEdit(getNode(id));
    }

    public boolean isSelected(final String id) {
	final TreeNode item = getNode(id);
	if (item != null) {
	    return item.isSelected();
	}
	return false;
    }

    public void selectItem(final String id) {
	final TreeNode item = getNode(id);
	if (item != null) {
	    item.select();
	    if (item.getParentNode() != null) {
		item.ensureVisible();
	    }
	    if (item.getChildNodes().length > 0) {
		item.expand();
	    }
	} else {
	    Log.error("Error building file tree, current token not found");
	}
    }

    public void setEditable(final boolean editable) {
	this.isEditable = editable;
    }

    public void setFireOnTextChange(final boolean fireOnTextChange) {
	this.fireOnTextChange = fireOnTextChange;
    }

    public void setItemText(final String genId, final String text) {
	final TreeNode node = getNode(genId);
	node.setText(text);
    }

    public void setRootItem(final String id, final String text, final StateToken stateToken,
	    final ActionCollection<StateToken> actions) {
	if (treePanel == null || treePanel.getNodeById(id) == null) {
	    createTreePanel(id, text, stateToken, actions);
	}
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

    private void createTreePanel(final String rootId, final String text, final StateToken stateToken,
	    final ActionCollection<StateToken> actions) {
	if (treePanel != null) {
	    clear();
	}
	treePanel = new TreePanel();
	treePanel.setAnimate(true);
	treePanel.setBorder(false);
	treePanel.setRootVisible(true);
	treePanel.setUseArrows(true);
	// treePanel.setSelectionModel(new MultiSelectionModel());
	final TreeNode root = new TreeNode();
	root.setAllowDrag(false);
	root.setExpanded(true);
	root.setId(rootId);
	root.setText(text);
	root.setHref("#" + stateToken);
	root.expand();
	createItemMenu(rootId, actions, stateToken);
	treePanel.addListener(new TreePanelListenerAdapter() {
	    public void onContextMenu(final TreeNode node, final EventObject e) {
		final Menu menu = contextMenus.get(node.getId());
		if (menu != null && menu.getItems().length > 0) {
		    menu.showAt(e.getXY());
		} else {
		    Log.info("Empty item menu");
		}
	    }

	    @Override
	    public void onMoveNode(final Tree treePanel, final TreeNode node, final TreeNode oldParent,
		    final TreeNode newParent, final int index) {
		newParent.removeChild(node);
		oldParent.appendChild(node);
		Site.info("Drag & drop of contents in development");
	    }
	});
	treePanel.setLines(false);
	treePanel.setEnableDD(isEditable);
	treePanel.setRootNode(root);
	// treePanel.setAutoScroll(true);
	treePanel.setContainerScroll(true);
	treePanel.setAutoWidth(true);

	final TextField field = new TextField();
	treeEditor = new TreeEditor(treePanel, field);
	treeEditor.addListener(new EditorListenerAdapter() {
	    public boolean doBeforeStartEdit(final Editor source, final ExtElement boundEl, final Object value) {
		return isEditable;
	    }
	});

	final ScrollPanel panel = new ScrollPanel();
	// final Panel panel = new Panel();
	// panel.setLayout(new FitLayout());
	// panel.setBorder(false);
	// panel.setAutoScroll(true);
	// new ScrollPanel
	panel.setWidth("100%");
	panel.setHeight("100%");
	panel.add(treePanel);

	ws.getEntityWorkspace().setContext(panel);
    }

    private void doAction(final ActionDescriptor<StateToken> action, final StateToken stateToken) {
	presenter.doAction(action, stateToken);
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

}
