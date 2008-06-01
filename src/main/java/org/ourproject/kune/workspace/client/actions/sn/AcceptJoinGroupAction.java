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
package org.ourproject.kune.workspace.client.actions.sn;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class AcceptJoinGroupAction implements Action<String> {

    private final StateManager stateManager;
    private final Workspace workspace;
    private final Session session;
    private final I18nTranslationService i18n;

    public AcceptJoinGroupAction(final Session session, final StateManager stateManager, final Workspace workspace,
            final I18nTranslationService i18n) {
        this.session = session;
        this.workspace = workspace;
        this.stateManager = stateManager;
        this.i18n = i18n;
    }

    public void execute(final String groupShortName) {
        onAcceptJoinGroup(groupShortName);
    }

    private void onAcceptJoinGroup(final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.AcceptJoinGroup(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
                groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                    public void onSuccess(final SocialNetworkResultDTO result) {
                        Site.hideProgress();
                        Site.info(i18n.t("Member accepted"));
                        stateManager.setSocialNetwork(result);
                        workspace.getGroupMembersComponent().showCollabs();
                    }
                });

    }
}
