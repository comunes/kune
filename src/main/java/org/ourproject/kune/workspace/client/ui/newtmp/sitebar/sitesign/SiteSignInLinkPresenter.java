package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.platf.client.View;

public class SiteSignInLinkPresenter implements SiteSignInLink {

private SiteSignInLinkView view;

public SiteSignInLinkPresenter() {
}

public void init(SiteSignInLinkView view) {
this.view = view;
}


    public View getView() {
        return view;
    }

}

