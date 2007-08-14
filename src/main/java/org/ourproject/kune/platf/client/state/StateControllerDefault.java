package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.tool.Tool;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateControllerDefault implements StateController {
    private final Application app;
    private final State state;
    private final ContentServiceAsync server;

    public StateControllerDefault(final Application app, final State state) {
	this.app = app;
	this.state = state;
	this.server = ContentService.App.getInstance();
    }

    public void reload() {
	onHistoryChanged(History.getToken());
    }

    public void onHistoryChanged(final String historyToken) {
	GWT.log("State: " + historyToken, null);
	onHistoryChanged(new StateToken(historyToken));
    }

    private void onHistoryChanged(final StateToken newState) {
	Site.showProgress("cargando...");
	server.getContent(state.user, newState.group, newState.tool, newState.folder, newState.document,
		new AsyncCallback() {
		    public void onFailure(final Throwable caught) {
			Site.hideProgress();
		    }

		    public void onSuccess(final Object result) {
			GWT.log("State response: " + result, null);
			loadContent((ContentDTO) result);
			Site.hideProgress();
		    }

		});
    }

    public String getUser() {
	return state.user;
    }

    private void loadContent(final ContentDTO content) {
	Workspace workspace = app.getWorkspace();
	workspace.showGroup(content.getGroup());
	String toolName = content.getToolName();
	workspace.setTool(toolName);

	Tool tool = app.getTool(toolName);
	tool.setContent(content);
	workspace.setContent(tool.getContent());
	workspace.setContext(tool.getContext());
    }

}
