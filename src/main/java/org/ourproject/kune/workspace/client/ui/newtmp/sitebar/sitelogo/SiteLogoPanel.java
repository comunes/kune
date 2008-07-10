package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.SiteBar;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Image;

public class SiteLogoPanel implements SiteLogoView {

    private final Image siteLogoImg;

    public SiteLogoPanel(final SiteLogoPresenter presenter, final WorkspaceSkeleton ws) {
	siteLogoImg = new Image();
	final SiteBar siteBar = ws.getSiteBar();
	siteBar.addSpacer();
	siteBar.addSpacer();
	siteBar.add(siteLogoImg);
    }

    public void setSiteLogoUrl(final String siteLogoUrl) {
	siteLogoImg.setUrl(siteLogoUrl);
    }
}
