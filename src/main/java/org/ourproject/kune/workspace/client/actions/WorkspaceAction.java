package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.ApplicationState;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.workspace.client.Workspace;

public abstract class WorkspaceAction implements Action {
    protected Application app;
    protected Workspace workspace;
    protected Dispatcher dispatcher;
    protected StateController stateManager;
    protected String user;

    public void init(final Application app, final ApplicationState applicationState, final StateController stateManager) {
	this.app = app;
	this.stateManager = stateManager;
	this.workspace = app.getWorkspace();
	this.dispatcher = app.getDispatcher();
	this.stateManager = stateManager;
	this.user = stateManager.getUser();
    }

    public int toInt(final Object value) {
	return Integer.parseInt((String) value);
    }

}
