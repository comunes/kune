
package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

public class Kune {
    private static Kune instance;
    public static final I18nUITranslationService I18N = I18nUITranslationService.getInstance();
    public ColorTheme theme;

    private Kune() {
        theme = new ColorTheme();
    }

    public static Kune getInstance() {
        if (instance == null) {
            instance = new Kune();
        }
        return instance;
    }

}
