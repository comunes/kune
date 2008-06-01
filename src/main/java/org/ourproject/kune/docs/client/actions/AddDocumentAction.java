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

package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddDocumentAction implements Action<String> {

    private final Session session;
    private final StateManager stateManager;
    private final I18nTranslationService i18n;

    public AddDocumentAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
    }

    public void execute(final String name) {
        addDocument(name, session.getCurrentState().getFolder());
    }

    private void addDocument(final String name, final ContainerDTO containerDTO) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addContent(session.getUserHash(), session.getCurrentState().getGroup().getShortName(), containerDTO
                .getId(), name, new AsyncCallbackSimple<StateDTO>() {
            public void onSuccess(final StateDTO state) {
                Site.hideProgress();
                Site.info(i18n.t("Created, now you can edit the document"));
                stateManager.setRetrievedState(state);
            }
        });
    }
}
