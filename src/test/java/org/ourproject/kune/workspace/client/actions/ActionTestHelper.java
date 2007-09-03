package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.stubs.ApplicationStub;
import org.ourproject.kune.platf.client.stubs.NiceState;
import org.ourproject.kune.workspace.client.Workspace;

public class ActionTestHelper {
    public static final boolean STRICT = false;
    public static final boolean NICE = true;

    public Application app;
    public Dispatcher dispatcher;
    public Session session;
    public String userHash;
    public Workspace workspace;
    private final Action action;
    private final Services services;

    public ActionTestHelper(final Action action, final boolean useNiceMocks) {
	this.action = action;
	app = new ApplicationStub(useNiceMocks);
	session = new NiceState();
	services = new Services("userHash", app, session, app.getStateManager(), app.getDispatcher());
    }

    public void execute(final Object value) {
	action.execute(value, null, services);
    }

}
