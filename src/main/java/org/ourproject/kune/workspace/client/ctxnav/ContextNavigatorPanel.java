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
package org.ourproject.kune.workspace.client.ctxnav;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.MenuItemsContainer;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.data.Node;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragDrop;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Editor;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.event.EditorListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.tree.DropNodeCallback;
import com.gwtext.client.widgets.tree.MultiSelectionModel;
import com.gwtext.client.widgets.tree.TreeEditor;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

public class ContextNavigatorPanel implements ContextNavigatorView {

    private TreePanel treePanel;
    private TreeEditor treeEditor;
    private final WorkspaceSkeleton ws;
    private boolean mustFireOnTextChange;
    private boolean isEditable;
    private final ActionManager actionManager;
    private final ContextNavigatorPresenter presenter;
    private final MenuItemsContainer<StateToken> menuItemsContainer;
    private boolean mustFireOnExpand;

    public ContextNavigatorPanel(final ContextNavigatorPresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws, final ActionManager actionManager) {
        this.presenter = presenter;
        this.ws = ws;
        this.actionManager = actionManager;

        mustFireOnTextChange = true;
        mustFireOnExpand = true;
        isEditable = false;
        menuItemsContainer = new MenuItemsContainer<StateToken>();
    }

    public void addItem(final ContextNavigatorItem item) {
        final String nodeId = item.getId();
        final TreeNode node = treePanel.getNodeById(nodeId);
        if (node == null) {
            // Log.info("Adding tree node " + nodeId);

            // FIXME: maybe to solve the length issues with items, we can store
            // a truncated title also and use something similar to:
            // String nodeText = Format.ellipsis(item.getText(),
            // ws.getEntityWorkspace().getContextTopBar().getPanel().getOffsetWidth()
            // / 8);
            // the problem is that editor uses this value, then we have to
            // restore the real value before edit
            final TreeNode child = new TreeNode(item.getText());
            child.setId(nodeId);
            final String icon = item.getIconUrl();
            if (icon != null) {
                child.setIcon(icon);
            }
            child.setHref("#" + item.getStateToken().toString());
            child.setAllowDrag(item.isDraggable());
            child.setAllowDrop(item.isDroppable());
            final String tooltip = item.getTooltip();
            if (tooltip != null) {
                child.setTooltip(tooltip);
            }
            menuItemsContainer.createItemMenu(nodeId, item.getActionCollection(),
                    new Listener<ActionItem<StateToken>>() {
                        public void onEvent(final ActionItem<StateToken> actionItem) {
                            doAction(actionItem);
                        }
                    });
            final TreeNode parent = treePanel.getNodeById(item.getParentId());
            if (parent != null) {
                child.addListener(new TreeNodeListenerAdapter() {
                    @Override
                    public void onTextChange(final Node node, final String text, final String oldText) {
                        if (mustFireOnTextChange) {
                            presenter.onItemRename(getToken(node), text, oldText);
                        }
                    }
                });
                final boolean isContainer = !item.getStateToken().hasAll();
                if (isContainer) {
                    child.setExpandable(true);
                    child.setSingleClickExpand(true);
                    child.addListener(new TreeNodeListenerAdapter() {
                        @Override
                        public void onExpand(final Node node) {
                            if (mustFireOnExpand) {
                                treePanel.getNodeById(node.getId()).select();
                                presenter.gotoToken(getToken(node));
                            }
                        }
                    });
                } else {
                    // is a document
                    child.setLeaf(true);
                }
                child.addListener(new TreeNodeListenerAdapter() {
                    @Override
                    public void onClick(final Node node, final EventObject e) {
                        presenter.gotoToken(getToken(node));
                    }
                });
                setNodeStatus(child, item.getContentStatus());
                parent.appendChild(child);
            } else {
                Log.error("Error building file tree, parent folder " + item.getParentId() + " not found");
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
        menuItemsContainer.clear();
    }

    public void detach() {
        clear();
    }

    public void editItem(final String id) {
        treeEditor.startEdit(getNode(id));
    }

    public boolean isAttached() {
        return treePanel != null;
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
            if (item.getParentNode() != null) {
                if (treePanel.isRendered()) {
                    mustFireOnExpand = false;
                    item.ensureVisible();
                    mustFireOnExpand = true;
                }
            }
            if (item.getChildNodes().length > 0) {
                item.expand();
            }
            item.select();
        } else {
            Log.error("Error building file tree, current token " + id + " not found");
        }
    }

    public void setEditable(final boolean editable) {
        this.isEditable = editable;
    }

    public void setItemStatus(final String id, final ContentStatus status) {
        setNodeStatus(getNode(id), status);
    }

    public void setItemText(final String genId, final String text) {
        final TreeNode node = getNode(genId);
        setFireOnTextChange(false);
        node.setText(text);
        setFireOnTextChange(true);
    }

    public void setRootItem(final String id, final String text, final StateToken stateToken) {
        if (treePanel == null || treePanel.getNodeById(id) == null) {
            createTreePanel(id, text, stateToken);
        }
    }

    private void createTreePanel(final String rootId, final String text, final StateToken stateToken) {
        if (treePanel != null) {
            clear();
        }
        treePanel = new TreePanel();
        treePanel.setHeight("100%");
        treePanel.setAutoScroll(true);
        treePanel.setRootVisible(false);
        treePanel.setContainerScroll(true);
        treePanel.setAnimate(true);
        treePanel.setBorder(false);
        treePanel.setUseArrows(true);
        treePanel.setSelectionModel(new MultiSelectionModel());
        // treePanel.setBufferResize(true;)
        // treePanel.setId(CTX_NAVIGATOR_TREEPANEL);
        final TreeNode root = new TreeNode();
        root.setAllowDrag(false);
        root.setExpanded(true);
        root.setId(rootId);
        root.setText(text);
        root.setHref("#" + stateToken);
        root.expand();
        treePanel.addListener(new TreePanelListenerAdapter() {
            @Override
            public boolean doBeforeNodeDrop(final TreePanel treePanel, final TreeNode target, final DragData dragData,
                    final String point, final DragDrop source, final TreeNode dropNode,
                    final DropNodeCallback dropNodeCallback) {
                NotifyUser.info("Drag & drop of contents in development");
                return false;
            }

            @Override
            public void onContextMenu(final TreeNode node, final EventObject e) {
                final Menu menu = menuItemsContainer.get(node.getId());
                if (menu != null && menu.getItems().length > 0) {
                    menu.showAt(e.getXY());
                } else {
                    Log.info("Empty item menu");
                }
            }
        });
        treePanel.setLines(false);
        treePanel.setEnableDD(isEditable);
        treePanel.setRootNode(root);
        // treePanel.expand();

        final TextField field = new TextField();
        treeEditor = new TreeEditor(treePanel, field);
        treeEditor.addListener(new EditorListenerAdapter() {
            @Override
            public boolean doBeforeStartEdit(final Editor source, final ExtElement boundEl, final Object value) {
                return isEditable;
            }

        });
        // test !?
        treeEditor.setAutosize(false);

        treePanel.addListener(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                // Log.debug("tree-------w: " + adjWidth + " h: " + adjHeight);
                treePanel.doLayout(false);
            }
        });
        // ws.getEntityWorkspace().addContextListener(new
        // ContainerListenerAdapter() {
        // @Override
        // public void onResize(final BoxComponent component, final int
        // adjWidth, final int adjHeight,
        // final int rawWidth, final int rawHeight) {
        // // Log.debug("-------w: " + adjWidth + " h: " + adjHeight);
        // // Log.debug("---r---w: " + rawWidth + " h: " + rawHeight);
        // }
        // });
        ws.getEntityWorkspace().setContext(treePanel);
    }

    private void doAction(final ActionItem<StateToken> actionItem) {
        actionManager.doAction(actionItem);
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

    private void setFireOnTextChange(final boolean fireOnTextChange) {
        this.mustFireOnTextChange = fireOnTextChange;
    }

    private void setNodeStatus(final TreeNode node, final ContentStatus status) {
        switch (status) {
        case publishedOnline:
            node.setCls("k-ctn-status-normal");
            break;
        case inTheDustbin:
            node.setCls("k-ctn-status-deleted");
            break;
        case rejected:
            node.setCls("k-ctn-status-rejected");
            break;
        case editingInProgress:
            node.setCls("k-ctn-status-editing");
            break;
        case submittedForEvaluation:
            node.setCls("k-ctn-status-submitted");
            break;
        }
    }

}
