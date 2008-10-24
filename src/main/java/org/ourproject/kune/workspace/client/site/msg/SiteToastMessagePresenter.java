package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

public class SiteToastMessagePresenter implements SiteToastMessage {

    private SiteToastMessageView view;

    public SiteToastMessagePresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(SiteToastMessageView view) {
        this.view = view;
    }

    public void showMessage(String title, final String message, final SiteErrorType type) {
        view.showMessage(title, message, type);
    }
}
