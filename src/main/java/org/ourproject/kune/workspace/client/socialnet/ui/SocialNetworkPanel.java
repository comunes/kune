package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class SocialNetworkPanel extends StackedDropDownPanel implements SocialNetworkView {
    private final Images img = Images.App.getInstance();

    public SocialNetworkPanel(final AbstractPresenter presenter) {
	super(presenter, "#00D4AA", "Group members", "People and groups collaborating in this group", true);
    }

    public void addJoinLink() {
	// FIXME: add new event
	// i18n
	super.addBottomLink(img.addGreen(), "Request to join", "fixme", WorkspaceEvents.GOTO_GROUP);
    }

    public void addAddMemberLink() {
	// FIXME: add new event
	// i18n
	super.addBottomLink(img.addGreen(), "Add member", "fixme", WorkspaceEvents.GOTO_GROUP);
    }

    public void clear() {
	super.clear();
    }

    public void addCategory(final String name, final String title) {
	super.addStackItem(name, title, true);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
	    final MemberAction[] memberActions) {
	StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
	for (int i = 0; i < memberActions.length; i++) {
	    subItems[i] = new StackSubItemAction(getIcon(memberActions[i].getAction()), memberActions[i].getText(),
		    memberActions[i].getAction());
	}

	super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    private AbstractImagePrototype getIcon(final String event) {
	if (event == WorkspaceEvents.ACCEPT_JOIN_GROUP) {
	    return img.accept();
	}
	if (event == WorkspaceEvents.DEL_MEMBER) {
	    return img.del();
	}
	if (event == WorkspaceEvents.GOTO_GROUP) {
	    return img.groupHome();
	}
	if (event == WorkspaceEvents.SET_ADMIN_AS_COLLAB) {
	    return img.arrowDownGreen();
	}
	if (event == WorkspaceEvents.SET_COLLAB_AS_ADMIN) {
	    return img.arrowUpGreen();
	}
	throw new IndexOutOfBoundsException("Event unknown in Socialnetwork");
    }

}
