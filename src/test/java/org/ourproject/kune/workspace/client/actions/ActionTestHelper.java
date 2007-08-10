package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.stubs.ApplicationStub;
import org.ourproject.kune.platf.client.stubs.NiceState;
import org.ourproject.kune.workspace.client.Workspace;

public class ActionTestHelper {
    public static final boolean STRICT = false;
    public static final boolean NICE = true;

    public Application app;
    public Dispatcher dispatcher;
    public State state;
    public String userHash;
    public Workspace workspace;
    private final WorkspaceAction action;

    public ActionTestHelper(final WorkspaceAction action, final boolean useNiceMocks) {
	this.action = action;
	app = new ApplicationStub(useNiceMocks);
	state = new NiceState();
	inject(action);
    }

    public void inject(final Object object) {
	WorkspaceAction action = (WorkspaceAction) object;
	action.init(app, null, null);
    }

    public void execute(final Object value) {
	action.execute(value);
    }

}
