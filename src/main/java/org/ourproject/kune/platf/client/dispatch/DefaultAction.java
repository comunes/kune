package org.ourproject.kune.platf.client.dispatch;

import org.ourproject.kune.platf.client.Application;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.workspace.Workspace;

public abstract class DefaultAction implements Action {
    protected String userHash;
    protected Workspace workspace;
    protected State state;
    protected Dispatcher dispatcher;
    protected Application app;

    public void setUserHash(String userHash) {
	this.userHash = userHash;
    }

    public void setWorkspace(Workspace workspace) {
	this.workspace = workspace;
    }

    public void setState(State state) {
	this.state = state;
    }

    public void setDispatcher(Dispatcher dispatcher) {
	this.dispatcher = dispatcher;
    }

    public void setApp(Application app) {
	this.app = app;
    }

    public int toInt(Object value) {
	return Integer.parseInt((String) value);
    }

}
