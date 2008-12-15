package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface AbstractOptionsView extends View {

    void addOptionTab(View tab);

    void createAndShow();

    void hide();

    void hideMessages();

    void setErrorMessage(final String message, final SiteErrorType type);

}
