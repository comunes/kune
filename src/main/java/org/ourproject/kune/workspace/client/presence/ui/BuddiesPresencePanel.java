package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.presence.BuddiesPresenceView;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.workspace.ui.MemberAction;

public class BuddiesPresencePanel extends StackedDropDownPanel implements BuddiesPresenceView {

    public BuddiesPresencePanel(final AbstractPresenter presenter) {
	super(presenter, "#CD87DE", "My buddies", "Presence of my buddies", true);
	super.addStackItem("Connected", "Buddies connected", true);
	super.addStackItem("Not connected", "Buddies not connected", true);
	super.addBottomLink(Images.App.getInstance().addGreen(), "Add new buddy", "addbuddy", "Test");
    }

    public void setBuddiesPresence() {
	super.addStackSubItem("Connected", Images.App.getInstance().personDef(), "fulano", "Waiting...",
		MemberAction.DEFAULT_VISIT_GROUP);
	super.addStackSubItem("Not connected", Images.App.getInstance().personDef(), "fulano 2", "Away...",
		MemberAction.DEFAULT_VISIT_GROUP);
    }
}
