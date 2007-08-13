package org.ourproject.kune.app.client;

import org.ourproject.kune.docs.client.DocumentModule;
import org.ourproject.kune.platf.client.KuneModule;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationBuilder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
	String userHash = obtainUserHash();

	// if (userHash == null) {
	// MockServer.start(MockServer.GWT);
	// }

	if (isNotValid(userHash)) {
	    informUserAndStop();
	}
	KunePlatform platform = new KunePlatform();
	platform.install(new KuneModule());
	platform.install(new DocumentModule());
	Application app = new ApplicationBuilder(userHash, platform).build();

	String token = History.getToken();
	app.getStateManager().onHistoryChanged(token);
    }

    private boolean isNotValid(final String userHash) {
	return false;
    }

    private void informUserAndStop() {
	throw new RuntimeException("not logged in!");
    }

    private String obtainUserHash() {
	String cookie = Cookies.getCookie("userHash");
	GWT.log("USER HASH: " + cookie, null);
	return cookie;
    }

}
