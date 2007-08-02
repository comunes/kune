package org.ourproject.kune.platf.client.inject;

import org.ourproject.kune.platf.client.App;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.dispatch.DefaultAction;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.Services;

public class DefaultActionInjector implements Injector {
	private final State state;
	private final App app;
	private Dispatcher dispatcher;

	public DefaultActionInjector(State state, App app, Services services) {
		this.state = state;
		this.app = app;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void inject(Object object) {
		injectAction((DefaultAction) object);
	}

	private void injectAction(DefaultAction action) {
		action.setUserHash(state.getUserHash());
		action.setState(state);
		action.setApp(app);
		action.setWorkspace(app.getWorkspace());
		action.setDispatcher(dispatcher);
	}


}
