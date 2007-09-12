package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.workspace.client.presence.BuddiesPresencePresenter;
import org.ourproject.kune.workspace.client.presence.BuddiesPresenceView;

import com.google.gwt.user.client.ui.Label;

public class BuddiesPresencePanel extends DropDownPanel implements BuddiesPresenceView {

    public BuddiesPresencePanel(final BuddiesPresencePresenter presenter) {
	setHeaderText("Buddies");
	setContentVisible(true);
    }

    public void setBuddiesPresence() {
	setContent(new Label("Here the presence of Buddies"));
    }

}
