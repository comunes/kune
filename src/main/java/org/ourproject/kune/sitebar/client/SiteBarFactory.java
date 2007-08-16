package org.ourproject.kune.sitebar.client;

import org.ourproject.kune.platf.client.group.NewGroupForm;
import org.ourproject.kune.platf.client.group.NewGroupListener;
import org.ourproject.kune.platf.client.group.NewGroupFormPanel;
import org.ourproject.kune.platf.client.group.NewGroupFormPresenter;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.login.LoginForm;
import org.ourproject.kune.sitebar.client.login.LoginListener;
import org.ourproject.kune.sitebar.client.login.LoginFormPanel;
import org.ourproject.kune.sitebar.client.login.LoginFormPresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePanel;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

public class SiteBarFactory {
    private static SiteMessage siteMessage;

    public static SiteBar createSiteBar(final SiteBarListener listener) {
	SiteBarPresenter siteBarPresenter = new SiteBarPresenter(listener);
	SiteBarPanel siteBarView = new SiteBarPanel(siteBarPresenter);
	siteBarPresenter.init(siteBarView);
	Site.sitebar = siteBarPresenter;
	return siteBarPresenter;
    }

    public static SiteMessage getSiteMessage() {
	if (siteMessage == null) {
	    SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
	    SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter);
	    siteMessagePresenter.init(siteMessageView);
	    siteMessage = siteMessagePresenter;
	    Site.siteUserMessage = siteMessagePresenter;
	}
	return siteMessage;
    }

    public static LoginForm createLogin(final LoginListener listener) {
	LoginFormPresenter presenter = new LoginFormPresenter(listener);
	LoginFormPanel view = new LoginFormPanel(presenter);
	presenter.init(view);
	return presenter;
    }

    public static NewGroupForm createNewGroup(final NewGroupListener listener) {
	NewGroupFormPresenter presenter = new NewGroupFormPresenter(listener);
	NewGroupFormPanel view = new NewGroupFormPanel(presenter);
	presenter.init(view);
	return presenter;
    }
}
