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

import java.util.ArrayList;
import java.util.HashMap;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.actions.ClientActionCollection;
import org.ourproject.kune.platf.client.actions.ClientActionDescriptor;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
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
import org.ourproject.kune.workspace.client.sitebar.Site;

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
    private final HashMap<String, ClientActionCollection<StateToken>> actions;
    private final ArrayList<String> draggables;
    private final ArrayList<String> droppables;

    public ContextNavigatorPresenter(final StateManager stateManager, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider, final I18nUITranslationService i18n) {
	this.stateManager = stateManager;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
	this.i18n = i18n;
	actions = new HashMap<String, ClientActionCollection<StateToken>>();
	draggables = new ArrayList<String>();
	droppables = new ArrayList<String>();
	createActions();
	registerDragDropTypes();
    }

    public void addAction(final String contentTypeId, final ClientActionDescriptor<StateToken> action) {
	ClientActionCollection<StateToken> actionColl = actions.get(contentTypeId);
	if (actionColl == null) {
	    actionColl = new ClientActionCollection<StateToken>();
	    actions.put(contentTypeId, actionColl);
	}
	actionColl.add(action);
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

    public void removeAction(final String contentTypeId, final ClientActionDescriptor<StateToken> action) {
	actions.get(contentTypeId).remove(action);
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

	final ClientActionCollection<StateToken> topActions = new ClientActionCollection<StateToken>();
	final ClientActionCollection<StateToken> itemActions = new ClientActionCollection<StateToken>();
	final ClientActionCollection<StateToken> bottomActions = new ClientActionCollection<StateToken>();

	boolean add = false;
	for (final ClientActionDescriptor<StateToken> action : actions.get(state.getTypeId())) {
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
		case topBarAndItemMenu:
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

	final ContextNavigationItem item = new ContextNavigationItem(treeId, containerTreeId, "", state.getTitle(),
		visible ? ContentStatusDTO.publicVisible : ContentStatusDTO.nonPublicVisible, stateToken, isDraggable(
			state.getTypeId(), rights.isAdministrable()), isDroppable(state.getTypeId(), rights
			.isAdministrable()), itemActions);
	view.addItem(item);

	for (final ContentDTO content : container.getContents()) {
	    final StateToken siblingToken = stateToken.clone().setDocument(content.getId().toString());
	    final StateToken siblingParentToken = stateToken.clone().setDocument(null);
	    // TODO: rights not correct
	    final ContextNavigationItem sibling = new ContextNavigationItem(genId(siblingToken),
		    genId(siblingParentToken), "", content.getTitle(), ContentStatusDTO.publicVisible, siblingToken,
		    isDraggable(content.getTypeId(), rights.isAdministrable()), isDroppable(content.getTypeId(), rights
			    .isAdministrable()), null);
	    view.addItem(sibling);

	}

	for (final ContainerDTO siblingFolder : container.getChilds()) {
	    final StateToken siblingToken = stateToken.clone().setDocument(null).setFolder(
		    siblingFolder.getId().toString());
	    final StateToken siblingParentToken = stateToken.clone().setDocument(null).setFolder(
		    siblingFolder.getParentFolderId().toString());
	    // TODO: rights not correct
	    final ContextNavigationItem sibling = new ContextNavigationItem(genId(siblingToken),
		    genId(siblingParentToken), "", siblingFolder.getName(), ContentStatusDTO.publicVisible,
		    siblingToken, isDraggable(container.getTypeId(), rights.isAdministrable()), isDroppable(container
			    .getTypeId(), rights.isAdministrable()), null);
	    view.addItem(sibling);
	}
	view.selectItem(treeId);
    }

    private void createActions() {
	final ClientActionDescriptor<StateToken> addFolder = new ClientActionDescriptor<StateToken>(
		AccessRolDTO.Editor, ActionPosition.topBarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken parameter) {
			contentServiceProvider.get().addFolder(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), new Long(parameter.getFolder()),
				i18n.t("New folder"), new AsyncCallbackSimple<StateDTO>() {
				    public void onSuccess(final StateDTO state) {
					setState(state);
					stateManager.setRetrievedState(state);
					stateManager.reload();
					view.editItem(genId(state.getStateToken()));
				    }
				});
		    }
		});
	addFolder.setTextDescription(i18n.t("New folder"));
	addFolder.setParentMenuTitle(i18n.t("New"));

	final ClientActionDescriptor<StateToken> addDoc = new ClientActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topBarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			contentServiceProvider.get().addContent(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(),
				session.getCurrentState().getFolder().getId(), i18n.t("New document"),
				new AsyncCallbackSimple<StateDTO>() {
				    public void onSuccess(final StateDTO state) {
					setState(state);
					stateManager.setRetrievedState(state);
					stateManager.reload();
					view.editItem(genId(state.getStateToken()));
				    }
				});

		    }
		});
	addDoc.setTextDescription(i18n.t("New document"));
	addDoc.setParentMenuTitle(i18n.t("New"));

	final ClientActionDescriptor<StateToken> delContainer = new ClientActionDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.info("Sorry, in development");
		    }
		});
	delContainer.setTextDescription(i18n.t("Delete"));
	delContainer.setMustBeConfirmed(true);
	delContainer.setConfirmationTitle(i18n.t("Please confirm"));
	delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));

	final ClientActionDescriptor<StateToken> delContent = new ClientActionDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			contentServiceProvider.get().delContent(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), token.getDocument(),
				new AsyncCallbackSimple<String>() {
				    public void onSuccess(final String result) {
					final StateToken parent = token.clone();
					parent.setDocument(null);
					stateManager.gotoToken(parent);
				    }
				});
		    }
		});
	delContent.setTextDescription(i18n.t("Delete"));
	delContent.setMustBeConfirmed(true);
	delContent.setConfirmationTitle(i18n.t("Please confirm"));
	delContent.setConfirmationText(i18n.t("Are you sure?"));

	final ClientActionDescriptor<StateToken> go = new ClientActionDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			stateManager.gotoToken(token);
		    }
		});
	go.setTextDescription(i18n.t("Open"));

	final ClientActionDescriptor<StateToken> rename = new ClientActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			view.editItem(genId(stateToken));
		    }
		});
	rename.setTextDescription(i18n.t("Rename"));

	addAction(DocumentClientTool.TYPE_FOLDER, go);
	addAction(DocumentClientTool.TYPE_FOLDER, addDoc);
	addAction(DocumentClientTool.TYPE_FOLDER, addFolder);
	addAction(DocumentClientTool.TYPE_FOLDER, delContainer);
	addAction(DocumentClientTool.TYPE_FOLDER, rename);
	addAction(DocumentClientTool.TYPE_ROOT, addDoc);
	addAction(DocumentClientTool.TYPE_ROOT, addFolder);
	addAction(DocumentClientTool.TYPE_DOCUMENT, go);
	addAction(DocumentClientTool.TYPE_DOCUMENT, delContent);
	addAction(DocumentClientTool.TYPE_DOCUMENT, rename);
    }

    // public void showContainer(final StateToken state, final ContainerDTO
    // container, final AccessRightsDTO rights) {
    // final StateToken stateLoc = state.clone();
    //
    // createTreePath(stateLoc, container.getAbsolutePath());
    //
    // stateLoc.setDocument(null);
    // view.clear();
    // final List<ContainerDTO> folders = container.getChilds();
    // for (int index = 0; index < folders.size(); index++) {
    // final ContainerDTO folder = folders.get(index);
    // stateLoc.setFolder(folder.getId().toString());
    // // view.addItem(folder.getName(), folder.getTypeId(),
    // // stateLoc.getEncoded(), rights.isEditable());
    // final String parentFolderId = folder.getParentFolderId() == null ? null :
    // folder.getParentFolderId()
    // .toString();
    // final String token = StateToken.encode(stateLoc.getGroup(),
    // stateLoc.getTool(), folder.getId().toString(),
    // null);
    // view.addItem(folder.getId(), folder.getName(), parentFolderId, "#" +
    // token, false);
    // }
    //
    // stateLoc.setFolder(container.getId().toString());
    // final List<ContentDTO> contents = container.getContents();
    // for (int index = 0; index < contents.size(); index++) {
    // final ContentDTO dto = contents.get(index);
    // stateLoc.setDocument(dto.getId().toString());
    // // view.addItem(dto.getTitle(), dto.getTypeId(),
    // // stateLoc.getEncoded(), rights.isEditable());
    // final String token = StateToken.encode(stateLoc.getGroup(),
    // stateLoc.getTool(), stateLoc.getFolder(), dto
    // .getId().toString());
    // view.addItem(dto.getId(), dto.getTitle(), stateLoc.getFolder(), "#" +
    // token, true);
    // }
    // // view.selectItem(state.isComplete() ? state.getDocument() :
    // // state.getFolder(), state.isComplete());
    // }

    private void createTreePath(final StateToken state, final ContainerSimpleDTO[] absolutePath) {
	for (int i = 0; i < absolutePath.length; i++) {
	    final ContainerSimpleDTO folder = absolutePath[i];
	    final String parentFolderId = folder.getParentFolderId() == null ? null : folder.getParentFolderId()
		    .toString();
	    final StateToken folderStateToken = state.clone().setDocument(null).setFolder(folder.getId().toString());
	    final StateToken parentStateToken = state.clone().setDocument(null).setFolder(parentFolderId);
	    if (folder.getParentFolderId() != null) {
		// Bad rights, draggable/droppable
		final ContextNavigationItem parent = new ContextNavigationItem(genId(folderStateToken),
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

    @Deprecated
    private void registerDragDropTypes() {
	draggables.add(DocumentClientTool.TYPE_DOCUMENT);
	draggables.add(DocumentClientTool.TYPE_FOLDER);
	droppables.add(DocumentClientTool.TYPE_ROOT);
	droppables.add(DocumentClientTool.TYPE_FOLDER);
	droppables.add(DocumentClientTool.TYPE_DOCUMENT);
    }
}
