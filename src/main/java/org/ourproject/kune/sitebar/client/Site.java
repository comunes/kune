package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;

public class Site {
    public static SiteMessage siteUserMessage;

    public static SiteBar sitebar;

    public static void info(final String value) {
        siteUserMessage.setValue(value, SiteMessage.INFO);
    }

    public static void important(final String value) {
        siteUserMessage.setValue(value, SiteMessage.IMP);
    }

    public static void veryImportant(final String value) {
        siteUserMessage.setValue(value, SiteMessage.VERYIMP);
    }

    public static void error(final String value) {
        siteUserMessage.setValue(value, SiteMessage.ERROR);
    }

    public static void showProgress(final String text) {
        sitebar.showProgress(text);
    }

    public static void hideProgress() {
        sitebar.hideProgress();
    }
}
