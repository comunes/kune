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
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.calclab.suco.client.signal.Slot2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContextNavigatorPresenter implements ContextNavigator {

    private ContextNavigatorView view;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final I18nUITranslationService i18n;
    private final HashMap<String, ActionCollection<StateToken>> actions;
    private final ArrayList<String> draggables;
    private final ArrayList<String> droppables;
    private final HashMap<String, String> contentTypesIcons;
    private final EntityTitle entityTitle;

    public ContextNavigatorPresenter(final StateManager stateManager, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider, final I18nUITranslationService i18n,
	    final EntityTitle entityTitle) {
	this.stateManager = stateManager;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
	this.i18n = i18n;
	this.entityTitle = entityTitle;
	actions = new HashMap<String, ActionCollection<StateToken>>();
	draggables = new ArrayList<String>();
	droppables = new ArrayList<String>();
	contentTypesIcons = new HashMap<String, String>();
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

    public void onItemRename(final String token, final String newName, final String oldName) {
	if (!newName.equals(oldName)) {
	    Site.showProgress(i18n.t("Renaming"));
	    final StateDTO currentState = session.getCurrentState();
	    contentServiceProvider.get().rename(session.getUserHash(), currentState.getGroup().getShortName(), token,
		    newName, new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
			    view.setItemText(genId(new StateToken(token)), oldName);
			    Site.error(i18n.t("Error renaming"));
			    Site.hideProgress();
			}

			public void onSuccess(final String result) {
			    Site.hideProgress();
			    if (session.getCurrentState().getStateToken().getEncoded().equals(token)) {
				// I have to update EntityTitle
				entityTitle.setContentTitle(newName);
			    }
			}
		    });
	}
    }

    public void registerContentTypeIcon(final String contentTypeId, final String iconUrl) {
	contentTypesIcons.put(contentTypeId, iconUrl);
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
	final ContainerDTO container = state.getFolder();

	createTreePath(state.getStateToken(), container.getAbsolutePath(), state.getFolderRights());

	if (state.hasDocument()) {
	    addItem(state.getTitle(), state.getTypeId(), state.getStatus(), state.getStateToken(), container
		    .getStateToken(), state.getContentRights());
	} else {
	    addItem(container.getName(), container.getTypeId(), ContentStatusDTO.publishedOnline, container
		    .getStateToken(), container.getStateToken().clone().setFolder(container.getParentFolderId()), state
		    .getFolderRights());
	}

	for (final ContentDTO content : container.getContents()) {
	    addItem(content.getTitle(), content.getTypeId(), content.getStatus(), content.getStateToken(), content
		    .getStateToken().clone().clearDocument(), content.getRights());
	}

	for (final ContainerSimpleDTO siblingFolder : container.getChilds()) {
	    addItem(siblingFolder.getName(), siblingFolder.getTypeId(), ContentStatusDTO.publishedOnline, siblingFolder
		    .getStateToken(), siblingFolder.getStateToken().clone()
		    .setFolder(siblingFolder.getParentFolderId()), state.getFolderRights());
	}
	view.selectItem(genId(state.getStateToken()));
    }

    private void addItem(final String title, final String contentTypeId, final ContentStatusDTO status,
	    final StateToken stateToken, final StateToken parentStateToken, final AccessRightsDTO rights) {
	final ActionCollection<StateToken> topActions = new ActionCollection<StateToken>();
	final ActionCollection<StateToken> itemActions = new ActionCollection<StateToken>();
	final ActionCollection<StateToken> bottomActions = new ActionCollection<StateToken>();

	createItemActions(rights, contentTypeId, topActions, itemActions, bottomActions);

	view.setTopActions(stateToken, topActions);
	view.setBottomActions(stateToken, bottomActions);
	final ContextNavigatorItem item = new ContextNavigatorItem(genId(stateToken), genId(parentStateToken),
		getContentTypeIcon(contentTypeId), title, status, stateToken, isDraggable(contentTypeId, rights
			.isAdministrable()), isDroppable(contentTypeId, rights.isAdministrable()), itemActions);
	view.addItem(item);
    }

    private void createItemActions(final AccessRightsDTO rights, final String contentTypeId,
	    final ActionCollection<StateToken> topActions, final ActionCollection<StateToken> itemActions,
	    final ActionCollection<StateToken> bottomActions) {
	boolean add = false;

	for (final ActionDescriptor<StateToken> action : actions.get(contentTypeId)) {
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
    }

    private void createTreePath(final StateToken state, final ContainerSimpleDTO[] absolutePath,
	    final AccessRightsDTO rights) {
	for (int i = 0; i < absolutePath.length; i++) {
	    final ContainerSimpleDTO folder = absolutePath[i];
	    final StateToken folderStateToken = folder.getStateToken();
	    final StateToken parentStateToken = state.clone().clearDocument().setFolder(folder.getParentFolderId());

	    if (folder.getParentFolderId() != null) {
		addItem(folder.getName(), folder.getTypeId(), ContentStatusDTO.publishedOnline, folderStateToken,
			parentStateToken, rights);
	    } else {
		// create root folder
		view.setRootItem(genId(folderStateToken), i18n.t("contents"), folderStateToken);
	    }
	}
    }

    private String genId(final StateToken token) {
	return "k-" + token.toString().replace(StateToken.SEPARATOR, "-");
    }

    private String getContentTypeIcon(final String typeId) {
	final String icon = contentTypesIcons.get(typeId);
	// TODO Auto-generated method stub
	return icon == null ? "" : icon;
    }

    private boolean isDraggable(final String typeId, final boolean administrable) {
	return administrable && draggables.contains(typeId);
    }

    private boolean isDroppable(final String typeId, final boolean administrable) {
	return administrable && droppables.contains(typeId);
    }

}
