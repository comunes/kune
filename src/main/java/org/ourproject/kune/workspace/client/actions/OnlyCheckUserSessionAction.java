package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class OnlyCheckUserSessionAction implements Action<AsyncCallback<Object>> {
    private final Session session;

    public OnlyCheckUserSessionAction(final Session session) {
        this.session = session;
    }

    public void execute(final AsyncCallback<Object> value) {
        onOnlyCheckUserSession(value);
    }

    private void onOnlyCheckUserSession(final AsyncCallback<Object> callback) {
        UserServiceAsync server = UserService.App.getInstance();
        server.onlyCheckSession(session.getUserHash(), callback);
    }
}
