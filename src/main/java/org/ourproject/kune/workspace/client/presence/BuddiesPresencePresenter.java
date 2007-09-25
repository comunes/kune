package org.ourproject.kune.workspace.client.presence;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.workspace.BuddiesPresenceComponent;

public class BuddiesPresencePresenter implements BuddiesPresenceComponent, AbstractPresenter {

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

    public void doAction(final String action, final String param) {
	// TODO Auto-generated method stub
    }

}
