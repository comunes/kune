package org.ourproject.kune.workspace.client.presence;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.workspace.BuddiesPresenceComponent;

public class BuddiesPresencePresenter implements BuddiesPresenceComponent {

    private BuddiesPresenceView view;

    public void setBuddiesPresence() {
	view.setBuddiesPresence();
    }

    public View getView() {
	return view;
    }

    public void init(final BuddiesPresenceView view) {
	this.view = view;
    }

}
