package org.ourproject.kune.workspace.client.sitebar;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.sitebar.bar.SiteBar;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessage;

public class Site {
    public static SiteMessage siteUserMessage;

    public static SiteBar sitebar;

    public static final String LOGIN_TOKEN = "login";

    public static final String NEWGROUP_TOKEN = "newgroup";

    public static final String FIXME_TOKEN = "fixme";

    public static final String TRANSLATE_TOKEN = "translate";

    public static final String IN_DEVELOPMENT = " (in development)";

    public static void info(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.INFO);
    }

    public static void important(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.IMP);
    }

    public static void veryImportant(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.VERYIMP);
    }

    public static void error(final String value) {
        siteUserMessage.setMessage(value, SiteMessage.ERROR);
    }

    public static void showProgress(final String text) {
        sitebar.showProgress(text);
    }

    public static void hideProgress() {
        sitebar.hideProgress();
    }

    public static void showProgressProcessing() {
        sitebar.showProgress(Kune.I18N.t("Processing"));
    }

    public static void showProgressLoading() {
        sitebar.showProgress(Kune.I18N.t("Loading"));
    }

    public static void showProgressSaving() {
        sitebar.showProgress(Kune.I18N.t("Saving"));
    }

    public static void doNewGroup(final String returnToken) {
        sitebar.doNewGroup(returnToken);
    }

    public static void doLogin(final String returnToken) {
        sitebar.doLogin(returnToken);
    }

    public static void doLogout() {
        sitebar.doLogout();
    }

    public static void showAlertMessage(final String message) {
        sitebar.showAlertMessage(message);
    }

    public static void mask() {
        sitebar.mask();
    }

    public static void mask(final String message) {
        sitebar.mask(message);
    }

    public static void unMask() {
        sitebar.unMask();
    }

}
