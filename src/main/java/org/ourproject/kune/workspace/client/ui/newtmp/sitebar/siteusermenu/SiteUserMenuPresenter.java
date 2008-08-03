package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;

public class SiteUserMenuPresenter implements SiteUserMenu {

    private SiteUserMenuView view;

    public SiteUserMenuPresenter(final Session session) {
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO userInfoDTO) {
		view.setVisible(true);
		view.setLoggedUserName(userInfoDTO.getShortName());
		view.setUseGroupsIsMember(userInfoDTO.getGroupsIsAdmin(), userInfoDTO.getGroupsIsCollab());
	    }
	});
	session.onUserSignOut(new Slot0() {
	    public void onEvent() {
		view.setVisible(false);
		view.setLoggedUserName("");
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SiteUserMenuView view) {
	this.view = view;
    }

}
