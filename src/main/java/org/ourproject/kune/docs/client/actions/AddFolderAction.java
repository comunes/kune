/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddFolderAction implements Action {
    private final Session session;
    private final StateManager stateManager;

    public AddFolderAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final Object value, final Object extra) {
        String name = (String) value;
        GroupDTO group = session.getCurrentState().getGroup();
        ContainerDTO container = session.getCurrentState().getFolder();
        addFolder(name, group, container);
    }

    private void addFolder(final String name, final GroupDTO group, final ContainerDTO container) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addFolder(session.getUserHash(), group.getShortName(), container.getId(), name,
                new AsyncCallbackSimple<StateDTO>() {
                    public void onSuccess(final StateDTO state) {
                        Site.info(Kune.I18N.t("Folder created"));
                        stateManager.setRetrievedState(state);
                        // FIXME: Isn't using cache
                        stateManager.reloadContextAndTitles();
                        Site.hideProgress();
                    }
                });
    }
}
