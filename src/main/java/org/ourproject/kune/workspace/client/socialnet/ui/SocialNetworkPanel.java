package org.ourproject.kune.workspace.client.socialnet.ui;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkPresenter;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SocialNetworkPanel extends DropDownPanel implements SocialNetworkView {

    private final VerticalPanel adminsVP;
    private final VerticalPanel collaboratorsVP;
    private final VerticalPanel pendingCollaboratorsVP;
    private final IconHyperlink joinLink;
    private final IconHyperlink addMemberLink;
    private final SocialNetworkPresenter presenter;
    private final StackPanel stack;

    public SocialNetworkPanel(final SocialNetworkPresenter presenter) {
	this.presenter = presenter;
	final Images img = Images.App.getInstance();
	final VerticalPanel vp = new VerticalPanel();
	stack = new StackPanel();
	adminsVP = new VerticalPanel();
	collaboratorsVP = new VerticalPanel();
	pendingCollaboratorsVP = new VerticalPanel();
	// i18n
	joinLink = new IconHyperlink(img.addGreen(), "Request to join", "fixme");
	addMemberLink = new IconHyperlink(img.addGreen(), "Add member", "fixme");

	// Layout
	stack.add(adminsVP, "");
	stack.add(collaboratorsVP, "");
	stack.add(pendingCollaboratorsVP, "");
	vp.add(stack);
	vp.add(joinLink);
	vp.add(addMemberLink);
	setContent(vp);
	setColor("#00D4AA");

	// Set properties
	setContentVisible(true); // DropDown
	// i18n
	setHeaderText("Group members");
	setHeaderTitle("People collaborating in this group");
	addStyleName("kune-SocialNet");
	addStyleName("kune-Margin-Medium-t");
	stack.setStyleName("kune-SocialNet");

	vp.setCellHorizontalAlignment(joinLink, HorizontalPanel.ALIGN_CENTER);
	vp.setCellHorizontalAlignment(addMemberLink, HorizontalPanel.ALIGN_CENTER);
	joinLink.addStyleName("kune-SocialNetJoinLink");
	addMemberLink.addStyleName("kune-SocialNetAddMemberLink");
	setVisibleJoinLink(false);
	setVisibleAddMemberLink(false);

	joinLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		presenter.onJoin();
	    }
	});

	addMemberLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		Window.alert("In develop!");
		presenter.onAddMember();
	    }
	});
    }

    public void setVisibleJoinLink(final boolean visible) {
	joinLink.setVisible(visible);

    }

    public void setVisibleAddMemberLink(final boolean visible) {
	addMemberLink.setVisible(visible);
    }

    private VerticalPanel createAdminListVP(final List groupList, final boolean userIsAdmin) {
	final Images img = Images.App.getInstance();
	VerticalPanel groupVP = new VerticalPanel();
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    AbstractImagePrototype icon = setGroupIcon(img, group);

	    GroupMemberMenu groupMenu = new GroupMemberMenu(icon, group.getShortName());
	    if (userIsAdmin) {
		groupMenu.addCmd(img.del(), "Remove this member", new Command() {
		    public void execute() {
			presenter.onDelGroup(group);
		    }
		});
		groupMenu.addCmd(img.arrowDownGreen(), "Change to collaborator", new Command() {
		    public void execute() {
			presenter.onSetAdminAsCollab(group);
		    }
		});
	    }
	    addGotoGroupLink(img, group, groupMenu);
	    groupVP.add(groupMenu);
	}
	return groupVP;
    }

    private VerticalPanel createCollabListVP(final List groupList, final boolean userIsAdmin) {
	final Images img = Images.App.getInstance();
	VerticalPanel groupVP = new VerticalPanel();
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    AbstractImagePrototype icon = setGroupIcon(img, group);
	    GroupMemberMenu groupMenu = new GroupMemberMenu(icon, group.getShortName());
	    if (userIsAdmin) {
		groupMenu.addCmd(img.del(), "Remove this member", new Command() {
		    public void execute() {
			presenter.onDelGroup(group);
		    }
		});
		groupMenu.addCmd(img.arrowUpGreen(), "Change to admin", new Command() {
		    public void execute() {
			presenter.onSetCollabAsAdmin(group);
		    }
		});
	    }
	    addGotoGroupLink(img, group, groupMenu);
	    groupVP.add(groupMenu);
	}
	return groupVP;
    }

    private VerticalPanel createPendingListVP(final List groupList, final boolean userIsAdmin) {
	final Images img = Images.App.getInstance();
	VerticalPanel groupVP = new VerticalPanel();
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    AbstractImagePrototype icon = setGroupIcon(img, group);
	    GroupMemberMenu groupMenu = new GroupMemberMenu(icon, group.getShortName());
	    if (userIsAdmin) {
		groupMenu.addCmd(img.accept(), "Accept this member", new Command() {
		    public void execute() {
			presenter.onAcceptMember(group);
		    }
		});
		groupMenu.addCmd(img.cancel(), "Don't accept this member", new Command() {
		    public void execute() {
			presenter.onDenyMember(group);
		    }
		});
	    }

	    addGotoGroupLink(img, group, groupMenu);
	    groupVP.add(groupMenu);
	}
	return groupVP;
    }

    private AbstractImagePrototype setGroupIcon(final Images img, final GroupDTO group) {
	// Until user upload icons/images ...
	AbstractImagePrototype icon;
	if (group.getType() == GroupDTO.TYPE_PERSONAL) {
	    icon = img.personDef();
	} else {
	    icon = img.groupDefIcon();
	}
	return icon;
    }

    private void addGotoGroupLink(final Images img, final GroupDTO group, final GroupMemberMenu groupMenu) {
	groupMenu.addCmd(img.groupHome(), "Visit " + group.getShortName() + " homepage", new Command() {
	    public void execute() {
		presenter.onGoToGroup(group);
	    }
	});
    }

    public void addAdminsItems(final int numAdmins, final List groupList, final boolean userIsAdmin) {
	// i18n
	stack.add(createAdminListVP(groupList, userIsAdmin), setLabelAndCount("Admins", numAdmins));
    }

    public void addCollabItems(final int numCollaborators, final List groupList, final boolean userIsAdmin) {
	// i18n
	stack.add(createCollabListVP(groupList, userIsAdmin), setLabelAndCount("Collaborators", numCollaborators));
    }

    public void addPendingCollabsItems(final int numPendingCollabs, final List groupList, final boolean userIsAdmin) {
	// i18n
	stack.add(createPendingListVP(groupList, userIsAdmin), setLabelAndCount("Pending", numPendingCollabs)
		+ Images.App.getInstance().alert().getHTML(), true);
    }

    public void clearGroups() {
	stack.clear();
    }

    private String setLabelAndCount(final String label, final int count) {
	return label + " (" + count + ")";
    }

    public void setDropDownContentVisible(final boolean visible) {
	setContentVisible(visible);
    }
}
