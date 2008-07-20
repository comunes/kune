package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.platf.client.View;

public class SiteSignOutLinkPresenter implements SiteSignOutLink {

private SiteSignOutLinkView view;

public SiteSignOutLinkPresenter() {
}

public void init(SiteSignOutLinkView view) {
this.view = view;
}


    public View getView() {
        return view;
    }

}

