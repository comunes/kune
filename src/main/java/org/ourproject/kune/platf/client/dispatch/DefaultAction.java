package org.ourproject.kune.platf.client.dispatch;

import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.workspace.WorkspacePresenter;

public abstract class DefaultAction implements Action {
	protected String userHash;
	protected WorkspacePresenter workspace;
	protected State state;
	protected Dispatcher dispatcher;

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public void setWorkspace(WorkspacePresenter workspace) {
		this.workspace = workspace;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
