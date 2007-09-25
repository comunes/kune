package org.ourproject.kune.workspace.client.socialnet.ui;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkPresenter;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.ScrollPanel;
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
    private final Images img = Images.App.getInstance();

    public SocialNetworkPanel(final SocialNetworkPresenter presenter) {
	this.presenter = presenter;
	final VerticalPanel vp = new VerticalPanel();
	stack = new StackPanel();
	ScrollPanel adminsSP = new ScrollPanel();
	adminsVP = new VerticalPanel();
	ScrollPanel collaboratorsSP = new ScrollPanel();
	collaboratorsVP = new VerticalPanel();
	ScrollPanel pendingCollaboratorsSP = new ScrollPanel();
	pendingCollaboratorsVP = new VerticalPanel();
	// i18n
	joinLink = new IconHyperlink(img.addGreen(), "Request to join", "fixme");
	addMemberLink = new IconHyperlink(img.addGreen(), "Add member", "fixme");

	// Layout
	adminsSP.add(adminsVP);
	collaboratorsSP.add(collaboratorsVP);
	pendingCollaboratorsSP.add(pendingCollaboratorsVP);
	stack.add(adminsSP, "");
	stack.add(collaboratorsSP, "");
	stack.add(pendingCollaboratorsSP, "");
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
	stack.setHeight("5em");
	adminsSP.setHeight("2em");
	collaboratorsSP.setHeight("2em");
	pendingCollaboratorsSP.setHeight("2em");

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

    public void addAdminsItems(final int numAdmins, final List groupList, final AccessRightsDTO rights) {
	// i18n
	MemberAction[] adminCommands = { new MemberAction(img.del(), "Remove this member", WorkspaceEvents.DEL_MEMBER),
		new MemberAction(img.arrowDownGreen(), "Change to collaborator", WorkspaceEvents.SET_ADMIN_AS_COLLAB) };
	MemberAction[] viewerCommands = { gotoGroupCommand() };
	stack.add(createGroupListVP(groupList, rights, adminCommands, null, viewerCommands), setLabelAndCount("Admins",
		numAdmins));
    }

    public void addCollabItems(final int numCollaborators, final List groupList, final AccessRightsDTO rights) {
	// i18n
	MemberAction[] adminCommands = { new MemberAction(img.del(), "Remove this member", WorkspaceEvents.DEL_MEMBER),
		new MemberAction(img.arrowUpGreen(), "Change to admin", WorkspaceEvents.SET_COLLAB_AS_ADMIN) };
	MemberAction[] viewerCommands = { gotoGroupCommand() };

	stack.add(createGroupListVP(groupList, rights, adminCommands, null, viewerCommands), setLabelAndCount(
		"Collaborators", numCollaborators));
    }

    public void addPendingCollabsItems(final int numPendingCollabs, final List groupList, final AccessRightsDTO rights) {
	// i18n
	MemberAction[] adminCommands = {
		new MemberAction(img.accept(), "Accept this member", WorkspaceEvents.ACCEPT_JOIN_GROUP),
		new MemberAction(img.cancel(), "Don't accept this member", WorkspaceEvents.DENY_JOIN_GROUP) };
	MemberAction[] viewerCommands = { gotoGroupCommand() };
	stack.add(createGroupListVP(groupList, rights, adminCommands, null, viewerCommands), setLabelAndCount(
		"Pending", numPendingCollabs)
		+ img.alert().getHTML(), true);
    }

    public void clearGroups() {
	stack.clear();
    }

    public void setDropDownContentVisible(final boolean visible) {
	setContentVisible(visible);
    }

    private String setLabelAndCount(final String label, final int count) {
	return label + " (" + count + ")";
    }

    private MemberAction gotoGroupCommand() {
	return new MemberAction(img.groupHome(), "Visit this member homepage", WorkspaceEvents.GOTO_GROUP);
    }

    private AbstractImagePrototype setGroupIcon(final GroupDTO group) {
	// Until user upload icons/images ...
	AbstractImagePrototype icon;
	if (group.getType() == GroupDTO.TYPE_PERSONAL) {
	    icon = img.personDef();
	} else {
	    icon = img.groupDefIcon();
	}
	return icon;
    }

    private VerticalPanel createGroupListVP(final List groupList, final AccessRightsDTO rights,
	    final MemberAction[] adminCommands, final MemberAction[] editorCommands, final MemberAction[] viewerCommands) {
	VerticalPanel groupVP = new VerticalPanel();
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    AbstractImagePrototype icon = setGroupIcon(group);
	    MemberMenu groupMenu = new MemberMenu(icon, group.getShortName());
	    if (rights.isAdministrable() && adminCommands != null) {
		for (int i = 0; i < adminCommands.length; i++) {
		    groupMenu.addCmd(adminCommands[i], group);
		}
	    }
	    if (rights.isEditable() && editorCommands != null) {
		for (int i = 0; i < editorCommands.length; i++) {
		    groupMenu.addCmd(editorCommands[i], group);
		}
	    }
	    if (rights.isVisible() && viewerCommands != null) {
		for (int i = 0; i < viewerCommands.length; i++) {
		    groupMenu.addCmd(viewerCommands[i], group);
		}
	    }
	    groupVP.add(groupMenu);
	}
	return groupVP;
    }

    private class MemberAction {
	AbstractImagePrototype icon;
	String text;
	String action;

	public MemberAction(final AbstractImagePrototype icon, final String text, final String action) {
	    this.icon = icon;
	    this.text = text;
	    this.action = action;
	}

	public AbstractImagePrototype getIcon() {
	    return icon;
	}

	public String getText() {
	    return text;
	}

	public String getAction() {
	    return action;
	}
    }

    class MemberMenu extends MenuBar {
	private final MenuBar commands;

	public MemberMenu(final AbstractImagePrototype image, final String text) {
	    super(false);
	    String label = image.getHTML() + text;
	    commands = new MenuBar(true);
	    addItem(label, true, commands);
	    setAutoOpen(false);
	    commands.setAutoOpen(true);
	    setStyleName("kune-GroupMemberLabel");
	    commands.setStyleName("kune-GroupMemberCommands");
	}

	public void addCmd(final MemberAction memberCommand, final GroupDTO group) {
	    String itemHtml = "";
	    AbstractImagePrototype icon = memberCommand.getIcon();
	    if (icon != null) {
		itemHtml = icon.getHTML();
	    }
	    itemHtml += memberCommand.getText();
	    commands.addItem(itemHtml, true, createCommand(group, memberCommand.getAction()));
	}

	private Command createCommand(final GroupDTO group, final String action) {
	    return new Command() {
		public void execute() {
		    presenter.doAction(group, action);
		}
	    };
	}
    }
}
