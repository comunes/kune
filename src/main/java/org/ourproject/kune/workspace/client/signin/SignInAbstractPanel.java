package org.ourproject.kune.workspace.client.signin;

import java.util.Date;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialogExtended;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Toolbar;

public abstract class SignInAbstractPanel extends BasicDialogExtended {

    protected final I18nUITranslationService i18n;
    protected Label errorLabel;
    private final Image errorIcon;
    private final Toolbar messageToolbar;

    public SignInAbstractPanel(I18nUITranslationService i18n, String title, boolean modal, boolean autoscroll,
            int width, int heigth, String icon, String firstButtonTitle, String cancelButtonTitle,
            Listener0 onFirstButtonClick, Listener0 onCancelButtonClick, Images images, String errorLabelId) {
        this(i18n, title, modal, autoscroll, width, heigth, icon, firstButtonTitle, Ext.generateId(),
                cancelButtonTitle, Ext.generateId(), onFirstButtonClick, onCancelButtonClick, images, errorLabelId);
    }

    public SignInAbstractPanel(I18nUITranslationService i18n, final String title, final boolean modal,
            final boolean autoscroll, final int width, final int heigth, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final Listener0 onFirstButtonClick, final Listener0 onCancelButtonClick,
            Images images, String errorLabelId) {
        super(title, modal, autoscroll, width, heigth, icon, firstButtonTitle, firstButtonId, cancelButtonTitle,
                cancelButtonId, onFirstButtonClick, onCancelButtonClick);
        this.i18n = i18n;
        errorLabel = new Label("");
        errorLabel.ensureDebugId(errorLabelId);
        messageToolbar = new Toolbar();
        errorIcon = new Image();
        Images.App.getInstance().error().applyTo(errorIcon);
        messageToolbar.addSpacer();
        messageToolbar.addElement(errorIcon.getElement());
        messageToolbar.setCls("k-error-tb");
        messageToolbar.addSpacer();
        messageToolbar.addSpacer();
        messageToolbar.addElement(errorLabel.getElement());
        errorIcon.setVisible(false);
        messageToolbar.setVisible(false);

        super.setBottomToolbar(messageToolbar);
    }

    @Override
    public void hide() {
        if (super.isVisible()) {
            super.hide();
        }
    }

    public void hideMessages() {
        errorIcon.setVisible(false);
        errorLabel.setText("");
        messageToolbar.setVisible(false);
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
        errorIcon.setVisible(true);
        messageToolbar.setVisible(true);
    }

    public void unMask() {
        getEl().unmask();
    }
}
