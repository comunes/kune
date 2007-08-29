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
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateControllerDefault implements StateController {
    private final Application app;
    private final Session session;
    private final ContentProvider provider;

    public StateControllerDefault(final ContentProvider provider, final Application app, final Session session) {
	this.provider = provider;
	this.app = app;
	this.session = session;
    }

    public void reload() {
	onHistoryChanged(History.getToken());
    }

    public void onHistoryChanged(final String historyToken) {
	GWT.log("State: " + historyToken, null);
	onHistoryChanged(new StateToken(historyToken));
    }

    private void onHistoryChanged(final StateToken newState) {
	Site.showProgress(Kune.getInstance().t.Loading());
	provider.getContent(session.user, newState, new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
	    }

	    public void onSuccess(final Object result) {
		GWT.log("State response: " + result, null);
		loadContent((StateDTO) result);
	    }

	});
    }

    public void setState(final StateDTO content) {
	StateToken state = content.getState();
	provider.cache(state, content);
	setState(state);
    }

    public void setState(final StateToken state) {
	History.newItem(state.getEncoded());
    }

    private void loadContent(final StateDTO state) {
	GWT.log("content rights:" + state.getContentRights().toString(), null);
	GWT.log("folder rights:" + state.getFolderRights().toString(), null);
	session.setCurrent(state);
	GroupDTO group = state.getGroup();
	app.setGroupState(group.getShortName());
	Workspace workspace = app.getWorkspace();
	workspace.showGroup(group);
	String toolName = state.getToolName();
	workspace.setTool(toolName);

	ClientTool clientTool = app.getTool(toolName);
	clientTool.setContent(state);
	workspace.setContentTitle(state.getTitle());
	workspace.setContent(clientTool.getContent());
	workspace.setContext(clientTool.getContext());
	Site.hideProgress();
    }

    public String getUser() {
	return session.user;
    }

}
