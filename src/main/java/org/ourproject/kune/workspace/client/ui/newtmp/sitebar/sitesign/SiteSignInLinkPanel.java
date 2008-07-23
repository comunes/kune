package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.SiteToken;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;
import com.gwtext.client.widgets.ToolbarButton;

public class SiteSignInLinkPanel implements SiteSignInLinkView {
    private final ToolbarButton loggedUserMenu;
    private final Hyperlink signInHyperlink;

    public SiteSignInLinkPanel(final SiteSignInLinkPresenter presenter, final I18nUITranslationService i18n,
	    final WorkspaceSkeleton ws) {
	signInHyperlink = new Hyperlink();
	signInHyperlink.setText(i18n.t("Sign in to collaborate"));
	signInHyperlink.setTargetHistoryToken(SiteToken.signin.toString());
	loggedUserMenu = new ToolbarButton("user");
	ws.getSiteBar().add(signInHyperlink);
	// ws.getSiteBar().add(loggedUserMenu);
    }

    public void setLoggedUserMenuVisible(final boolean visible) {
	loggedUserMenu.setVisible(visible);
    }

    public void setLoggedUserName(final String name, final String homePage) {
	loggedUserMenu.setText(name);
    }

    public void setVisibleSignInLink(final boolean visible) {
	signInHyperlink.setVisible(visible);
    }

}
