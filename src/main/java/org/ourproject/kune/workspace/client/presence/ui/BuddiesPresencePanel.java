package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.presence.BuddiesPresenceView;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class BuddiesPresencePanel extends StackedDropDownPanel implements BuddiesPresenceView {
    private static final int CONTACT_ONLINE = 0;
    private static final int CONTACT_OFFLINE = 1;
    private static final int CONTACT_BUSY = 2;
    private static final int CONTACT_INVISIBLE = 3;
    private static final int CONTACT_XA = 4;
    private static final int CONTACT_AWAY = 5;
    private static final int CONTACT_MESSAGE = 6;

    XmppIcons statusIcons = XmppIcons.App.getInstance();
    Images img = Images.App.getInstance();

    public BuddiesPresencePanel(final AbstractPresenter presenter) {
	super(presenter, "#CD87DE", "My buddies", "Presence of my buddies", true);
	super.addStackItem("Connected", "Buddies connected", true);
	super.addStackItem("Not connected", "Buddies not connected", true);
	super.addStackItem("test", "Buddies not connected", true);
	super.addBottomLink(img.addGreen(), "Add new buddy", "addbuddy", "Test");
	addRoster("fulano", "Connected", CONTACT_ONLINE);
	addRoster("fulano away", "Connected", CONTACT_AWAY);
	addRoster("fulano busy", "Connected", CONTACT_BUSY);
	addRoster("fulano busy2", "Connected", CONTACT_BUSY);
	addRoster("fulano off", "Not connected", CONTACT_OFFLINE);
	removeRoster("fulano busy2", "Connected");
	super.removeStackItem("test");
    }

    public void setBuddiesPresence() {
	// super.addStackSubItem("Connected",
	// Images.App.getInstance().personDef(), "fulano", "Waiting...",
	// MemberAction.DEFAULT_VISIT_GROUP);
	// super.addStackSubItem("Not connected",
	// Images.App.getInstance().personDef(), "fulano 2", "Away...",
	// MemberAction.DEFAULT_VISIT_GROUP);
    }

    public void addRoster(final String name, final String category, final int status) {
	StackSubItemAction[] actions = {
		new StackSubItemAction(img.chat(), "Start a chat with this person", WorkspaceEvents.GOTO_GROUP),
		StackSubItemAction.DEFAULT_VISIT_GROUP };
	super.addStackSubItem(category, getStatusIcon(status), name, getStatusText(status), actions);
    }

    public void removeRoster(final String name, final String category) {
	super.removeStackSubItem(category, name);
    }

    private AbstractImagePrototype getStatusIcon(final int status) {
	switch (status) {
	case CONTACT_ONLINE:
	    return statusIcons.online();
	case CONTACT_OFFLINE:
	    return statusIcons.offline();
	case CONTACT_BUSY:
	    return statusIcons.busy();
	case CONTACT_INVISIBLE:
	    return statusIcons.invisible();
	case CONTACT_XA:
	    return statusIcons.extendedAway();
	case CONTACT_AWAY:
	    return statusIcons.away();
	case CONTACT_MESSAGE:
	    return statusIcons.message();
	default:
	    throw new IndexOutOfBoundsException("Xmpp status unknown");

	}
    }

    private String getStatusText(final int status) {
	switch (status) {
	case CONTACT_ONLINE:
	    return "Online";
	case CONTACT_OFFLINE:
	    return "Offline";
	case CONTACT_BUSY:
	    return "Busy";
	case CONTACT_INVISIBLE:
	    return "Invisible";
	case CONTACT_XA:
	    return "Extended away";
	case CONTACT_AWAY:
	    return "Away";
	case CONTACT_MESSAGE:
	    return "Message from contact";
	default:
	    throw new IndexOutOfBoundsException("Xmpp status unknown");

	}
    }
}
