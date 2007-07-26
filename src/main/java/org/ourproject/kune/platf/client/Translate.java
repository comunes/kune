package org.ourproject.kune.platf.client;

import com.google.gwt.i18n.client.Dictionary;

public class Translate {

    private static Dictionary theme;
    
    public static void init(String locale) {
        theme = Dictionary.getDictionary(locale);
    }
    
    public static String putYourLogo() {
        return theme.get("putYourLogoHere");
    }

}
