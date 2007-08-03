package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.bar.SiteBarView;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

public class SiteBarViewFactory {
    public static SiteBarView siteBarView;
    public static SiteMessageView siteMessageView;

    public static SiteBarView createSiteBarView(final SiteBarPresenter presenter) {
	if (siteBarView == null) {
	    siteBarView = new SiteBarPanel(presenter);
	}
	return siteBarView;
    }

    public static SiteMessageView createSiteMessageView(final SiteMessagePresenter presenter) {
	if (siteMessageView == null) {
	    siteMessageView = new SiteMessagePanel(presenter);
	}
	return siteMessageView;
    }
}
