package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface SignInAbstractView extends View {

    void center();

    void hide();

    void hideMessages();

    void mask(final String message);

    void maskProcessing();

    void reset();

    void setCookie(String userHash);

    void setErrorMessage(final String message, final SiteErrorType type);

    void show();

    void unMask();

}
