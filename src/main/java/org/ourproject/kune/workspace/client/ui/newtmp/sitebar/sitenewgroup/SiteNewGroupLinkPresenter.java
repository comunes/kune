package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup;

import org.ourproject.kune.platf.client.View;

public class SiteNewGroupLinkPresenter implements SiteNewGroupLink {

private SiteNewGroupLinkView view;

public SiteNewGroupLinkPresenter() {
}

public void init(SiteNewGroupLinkView view) {
this.view = view;
}


    public View getView() {
        return view;
    }

}

