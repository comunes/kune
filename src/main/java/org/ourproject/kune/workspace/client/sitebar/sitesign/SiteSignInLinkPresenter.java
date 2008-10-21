package org.ourproject.kune.workspace.client.sitebar.sitesign;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public class SiteSignInLinkPresenter implements SiteSignInLink {

    private SiteSignInLinkView view;

    public SiteSignInLinkPresenter(final Session session) {
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO user) {
                view.setVisible(false);
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
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
