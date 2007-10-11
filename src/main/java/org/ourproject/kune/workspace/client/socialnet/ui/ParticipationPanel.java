package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.socialnet.ParticipationView;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.workspace.ui.StackSubItemAction;
import org.ourproject.kune.workspace.client.workspace.ui.StackedDropDownPanel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ParticipationPanel extends StackedDropDownPanel implements ParticipationView {

    private final Images img = Images.App.getInstance();

    public ParticipationPanel(final AbstractPresenter presenter) {
	super(presenter, "#00D4AA", "Participates", "Other groups in which participates", true);
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
	throw new IndexOutOfBoundsException("Icon unknown in ParticipationPanelk");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
	if (event == WorkspaceEvents.UNJOIN_GROUP) {
	    return img.del();
	}
	if (event == WorkspaceEvents.GOTO_GROUP) {
	    return img.groupHome();
	}
	throw new IndexOutOfBoundsException("Event unknown in ParticipationPanel");
    }

    public void show() {
	this.setVisible(true);
    }

    public void hide() {
	this.setVisible(false);
    }

}
