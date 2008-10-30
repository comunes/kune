package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface SignInView extends SignInAbstractView {

    void center();

    void focusOnNickname();

    String getLoginPassword();

    String getNickOrEmail();

    void hide();

    void hideMessages();

    boolean isSignInFormValid();

    void mask(final String message);

    void maskProcessing();

    void reset();

    void setCookie(final String userHash);

    void setErrorMessage(final String message, final SiteErrorType type);

    void show();

    void unMask();

}
