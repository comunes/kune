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

package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerDefault implements StateManager {
    private final Application app;
    private final Session session;
    private final ContentProvider provider;
    private final Workspace workspace;
    private String oldState;
    private String lastTheme = "purpleKuneTheme";

    public StateManagerDefault(final ContentProvider provider, final Application app, final Session session) {
	this.provider = provider;
	this.app = app;
	this.session = session;
	this.oldState = "";
	this.workspace = app.getWorkspace();
    }

    public void reload() {
	onHistoryChanged(History.getToken());
    }

    public void onHistoryChanged(final String historyToken) {
	GWT.log("State: " + historyToken, null);
	if (isNotIgnore(historyToken)) {
	    onHistoryChanged(new StateToken(historyToken));
	} else {
	    onHistoryChanged(oldState);
	}
    }

    private boolean isNotIgnore(final String historyToken) {
	return !historyToken.equals("fixme");
    }

    private void onHistoryChanged(final StateToken newState) {
	Site.showProgress(Kune.getInstance().t.Loading());
	provider.getContent(session.user, newState, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
		oldState = newState.getEncoded();
		try {
		    throw caught;
		} catch (final AccessViolationException e) {
		    Site.error("You can't access this content");
		} catch (final GroupNotFoundException e) {
		    Site.error("Group not found");
		} catch (final Throwable e) {
		    GWT.log("Error getting content", null);
		    throw new RuntimeException();
		}
	    }

	    public void onSuccess(final Object result) {
		GWT.log("State response: " + result, null);
		loadContent((StateDTO) result);
		oldState = newState.getEncoded();
	    }

	});
    }

    public void setState(final StateDTO content) {
	final StateToken state = content.getState();
	provider.cache(state, content);
	setState(state);
    }

    public void setState(final StateToken state) {
	History.newItem(state.getEncoded());
    }

    private void loadContent(final StateDTO state) {
	GWT.log("title: " + state.getTitle(), null);
	StateToken oldStateToken = new StateToken(oldState);
	session.setCurrent(state);
	final GroupDTO group = state.getGroup();
	app.setGroupState(group.getShortName());
	// check def theme... (this is only for ui test)
	if (lastTheme == "defaultKuneTheme") {
	    lastTheme = "greenKuneTheme";
	} else if (lastTheme == "greenKuneTheme") {
	    lastTheme = "blueKuneTheme";
	} else if (lastTheme == "blueKuneTheme") {
	    lastTheme = "greyKuneTheme";
	} else if (lastTheme == "greyKuneTheme") {
	    lastTheme = "purpleKuneTheme";
	} else if (lastTheme == "purpleKuneTheme") {
	    lastTheme = "defaultKuneTheme";
	}
	workspace.setTheme(lastTheme);
	workspace.showGroup(group);
	final String toolName = state.getToolName();
	workspace.setTool(toolName);

	final ClientTool clientTool = app.getTool(toolName);
	clientTool.setContent(state);
	workspace.getContentTitleComponent().setContentTitle(state.getTitle(), "11/06/07");
	workspace.getContentSubTitleComponent().setContentSubTitle("by Luther Blissett, Luther Blissett Jr", "English");
	// , Double rate, Integer rateByUsers en Content...
	workspace.setContent(clientTool.getContent());
	workspace.setContext(clientTool.getContext());
	workspace.getLicenseComponent().setLicense(state.getGroup().getLongName(), state.getLicense());
	// TODO: put GroupRights inside ParticipationDataDTO
	if (oldStateToken.hasGroup() && oldStateToken.getGroup().equals(state.getGroup().getShortName())) {
	    // Same group, do nothing
	} else {
	    loadSocialNetwork();
	}
	// only for UI tests:
	workspace.getBuddiesPresenceComponent().setBuddiesPresence();
	Site.hideProgress();
    }

    public String getUser() {
	return session.user;
    }

    public void reloadSocialNetwork() {
	Site.sitebar.reloadUserInfo(session.user);
	loadSocialNetwork();
    }

    private void loadSocialNetwork() {
	// FIXME: bug: session.getCurrentState null in init, logged: onLoggedIn
	// --> reloadSN --> loadSN --> bug
	StateDTO state = session.getCurrentState();
	workspace.getGroupMembersComponent().getGroupMembers(session.user, state.getGroup(), state.getGroupRights());
	workspace.getParticipationComponent().getParticipation(session.user, state.getGroup(), state.getGroupRights());
    }

}
