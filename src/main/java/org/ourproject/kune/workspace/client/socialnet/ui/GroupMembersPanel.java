package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class GroupMembersPanel extends StackedDropDownPanel implements GroupMembersView {

    private final Images img = Images.App.getInstance();

    public GroupMembersPanel(final AbstractPresenter presenter) {
	super(presenter, "#00D4AA", "Group members", "People and groups collaborating in this group", true);
    }

    public void addJoinLink() {
	// FIXME: add new event
	// i18n
	super.addBottomLink(img.addGreen(), "Request to join", "fixme", WorkspaceEvents.REQ_JOIN_GROUP);
    }

    public void addAddMemberLink() {
	// FIXME: add new event
	// i18n
	super.addBottomLink(img.addGreen(), "Add member", "fixme", WorkspaceEvents.ADD_ADMIN_MEMBER);
    }

    public void clear() {
	super.clear();
    }

    public void addCategory(final String name, final String title) {
	super.addStackItem(name, title, true);
    }

    public void addCategory(final String name, final String title, final String iconType) {
	super.addStackItem(name, title, getIcon(iconType), StackedDropDownPanel.ICON_HORIZ_ALIGN_RIGHT, true);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
	    final MemberAction[] memberActions) {
	StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
	for (int i = 0; i < memberActions.length; i++) {
	    subItems[i] = new StackSubItemAction(getIconFronEvent(memberActions[i].getAction()), memberActions[i]
		    .getText(), memberActions[i].getAction());
	}

	super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    private AbstractImagePrototype getIcon(final String event) {
	if (event == GroupMembersView.ICON_ALERT) {
	    return img.alert();
	}
	throw new IndexOutOfBoundsException("Icon unknown in GroupMemebersPanel");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
	if (event == WorkspaceEvents.ACCEPT_JOIN_GROUP) {
	    return img.accept();
	}
	if (event == WorkspaceEvents.DENY_JOIN_GROUP) {
	    return img.cancel();
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
	throw new IndexOutOfBoundsException("Event unknown in GroupMembersPanel");
    }

    public void show() {
	this.setVisible(true);
    }

    public void hide() {
	this.setVisible(false);
    }

}
