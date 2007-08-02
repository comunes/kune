package org.ourproject.kune.sitebar.client.rpc;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarServiceMocked implements SiteBarServiceAsync {

    public void login(String nick, String pass, final AsyncCallback callback) {
        Timer timer = new Timer() {
            public void run() {
                callback.onSuccess("ThisIsTheUserHash");
            }
        };
        timer.schedule(1000);
    }

    public void logout(final AsyncCallback callback) {
        Timer timer = new Timer() {
            public void run() {
                callback.onSuccess(null);
            }
        };
        timer.schedule(1000);
    }

    public void createNewGroup(String shortName, String longName, String publicDesc, int type,
            final AsyncCallback callback) {
        Timer timer = new Timer() {
            public void run() {
                callback.onSuccess(null);
            }
        };
        timer.schedule(1000);

    }

}
