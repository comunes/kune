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

package org.ourproject.kune.docs.client.ctx.folder;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsImages;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;

public class FolderContextPresenter implements FolderContext {
    private final ContextItems contextItems;

    public FolderContextPresenter(final ContextItems contextItems, final StateManager stateManager,
	    final Session session, final I18nTranslationService i18n,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	this.contextItems = contextItems;
	final ContextItemsImages contextImages = ContextItemsImages.App.getInstance();
	contextItems.registerType(DocumentClientTool.TYPE_DOCUMENT, contextImages.pageWhite());
	contextItems.registerType(DocumentClientTool.TYPE_FOLDER, contextImages.folder());
	contextItems.canCreate(i18n.t("New document"), i18n.t("Add a document"), new Slot<String>() {
	    public void onEvent(final String name) {
		Site.showProgressProcessing();
		contentServiceProvider.get().addContent(session.getUserHash(),
			session.getCurrentState().getGroup().getShortName(),
			session.getCurrentState().getFolder().getId(), name, new AsyncCallbackSimple<StateDTO>() {
			    public void onSuccess(final StateDTO state) {
				Site.info(i18n.t("Created, now you can edit the document"));
				stateManager.setRetrievedState(state);
				Site.hideProgress();
			    }
			});
	    }
	});
	contextItems.canCreate(i18n.t("New folder"), i18n.t("Add a folder"), new Slot<String>() {
	    public void onEvent(final String name) {
		Site.showProgressProcessing();
		contentServiceProvider.get().addFolder(session.getUserHash(),
			session.getCurrentState().getGroup().getShortName(),
			session.getCurrentState().getFolder().getId(), name, new AsyncCallbackSimple<StateDTO>() {
			    public void onSuccess(final StateDTO state) {
				Site.info(i18n.t("Folder created"));
				stateManager.setRetrievedState(state);
				// FIXME: Isn't using cache
				stateManager.reloadContextAndTitles();
				Site.hideProgress();
			    }
			});
	    }
	});
	contextItems.setParentTreeVisible(true);
    }

    public View getView() {
	return contextItems.getView();
    }

    public void setContainer(final StateToken state, final ContainerDTO container, final AccessRightsDTO rights) {
	contextItems.showContainer(state, container, rights);
    }

}
