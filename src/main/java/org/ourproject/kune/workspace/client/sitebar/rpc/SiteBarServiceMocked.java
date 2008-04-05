
package org.ourproject.kune.workspace.client.sitebar.rpc;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarServiceMocked implements UserServiceAsync {

    public void login(final String nick, final String pass, final AsyncCallback<UserInfoDTO> callback) {
        Site.showProgress("Login");
        Timer timer = new Timer() {
            public void run() {
                Site.hideProgress();
                callback.onSuccess(new UserInfoDTO());
            }
        };
        timer.schedule(1000);
    }

    public void logout(final String userHash, final AsyncCallback<?> callback) {
        timerAndSuccess(callback);
    }

    @SuppressWarnings("unchecked")
    private void timerAndSuccess(final AsyncCallback callback) {
        Timer timer = new Timer() {
            public void run() {
                callback.onSuccess(null);
            }
        };
        timer.schedule(1000);
    }

    public void createUser(final UserDTO user, final AsyncCallback<UserInfoDTO> asyncCallback) {
        timerAndSuccess(asyncCallback);
    }

    public void reloadUserInfo(final String userHash, final AsyncCallback<UserInfoDTO> asyncCallback) {
        timerAndSuccess(asyncCallback);
    }

    public void onlyCheckSession(final String userHash, final AsyncCallback<?> asyncCallback) {
        timerAndSuccess(asyncCallback);
    }
}
