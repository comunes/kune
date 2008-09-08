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

package org.ourproject.kune.workspace.client.ui.ctx.nav;

import java.util.ArrayList;
import java.util.HashMap;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.calclab.suco.client.signal.Slot2;

public class ContextNavigatorPresenter implements ContextNavigator {

    private ContextNavigatorView view;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final I18nUITranslationService i18n;
    private final HashMap<String, ActionCollection<StateToken>> actions;
    private final ArrayList<String> draggables;
    private final ArrayList<String> droppables;

    public ContextNavigatorPresenter(final StateManager stateManager, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider, final I18nUITranslationService i18n) {
	this.stateManager = stateManager;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
	this.i18n = i18n;
	actions = new HashMap<String, ActionCollection<StateToken>>();
	draggables = new ArrayList<String>();
	droppables = new ArrayList<String>();
    }

    public void addAction(final String contentTypeId, final ActionDescriptor<StateToken> action) {
	ActionCollection<StateToken> actionColl = actions.get(contentTypeId);
	if (actionColl == null) {
	    actionColl = new ActionCollection<StateToken>();
	    actions.put(contentTypeId, actionColl);
	}
	actionColl.add(action);
    }

    public void editItem(final StateToken stateToken) {
	view.editItem(genId(stateToken));
    }

    public View getView() {
	return view;
    }

