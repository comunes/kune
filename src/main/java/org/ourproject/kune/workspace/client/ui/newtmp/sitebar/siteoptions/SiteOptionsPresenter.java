package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteoptions;

import org.ourproject.kune.platf.client.View;

public class SiteOptionsPresenter implements SiteOptions {

private SiteOptionsView view;

public SiteOptionsPresenter() {
}

public void init(SiteOptionsView view) {
this.view = view;
}


    public View getView() {
        return view;
    }

}

