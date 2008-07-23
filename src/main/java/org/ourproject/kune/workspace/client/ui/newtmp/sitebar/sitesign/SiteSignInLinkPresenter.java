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
		doAfterSignIn(user);
	    }
	});
	session.onUserSignOut(new Slot<Object>() {
	    public void onEvent(final Object parameter) {
		setLogged(false);
		view.setLoggedUserName("", "");
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SiteSignInLinkView view) {
	this.view = view;
	setLogged(false);
    }

    private void doAfterSignIn(final UserInfoDTO user) {
	if (user == null) {
	    setLogged(false);
	} else {
	    setLogged(true);
	    view.setLoggedUserName(user.getShortName(), user.getHomePage());
	}
    }

    private void setLogged(boolean logged) {
	view.setVisibleSignInLink(!logged);
	view.setLoggedUserMenuVisible(logged);
    }

}
