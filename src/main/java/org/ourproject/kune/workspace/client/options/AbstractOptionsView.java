package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface AbstractOptionsView {

    void hideMessages();

    void setErrorMessage(final String message, final SiteErrorType type);

}
