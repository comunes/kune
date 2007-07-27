package org.ourproject.kune.platf.client;

import com.google.gwt.core.client.GWT;

public class Kune {
    private static Kune instance;
    public Translate t;

    private Kune() {
        t = (Translate) GWT.create(Translate.class);
    }

    public static Kune getInstance() {
        if (instance == null) {
            instance = new Kune();
        }
        return instance;
    }

    public static String getUserHash() {
        return "userHash";
    }


}
