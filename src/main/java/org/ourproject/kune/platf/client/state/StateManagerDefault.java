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

package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.WorkspaceManager;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class StateManagerDefault implements StateManager {
    private final Application app;
    private final ContentProvider provider;
    private final Workspace workspace;
    private StateDTO oldState;
    private final Session session;
    private final HistoryWrapper history;
    private final WorkspaceManager ws;

    public StateManagerDefault(final ContentProvider provider, final Application app, final Session session,
	    final HistoryWrapper history, final WorkspaceManager ws) {
	this.provider = provider;
	this.app = app;
	this.session = session;
	this.history = history;
	this.ws = ws;
	this.workspace = app.getWorkspace();
	this.oldState = null;
    }

    public void gotoToken(final String token) {
	setState(new StateToken(token));
    }

    public void onHistoryChanged(final String historyToken) {
	String oldStateEncoded = "";
	if (oldState != null) {
	    oldStateEncoded = oldState.getStateToken().getEncoded();
	}
	if (historyToken.equals(Site.NEWGROUP_TOKEN)) {
	    Site.doNewGroup(oldStateEncoded);
	} else if (historyToken.equals(Site.LOGIN_TOKEN)) {
	    Site.doLogin(oldStateEncoded);
	} else if (historyToken.equals(Site.TRANSLATE_TOKEN)) {
	    DefaultDispatcher.getInstance().fire(WorkspaceEvents.SHOW_TRANSLATOR, null);
	} else if (historyToken.equals(Site.FIXME_TOKEN)) {
	    if (oldState == null) {
		onHistoryChanged(new StateToken());
	    } else {
		loadContent(oldState);
	    }
	} else {
	    onHistoryChanged(new StateToken(historyToken));
	}
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    public void reload() {
	onHistoryChanged(history.getToken());
    }

    public void reloadContextAndTitles() {
	provider.getContent(session.getUserHash(), new StateToken(history.getToken()),
		new AsyncCallbackSimple<StateDTO>() {
		    public void onSuccess(final StateDTO newStateDTO) {
			loadContextOnly(newStateDTO);
			oldState = newStateDTO;
			workspace.getContentTitleComponent().setState(oldState);
			workspace.getContentSubTitleComponent().setState(oldState);
			Site.hideProgress();
		    }
		});
    }

    public void setRetrievedState(final StateDTO content) {
	final StateToken state = content.getStateToken();
	provider.cache(state, content);
	setState(state);
    }

    public void setSocialNetwork(final SocialNetworkResultDTO socialNet) {
	StateDTO state;
	if (session != null && (state = session.getCurrentState()) != null) {
	    // After a SN operation, usually returns a SocialNetworkResultDTO
	    // with new SN data and we refresh the state
	    // to avoid to reload() again the state
	    final SocialNetworkDTO groupMembers = socialNet.getGroupMembers();
	    final ParticipationDataDTO userParticipation = socialNet.getUserParticipation();
	    state.setGroupMembers(groupMembers);
	    state.setParticipation(userParticipation);
	    ws.setSocialNetwork(state);
	}
    }

    public void setState(final StateToken state) {
	history.newItem(state.getEncoded());
    }

    private void loadContent(final StateDTO state) {
	session.setCurrent(state);
	final GroupDTO group = state.getGroup();
	app.setGroupState(group.getShortName());
	final boolean isAdmin = state.getGroupRights().isAdministrable();
	if (isAdmin) {
	    workspace.getThemeMenuComponent().setVisible(true);
	} else {
	    workspace.getThemeMenuComponent().setVisible(false);
	}
	workspace.showGroup(group, isAdmin);

	final String toolName = state.getToolName();
	workspace.setTool(toolName);

	Site.sitebar.setState(state);
	final ClientTool clientTool = app.getTool(toolName);
	clientTool.setContent(state);
	clientTool.setContext(state);
	ws.setState(state);
	workspace.getContentTitleComponent().setState(state);
	workspace.getContentSubTitleComponent().setState(state);
	workspace.getContentBottomToolBarComponent().setRate(state, session.isLogged());
	workspace.setContent(clientTool.getContent());
	workspace.setContext(clientTool.getContext());
	workspace.getLicenseComponent().setLicense(state);
	Site.hideProgress();
    }

    private void loadContextOnly(final StateDTO state) {
	session.setCurrent(state);
	final String toolName = state.getToolName();
	final ClientTool clientTool = app.getTool(toolName);
	clientTool.setContext(state);
	workspace.setContext(clientTool.getContext());
    }

    private void onHistoryChanged(final StateToken newState) {
	provider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateDTO>() {
	    public void onSuccess(final StateDTO newStateDTO) {
		loadContent(newStateDTO);
		oldState = newStateDTO;
	    }
	});
    }
}
