package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public abstract class WorkspaceAction implements Action {
    protected Application app;
    protected Workspace workspace;
    protected Dispatcher dispatcher;
    protected StateController stateManager;
    protected String user;
    protected Session session;

    public void init(final Application app, final Session session, final StateController stateManager) {
	this.app = app;
	this.session = session;
	this.stateManager = stateManager;
	this.workspace = app.getWorkspace();
	this.dispatcher = app.getDispatcher();
	this.user = stateManager.getUser();
    }

    protected int toInt(final Object value) {
	return Integer.parseInt((String) value);
    }

    protected StateDTO getState() {
	return session.getCurrentState();
    }
}
