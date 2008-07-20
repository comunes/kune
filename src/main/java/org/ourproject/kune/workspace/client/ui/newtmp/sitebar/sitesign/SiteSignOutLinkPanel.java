package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Label;

public class SiteSignOutLinkPanel implements SiteSignOutLinkView {

    private final Label signOutLabel;

    public SiteSignOutLinkPanel(final SiteSignOutLinkPresenter presenter, final WorkspaceSkeleton ws) {
	signOutLabel = new Label();
    }
}
