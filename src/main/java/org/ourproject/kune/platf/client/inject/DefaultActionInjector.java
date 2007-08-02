package org.ourproject.kune.platf.client.inject;

import org.ourproject.kune.platf.client.App;
import org.ourproject.kune.platf.client.dispatch.DefaultAction;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;

public class DefaultActionInjector implements Injector {
	private final App app;
	private Dispatcher dispatcher;

	public DefaultActionInjector(App app) {
		this.app = app;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void inject(Object object) {
		injectAction((DefaultAction) object);
	}

	private void injectAction(DefaultAction action) {
		action.setUserHash(app.state.user);
		action.setState(app.state);
		action.setApp(app);
		action.setWorkspace(app.getWorkspace());
		action.setDispatcher(dispatcher);
	}


}
