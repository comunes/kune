package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;
import com.gwtext.client.widgets.ToolbarButton;

public class SiteSignInLinkPanel implements SiteSignInLinkView {
    private final ToolbarButton loggedUserMenu;
    private final Hyperlink signInHyperlink;

    public SiteSignInLinkPanel(final SiteSignInLinkPresenter presenter, final WorkspaceSkeleton ws) {
	signInHyperlink = new Hyperlink();
	loggedUserMenu = new ToolbarButton();
    }
}
