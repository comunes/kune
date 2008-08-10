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

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ContextItemsPresenter implements ContextItems {
    protected ContextItemsView view;
    private final I18nTranslationService i18n;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;

    public ContextItemsPresenter(final I18nTranslationService i18n, final StateManager stateManager,
	    final Session session, final Provider<ContentServiceAsync> contentServiceProvider) {
	this.i18n = i18n;
	this.stateManager = stateManager;
	this.session = session;
	this.contentServiceProvider = contentServiceProvider;
    }

    public void canCreate(final String buttonText, final String promptMessage, final Slot<String> slot) {
	view.addCommand(buttonText, promptMessage, slot);
    }

    public View getView() {
	return view;
    }

    public void init(final ContextItemsView view) {
	this.view = view;
    }

    public void onGoUp() {
	final StateDTO state = session.getCurrentState();
	final StateToken token = state.getStateToken();
	token.setDocument(null);
	token.setFolder(state.getFolder().getParentFolderId().toString());
	stateManager.setState(token);
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

    public void registerType(final String typeName, final AbstractImagePrototype image) {
	view.registerType(typeName, image);
    }

    public void setControlsVisible(final boolean visible) {
	view.setControlsVisible(visible);
    }

    public void setParentTreeVisible(final boolean visible) {
	view.setParentTreeVisible(visible);
    }

    public void showContainer(final StateToken state, final ContainerDTO container, final AccessRightsDTO rights) {
	state.setDocument(null);
	if (container.getParentFolderId() == null) {
	    // We translate root folder names (documents, chat room,
	    // etcetera)
	    view.setCurrentName(i18n.t(container.getName()));
	} else {
	    view.setCurrentName(container.getName());
	}
	view.clear();
	final List<ContainerDTO> folders = container.getChilds();
	for (int index = 0; index < folders.size(); index++) {
	    final ContainerDTO child = folders.get(index);
	    state.setFolder(child.getId().toString());
	    view.addItem(child.getName(), child.getTypeId(), state.getEncoded(), rights.isEditable());
	}

	state.setFolder(container.getId().toString());
	final List<ContentDTO> contents = container.getContents();
	for (int index = 0; index < contents.size(); index++) {
	    final ContentDTO dto = contents.get(index);
	    state.setDocument(dto.getId().toString());
	    view.addItem(dto.getTitle(), dto.getTypeId(), state.getEncoded(), rights.isEditable());
	}
	view.setParentButtonEnabled(container.getParentFolderId() != null);
	view.setControlsVisible(rights.isEditable());
	view.setAbsolutePath(container.getAbsolutePath());
    }
}
