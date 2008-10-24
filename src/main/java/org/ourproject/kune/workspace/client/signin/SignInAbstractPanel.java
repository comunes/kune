package org.ourproject.kune.workspace.client.signin;

import java.util.Date;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialogExtended;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Toolbar;

public abstract class SignInAbstractPanel extends BasicDialogExtended {

    protected final I18nUITranslationService i18n;
    protected Label errorLabel;

    public SignInAbstractPanel(I18nUITranslationService i18n, String title, boolean modal, boolean autoscroll,
            int width, int heigth, String icon, String firstButtonTitle, String cancelButtonTitle,
            Listener0 onFirstButtonClick, Listener0 onCancelButtonClick) {
        this(i18n, title, modal, autoscroll, width, heigth, icon, firstButtonTitle, Ext.generateId(),
                cancelButtonTitle, Ext.generateId(), onFirstButtonClick, onCancelButtonClick);
    }

    public SignInAbstractPanel(I18nUITranslationService i18n, final String title, final boolean modal,
            final boolean autoscroll, final int width, final int heigth, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final Listener0 onFirstButtonClick, final Listener0 onCancelButtonClick) {
        super(title, modal, autoscroll, width, heigth, icon, firstButtonTitle, firstButtonId, cancelButtonTitle,
                cancelButtonId, onFirstButtonClick, onCancelButtonClick);
        this.i18n = i18n;
        errorLabel = new Label("");
        Toolbar messageToolbar = new Toolbar();
        messageToolbar.addElement(errorLabel.getElement());
        super.setBottomToolbar(messageToolbar);
    }

    @Override
    public void hide() {
        if (super.isVisible()) {
            super.hide();
        }
    }

    public void hideMessages() {
        errorLabel.setText("");
        // renderDialogIfNeeded();
    }

    public void mask(final String message) {
        getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void setCookie(final String userHash) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        final long duration = Session.SESSION_DURATION;
        final Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie(Site.USERHASH, userHash, expires, null, "/", false);
        GWT.log("Received hash: " + userHash, null);
    }

    public void setErrorMessage(final String message, final SiteErrorType type) {
        errorLabel.setText(message);
        // renderDialogIfNeeded();
    }

    public void unMask() {
        getEl().unmask();
    }

}
