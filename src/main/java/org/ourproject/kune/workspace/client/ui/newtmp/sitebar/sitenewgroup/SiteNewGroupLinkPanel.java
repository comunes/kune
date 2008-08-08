package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.SiteToken;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;

public class SiteNewGroupLinkPanel implements SiteNewGroupLinkView {

    public SiteNewGroupLinkPanel(final SiteNewGroupLinkPresenter presenter, final WorkspaceSkeleton ws,
	    final I18nUITranslationService i18n) {
	final Hyperlink newGroupHyperlink = new Hyperlink();
	newGroupHyperlink.setText(i18n.t("Create New Group"));
	newGroupHyperlink.setTargetHistoryToken(SiteToken.newgroup.toString());
	ws.getSiteBar().addSeparator();
	ws.getSiteBar().add(newGroupHyperlink);
	ws.getSiteBar().addSpacer();
    }
}
