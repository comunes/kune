package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface AbstractOptions {

    public void hideMessages();

    public void setErrorMessage(String message, SiteErrorType type);

    void addOptionTab(View view);

    void show();

}
