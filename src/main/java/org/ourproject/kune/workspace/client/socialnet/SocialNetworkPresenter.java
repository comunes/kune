package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.workspace.client.workspace.SocialNetworkComponent;

public class SocialNetworkPresenter implements SocialNetworkComponent {

    private SocialNetworkView view;

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
	view.setSocialNetwork(socialNetwork);
    }

    public void init(final SocialNetworkView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

}
