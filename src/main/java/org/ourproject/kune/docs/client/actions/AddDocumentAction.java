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
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;

public class AddDocumentAction implements Action {
    public void execute(final Object value, final Object extra, final Services services) {
        addDocument(services, (String) value, services.session.getCurrentState().getFolder());
    }

    private void addDocument(final Services services, final String name, final ContainerDTO containerDTO) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addContent(services.session.getUserHash(), services.session.getCurrentState().getGroup().getShortName(),
                containerDTO.getId(), name, new AsyncCallbackSimple<StateDTO>() {
                    public void onSuccess(final StateDTO state) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Created, now you can edit the document"));
                        services.stateManager.setRetrievedState(state);
                    }
                });
    }
}
