package org.ourproject.kune.workspace.client.sitebar.sitesign;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SiteSignOutLinkPanel implements SiteSignOutLinkView {

    private final Label signOutLabel;

    public SiteSignOutLinkPanel(final SiteSignOutLinkPresenter presenter, final I18nUITranslationService i18n,
	    final WorkspaceSkeleton ws) {
	signOutLabel = new Label();
	signOutLabel.setText(i18n.t("Sign out"));
	signOutLabel.addStyleName("k-sitebar-labellink");
	ws.getSiteBar().add(signOutLabel);
	signOutLabel.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		Site.showProgressProcessing();
		presenter.doSignOut();
	    }
	});
    }

    public void setVisible(final boolean visible) {
	signOutLabel.setVisible(visible);
    }
}
