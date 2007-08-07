package org.ourproject.kune.app.client;

import org.ourproject.kune.docs.client.DocumentModule;
import org.ourproject.kune.platf.client.Application;
import org.ourproject.kune.platf.client.KuneModule;
import org.ourproject.kune.platf.client.KunePlatform;

import com.google.gwt.core.client.EntryPoint;

public class KuneEntryPoint implements EntryPoint {
    private final boolean useServer;

    public KuneEntryPoint() {
        useServer = false;
    }

    public void onModuleLoad() {
        if (!useServer) {
            MockServer.start(MockServer.GWT);
        }

        String userHash = obtainUserHash();
        if (isNotValid(userHash)) {
            informUserAndStop();
        }
        KunePlatform platform = new KunePlatform();
        platform.install(new KuneModule());
        platform.install(new DocumentModule());
        Application app = platform.buildApplication(userHash);
        app.getDispatcher().fire("init", null);
    }

    private boolean isNotValid(final String userHash) {
        return false;
    }

    private void informUserAndStop() {
        throw new RuntimeException("not logged in!");
    }

    // TODO: decidir una manera de hacer esto, para mi: meterlo en el html y
    // leerlo
    private String obtainUserHash() {
        return "xxxxx";
    }

}
