package org.ourproject.kune.platf.client.workspace.actions;

import org.easymock.EasyMock;
import org.ourproject.kune.platf.client.Application;
import org.ourproject.kune.platf.client.State;
import org.ourproject.kune.platf.client.dispatch.DefaultAction;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.inject.Injector;
import org.ourproject.kune.platf.client.stubs.NiceState;
import org.ourproject.kune.platf.client.workspace.Workspace;

public class ActionTestHelper implements Injector {
    public static final boolean STRICT = false;
    public static final boolean NICE = true;

    private final boolean useNiceMocks;
    public Application app;
    public Dispatcher dispatcher;
    public State state;
    public String userHash;
    public Workspace workspace;
    private final DefaultAction action;

    public ActionTestHelper(final DefaultAction action, final boolean useNiceMocks) {
	this.action = action;
	this.useNiceMocks = useNiceMocks;
	app = mock(Application.class);
	dispatcher = mock(Dispatcher.class);
	state = new NiceState();
	userHash = state.user;
	workspace = mock(Workspace.class);
	inject(action);
    }

    private <T> T mock(final Class<T> type) {
	if (useNiceMocks) {
	    return EasyMock.createNiceMock(type);
	} else {
	    return EasyMock.createStrictMock(type);
	}
    }

    public void inject(final Object object) {
	DefaultAction action = (DefaultAction) object;
	action.setApp(app);
	action.setDispatcher(dispatcher);
	action.setState(state);
	action.setUserHash(userHash);
	action.setWorkspace(workspace);
    }

    public void setDispatcher(final Dispatcher dispatcher) {
    }

    public void execute(final Object value) {
	action.execute(value);
    }

}
