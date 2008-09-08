package org.ourproject.kune.workspace.client.sitebar.sitelogo;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.signal.Slot;

public class SiteLogoPresenter implements SiteLogo {

    private SiteLogoView view;

    public SiteLogoPresenter(final Session session) {
	session.onInitDataReceived(new Slot<InitDataDTO>() {
	    public void onEvent(final InitDataDTO initData) {
		view.setSiteLogoUrl(initData.getSiteLogoUrl());
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SiteLogoView view) {
	this.view = view;
    }

}
