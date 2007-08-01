package org.ourproject.kune.sitebar.client.rpc;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarServiceMocked implements SiteBarServiceAsync {

    public void login(String nick, String pass, final AsyncCallback callback) {
        FireLog.info("login:" + nick);
        Timer timer = new Timer() {
            public void run() {
                callback.onSuccess("ThisIsTheUserHash");
            }
        };
        timer.schedule(1000);
    }

    public void logout(AsyncCallback callback) {
        FireLog.info("Logout");
    }

}
