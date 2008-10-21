package org.ourproject.kune.workspace.client.sitebar.siteoptions;

import org.ourproject.kune.platf.client.View;

public class SiteOptionsPresenter implements SiteOptions {

    private SiteOptionsView view;

    public SiteOptionsPresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(final SiteOptionsView view) {
        this.view = view;
    }

}
