package org.ourproject.kune.app.client;

import org.ourproject.kune.docs.client.DocumentModule;
import org.ourproject.kune.platf.client.KuneModule;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationBuilder;
import org.ourproject.kune.workspace.client.actions.InitAction;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
	String userHash = obtainUserHash();

	if (userHash == null) {
	    MockServer.start(MockServer.GWT);
	}

	if (isNotValid(userHash)) {
	    informUserAndStop();
	}
	KunePlatform platform = new KunePlatform();
	platform.install(new KuneModule());
	platform.install(new DocumentModule());
	Application app = new ApplicationBuilder(userHash, platform).build();

	String token = History.getToken();
	if (token.length() > 0) {
	    app.getDispatcher().onHistoryChanged(token);
	} else {
	    app.getDispatcher().fire(InitAction.NAME, null);
	}
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
