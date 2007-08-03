package org.ourproject.kune.platf.client.workspace.actions;

import org.ourproject.kune.platf.client.Application;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dispatch.Injector;

public class DefaultActionInjector implements Injector {
	private final Application app;
	private Dispatcher dispatcher;
	private final State state;

	public DefaultActionInjector(Application app) {
		this.app = app;
		this.state = app.getState();
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void inject(Object object) {
		injectAction((WorkspaceAction) object);
	}

	private void injectAction(WorkspaceAction action) {
		action.setUserHash(state.user);
		action.setState(state);
		action.setApp(app);
		action.setWorkspace(app.getWorkspace());
		action.setDispatcher(dispatcher);
	}


}
