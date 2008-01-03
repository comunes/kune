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

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class AddFolder implements Action {
    public void execute(final Object value, final Object extra, final Services services) {
        String name = (String) value;
        GroupDTO group = services.session.getCurrentState().getGroup();
        ContainerDTO container = services.session.getCurrentState().getFolder();
        addFolder(services, name, group, container);
    }

    private void addFolder(final Services services, final String name, final GroupDTO group,
            final ContainerDTO container) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addFolder(services.session.getUserHash(), group.getShortName(), container.getId(), name,
                new AsyncCallbackSimple() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Folder created"));
                        StateDTO state = (StateDTO) result;
                        services.stateManager.setRetrievedState(state);
                    }
                });
    }
}
