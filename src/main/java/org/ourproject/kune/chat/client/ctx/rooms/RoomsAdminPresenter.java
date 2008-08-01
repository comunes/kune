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

package org.ourproject.kune.chat.client.ctx.rooms;

import org.ourproject.kune.chat.client.ChatClientTool;
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

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot;

public class RoomsAdminPresenter implements RoomsAdmin {
    private final ContextItems contextItems;

    public RoomsAdminPresenter(final ContextItems contextItems, final I18nTranslationService i18n,
	    final Provider<StateManager> stateManagerProvider, final Session session,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	this.contextItems = contextItems;
	final ContextItemsImages images = ContextItemsImages.App.getInstance();
	contextItems.setParentTreeVisible(false);
	contextItems.registerType(ChatClientTool.TYPE_CHAT, images.page());
	contextItems.registerType(ChatClientTool.TYPE_ROOM, images.chatGreen());
	contextItems.canCreate(ChatClientTool.TYPE_ROOM, i18n.t("New chat room"), new Slot<String>() {
	    public void onEvent(final String name) {
		Site.showProgressProcessing();
		final String groupShortName = session.getCurrentState().getGroup().getShortName();
		final Long containerId = session.getCurrentState().getFolder().getId();
		contentServiceProvider.get().addRoom(session.getUserHash(), groupShortName, containerId,
			groupShortName + "-" + name, new AsyncCallbackSimple<StateDTO>() {
			    public void onSuccess(final StateDTO state) {
				final StateManager stateManager = stateManagerProvider.get();
				stateManager.setRetrievedState(state);
				// FIXME: Isn't using cache (same in Add folder)
				stateManager.reloadContextAndTitles();
				Site.hideProgress();
			    }
			});
	    }
	});
    }

    public View getView() {
	return contextItems.getView();
    }

    // FIXME: cierta lógica de negocio en el cliente
    // ¿debemos quitarla? es decir, enviar desde el servidor si se puede añadir
    // hijos al contenedor
    public void showRoom(final StateToken token, final ContainerDTO container, final AccessRightsDTO rights) {
	contextItems.showContainer(token, container, rights);
	final String type = container.getTypeId();
	if (type.equals(ChatClientTool.TYPE_ROOM)) {
	    contextItems.setControlsVisible(false);
	}
    }

}
