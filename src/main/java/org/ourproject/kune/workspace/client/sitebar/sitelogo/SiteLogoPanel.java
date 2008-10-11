package org.ourproject.kune.workspace.client.sitebar.sitelogo;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Image;

public class SiteLogoPanel implements SiteLogoView {

    private final Image siteLogoImg;

    public SiteLogoPanel(final SiteLogoPresenter presenter, final WorkspaceSkeleton ws) {
        siteLogoImg = new Image();
        ws.getSiteBar().add(siteLogoImg);
    }

    public void setSiteLogoUrl(final String siteLogoUrl) {
        siteLogoImg.setUrl(siteLogoUrl);
    }
}
