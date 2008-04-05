package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserLogoutAction implements Action<AsyncCallback<Object>> {

    private final Session session;

    public UserLogoutAction(final Session session) {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    public void execute(final AsyncCallback<Object> value) {
        onLogout(value);
    }

    private void onLogout(final AsyncCallback<Object> callback) {
        UserServiceAsync userService = UserService.App.getInstance();
        userService.logout(session.getUserHash(), callback);
    }
}
