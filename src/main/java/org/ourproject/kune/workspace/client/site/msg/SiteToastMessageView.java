package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface SiteToastMessageView extends View {

    void showMessage(String title, String message, SiteErrorType type);

}
