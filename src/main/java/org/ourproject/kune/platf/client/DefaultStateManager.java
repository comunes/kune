package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.UIObject;

public class DefaultStateManager implements StateManager {
    private final Application app;
    private final State state;
    private final KuneServiceAsync server;

    public DefaultStateManager(final Application app, final State state) {
	this.app = app;
	this.state = state;
	this.server = KuneService.App.getInstance();
    }

    public void onHistoryChanged(final String historyToken) {
	onHistoryChanged(new HistoryToken(historyToken));
    }

    private void onHistoryChanged(final HistoryToken newState) {
	if (newState.isComplete()) {
	    Tool tool = app.getTool(newState.tool);
	    tool.setState(newState.group, newState.folder, newState.document);
	    app.getWorkspace().setContext(tool.getContext());
	    app.getWorkspace().setContent(tool.getContent());
	    loadGroup(newState.group);
	}
	// app.changeGroup(newState.group);
	// app.getWorkspace().show(newState.group, newState.folder,
	// newState.document);
	UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);

    }

    private void loadGroup(final String groupName) {
	if (!state.isCurrentGroup(groupName)) {
	    server.getGroup(state.user, groupName, new AsyncCallback() {
		public void onFailure(final Throwable caught) {
		}

		public void onSuccess(final Object result) {
		}
	    });
	}
    }

    public String getUser() {
	return state.user;
    }

    public void setState(final String groupShortName, final String toolName, final Long folderId, final Long contentId) {
	History.newItem(HistoryToken.encode(groupShortName, toolName, folderId.toString(), contentId.toString()));
    }

    public void setState(final GroupDTO group) {
	changeGroup(group);
	setState(group.getShortName(), group.getDefaultToolName(), group.getDefaultFolderId(), group
		.getDefaultContentId());
    }

    private void changeGroup(final GroupDTO group) {
	state.setGroup(group);
	app.getWorkspace().showGroupLogo(group);
    }
}
