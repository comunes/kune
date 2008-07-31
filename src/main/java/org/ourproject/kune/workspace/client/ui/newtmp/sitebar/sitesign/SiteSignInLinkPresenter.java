package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.signal.Slot;

public class SiteSignInLinkPresenter implements SiteSignInLink {

    private SiteSignInLinkView view;

    public SiteSignInLinkPresenter(final Session session) {
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO user) {
		view.setVisible(false);
	    }
	});
	session.onUserSignOut(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
		view.setVisible(true);
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SiteSignInLinkView view) {
	this.view = view;
	view.setVisible(false);
    }

}
