package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface EntityOptions extends AbstractOptions {

    public void addOptionTab(View tab);

    public View getView();

    public void hideMessages();

    public void setErrorMessage(String message, SiteErrorType type);

    public void show();

}
