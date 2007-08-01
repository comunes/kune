package org.ourproject.kune.platf.client.services;

import com.google.gwt.core.client.GWT;

public class Kune {
    private static Kune instance;
    public final Translate t;
    public final ColorScheme c;

    private Kune() {
        t = (Translate) GWT.create(Translate.class);
        c = (ColorScheme) GWT.create(ColorScheme.class);
    }

    public static Kune getInstance() {
        if (instance == null) {
            instance = new Kune();
        }
        return instance;
    }

    public String getUserHash() {
        return "userHash";
    }


}
