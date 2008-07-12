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

@Deprecated
public class SetCollabAsAdminAction implements Action<String> {

    private final Session session;
    private final StateManager stateManager;
    private final Workspace workspace;
    private final I18nTranslationService i18n;

    public SetCollabAsAdminAction(final Session session, final StateManager stateManager, final Workspace workspace,
	    final I18nTranslationService i18n) {
	this.session = session;
	this.stateManager = stateManager;
	this.workspace = workspace;
	this.i18n = i18n;
    }

    public void execute(final String value) {
	onSetCollabAsAdmin(value);
    }

    private void onSetCollabAsAdmin(final String groupShortName) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
	server.setCollabAsAdmin(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
		groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
		    public void onSuccess(final SocialNetworkResultDTO result) {
			Site.hideProgress();
			Site.info(i18n.t("Type of member changed"));
			stateManager.setSocialNetwork(result);
			// workspace.getGroupMembersComponent().showAdmins();
			// workspace.getGroupMembersComponent().showAdmins();
		    }
		});

    }
}
