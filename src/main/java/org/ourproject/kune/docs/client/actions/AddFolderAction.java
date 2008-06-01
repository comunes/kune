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
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddFolderAction implements Action<String> {
    private final Session session;
    private final StateManager stateManager;
    private final I18nTranslationService i18n;

    public AddFolderAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
    }

    public void execute(final String name) {
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
                        Site.info(i18n.t("Folder created"));
                        stateManager.setRetrievedState(state);
                        // FIXME: Isn't using cache
                        stateManager.reloadContextAndTitles();
                        Site.hideProgress();
                    }
                });
    }
}
