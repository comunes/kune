package org.ourproject.kune.sitebar.client.rpc;

import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteBarServiceMocked implements SiteBarServiceAsync {

    public void login(final String nick, final String pass, final AsyncCallback callback) {
	Site.showProgress("Login");
	Timer timer = new Timer() {
	    public void run() {
		Site.hideProgress();
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

}
