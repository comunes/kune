package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

public class SiteBarFactory {
    private static SiteBar siteBar;
    private static SiteMessage siteMessage;

    public static SiteBar getSiteBar() {
	if (siteBar == null) {
	    SiteBarPresenter siteBarPresenter = new SiteBarPresenter();
	    SiteBarPanel siteBarView = new SiteBarPanel(siteBarPresenter);
	    siteBarPresenter.init(siteBarView);
	    siteBar = siteBarPresenter;
	}
	return siteBar;
    }

    public static SiteMessage getSiteMessage() {
	if (siteMessage == null) {
	    SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
	    SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter);
	    siteMessagePresenter.init(siteMessageView);
	    Site.siteUserMessage = siteMessagePresenter;
	    siteMessage = siteMessagePresenter;
	}
	return siteMessage;
    }
}