    public void init(final ContextNavigatorView view) {
	this.view = view;
	stateManager.onGroupChanged(new Slot2<String, String>() {
	    public void onEvent(final String oldGroup, final String newGroup) {
		if (oldGroup != null) {
		    view.clear();
		}
	    }
	});
	stateManager.onToolChanged(new Slot2<String, String>() {
	    public void onEvent(final String oldTool, final String newTool) {
		if (oldTool != null) {
		    view.clear();
		}
	    }
	});
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO parameter) {
		view.clear();
	    }
	});
	session.onUserSignOut(new Slot0() {
	    public void onEvent() {
		view.clear();
	    }
	});
    }

    public void onTitleRename(final String newName, final String token) {
	Site.showProgressSaving();
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().rename(session.getUserHash(), currentState.getGroup().getShortName(), token,
		newName, new AsyncCallbackSimple<String>() {
		    public void onSuccess(final String result) {
			stateManager.reloadContextAndTitles();
			Site.hideProgress();
		    }
		});
    }

    public void registerDraggableType(final String type) {
	draggables.add(type);
    }

    public void registerDroppableType(final String type) {
	droppables.add(type);
    }

    public void removeAction(final String contentTypeId, final ActionDescriptor<StateToken> action) {
	actions.get(contentTypeId).remove(action);
    }

    public void selectItem(final StateToken stateToken) {
	view.selectItem(genId(stateToken));
    }

    public void setState(final StateDTO state) {
	final StateToken stateToken = state.getStateToken().clone();
	final String treeId = genId(stateToken);

	final ContainerDTO container = state.getFolder();
	createTreePath(stateToken, container.getAbsolutePath());

	boolean visible = false;
	AccessRightsDTO rights = null;

	String containerTreeId;

	if (state.hasDocument()) {
	    rights = state.getContentRights();
	    visible = rights.isVisible();
	    containerTreeId = genId(stateToken.clone().setDocument(null));
	} else {
	    rights = state.getFolderRights();
	    visible = rights.isVisible();
	    final Long folderId = container.getParentFolderId();
	    containerTreeId = genId(stateToken.clone().setDocument(null).setFolder(
		    folderId == null ? null : folderId.toString()));
	}

	// here check deletion mark

	final ActionCollection<StateToken> topActions = new ActionCollection<StateToken>();
	final ActionCollection<StateToken> itemActions = new ActionCollection<StateToken>();
	final ActionCollection<StateToken> bottomActions = new ActionCollection<StateToken>();

	boolean add = false;
	for (final ActionDescriptor<StateToken> action : actions.get(state.getTypeId())) {
	    switch (action.getAccessRol()) {
	    case Administrator:
		add = rights.isAdministrable();
		break;
	    case Editor:
		add = rights.isEditable();
		break;
	    case Viewer:
		add = rights.isVisible();
		break;
	    }
	    if (add) {
		switch (action.getActionPosition()) {
		case topbarAndItemMenu:
		    itemActions.add(action);
		case topbar:
		    topActions.add(action);
		    break;
		case bootombarAndItemMenu:
		    itemActions.add(action);
		case bottombar:
		    bottomActions.add(action);
		    break;
		case itemMenu:
		    itemActions.add(action);
		    break;
		}
	    }
	}

	view.setTopActions(stateToken, topActions);
	view.setBottomActions(stateToken, bottomActions);

	final ContextNavigatorItem item = new ContextNavigatorItem(treeId, containerTreeId, "images/nav/page.png",
		state.getTitle(), visible ? ContentStatusDTO.publicVisible : ContentStatusDTO.nonPublicVisible,
		stateToken, isDraggable(state.getTypeId(), rights.isAdministrable()), isDroppable(state.getTypeId(),
			rights.isAdministrable()), itemActions);
	view.addItem(item);

	for (final ContentDTO content : container.getContents()) {
	    final StateToken siblingToken = stateToken.clone().setDocument(content.getId().toString());
	    final StateToken siblingParentToken = stateToken.clone().setDocument(null);
	    // TODO: rights not correct
	    final ContextNavigatorItem sibling = new ContextNavigatorItem(genId(siblingToken),
		    genId(siblingParentToken), "images/nav/page.png", content.getTitle(),
		    ContentStatusDTO.publicVisible, siblingToken, isDraggable(content.getTypeId(), rights
			    .isAdministrable()), isDroppable(content.getTypeId(), rights.isAdministrable()), null);
	    view.addItem(sibling);

	}

	for (final ContainerDTO siblingFolder : container.getChilds()) {
	    final StateToken siblingToken = stateToken.clone().setDocument(null).setFolder(
		    siblingFolder.getId().toString());
	    final StateToken siblingParentToken = stateToken.clone().setDocument(null).setFolder(
		    siblingFolder.getParentFolderId().toString());
	    // TODO: rights not correct
	    final ContextNavigatorItem sibling = new ContextNavigatorItem(genId(siblingToken),
		    genId(siblingParentToken), "", siblingFolder.getName(), ContentStatusDTO.publicVisible,
		    siblingToken, isDraggable(container.getTypeId(), rights.isAdministrable()), isDroppable(container
			    .getTypeId(), rights.isAdministrable()), null);
	    view.addItem(sibling);
	}
	view.selectItem(treeId);
    }

    private void createTreePath(final StateToken state, final ContainerSimpleDTO[] absolutePath) {
	for (int i = 0; i < absolutePath.length; i++) {
	    final ContainerSimpleDTO folder = absolutePath[i];
	    final String parentFolderId = folder.getParentFolderId() == null ? null : folder.getParentFolderId()
		    .toString();
	    final StateToken folderStateToken = state.clone().setDocument(null).setFolder(folder.getId().toString());
	    final StateToken parentStateToken = state.clone().setDocument(null).setFolder(parentFolderId);
	    if (folder.getParentFolderId() != null) {
		// Bad rights, draggable/droppable
		final ContextNavigatorItem parent = new ContextNavigatorItem(genId(folderStateToken),
			genId(parentStateToken), "", folder.getName(), ContentStatusDTO.publicVisible,
			folderStateToken, false, true, null);
		view.addItem(parent);
	    } else {
		// create root folder
		view.setRootItem(genId(folderStateToken), i18n.t("contents"), folderStateToken);
	    }
	}
    }

    private String genId(final StateToken token) {
	return "k-" + token.toString().replace(StateToken.SEPARATOR, "-");
    }

    private boolean isDraggable(final String typeId, final boolean administrable) {
	return administrable && draggables.contains(typeId);
    }

    private boolean isDroppable(final String typeId, final boolean administrable) {
	return administrable && droppables.contains(typeId);
    }

}
