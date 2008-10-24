package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public interface SiteToastMessage {

    void showMessage(String title, String message, SiteErrorType type);

}
