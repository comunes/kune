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
    //
    // public void addAdminsItems(final int numAdmins, final List groupList,
    // final AccessRightsDTO rights) {
    // // i18n
    // MemberAction[] adminCommands = { new MemberAction(img.del(), "Remove this
    // member", WorkspaceEvents.DEL_MEMBER),
    // new MemberAction(img.arrowDownGreen(), "Change to collaborator",
    // WorkspaceEvents.SET_ADMIN_AS_COLLAB) };
    // MemberAction[] viewerCommands = { gotoGroupCommand() };
    // stack.add(createGroupListVP(groupList, rights, adminCommands, null,
    // viewerCommands), setLabelAndCount("Admins",
    // numAdmins));
    // }
    //
    // public void addCollabItems(final int numCollaborators, final List
    // groupList, final AccessRightsDTO rights) {
    // // i18n
    // MemberAction[] adminCommands = { new MemberAction(img.del(), "Remove this
    // member", WorkspaceEvents.DEL_MEMBER),
    // new MemberAction(img.arrowUpGreen(), "Change to admin",
    // WorkspaceEvents.SET_COLLAB_AS_ADMIN) };
    // MemberAction[] viewerCommands = { gotoGroupCommand() };
    //
    // stack.add(createGroupListVP(groupList, rights, adminCommands, null,
    // viewerCommands), setLabelAndCount(
    // "Collaborators", numCollaborators));
    // }
    //
    // public void addPendingCollabsItems(final int numPendingCollabs, final
    // List groupList, final AccessRightsDTO rights) {
    // // i18n
    // MemberAction[] adminCommands = {
    // new MemberAction(img.accept(), "Accept this member",
    // WorkspaceEvents.ACCEPT_JOIN_GROUP),
    // new MemberAction(img.cancel(), "Don't accept this member",
    // WorkspaceEvents.DENY_JOIN_GROUP) };
    // MemberAction[] viewerCommands = { gotoGroupCommand() };
    // stack.add(createGroupListVP(groupList, rights, adminCommands, null,
    // viewerCommands), setLabelAndCount(
    // "Pending", numPendingCollabs)
    // + img.alert().getHTML(), true);
    // }
    //
    // public void clearGroups() {
    // stack.clear();
    // }
    //
    // public void setDropDownContentVisible(final boolean visible) {
    // setContentVisible(visible);
    // }
    //
    // private String setLabelAndCount(final String label, final int count) {
    // return label + " (" + count + ")";
    // }
    //
    // private MemberAction gotoGroupCommand() {
    // return new MemberAction(img.groupHome(), "Visit this member homepage",
    // WorkspaceEvents.GOTO_GROUP);
    // }
    //
    // private AbstractImagePrototype setGroupIcon(final GroupDTO group) {
    // // Until user upload icons/images ...
    // AbstractImagePrototype icon;
    // if (group.getType() == GroupDTO.TYPE_PERSONAL) {
    // icon = img.personDef();
    // } else {
    // icon = img.groupDefIcon();
    // }
    // return icon;
    // }
    //
    // private VerticalPanel createGroupListVP(final List groupList, final
    // AccessRightsDTO rights,
    // final MemberAction[] adminCommands, final MemberAction[] editorCommands,
    // final MemberAction[] viewerCommands) {
    // VerticalPanel groupVP = new VerticalPanel();
    // final Iterator iter = groupList.iterator();
    // while (iter.hasNext()) {
    // final GroupDTO group = (GroupDTO) iter.next();
    // AbstractImagePrototype icon = setGroupIcon(group);
    // MemberMenu groupMenu = new MemberMenu(icon, group.getShortName());
    // if (rights.isAdministrable() && adminCommands != null) {
    // for (int i = 0; i < adminCommands.length; i++) {
    // groupMenu.addCmd(adminCommands[i], group);
    // }
    // }
    // if (rights.isEditable() && editorCommands != null) {
    // for (int i = 0; i < editorCommands.length; i++) {
    // groupMenu.addCmd(editorCommands[i], group);
    // }
    // }
    // if (rights.isVisible() && viewerCommands != null) {
    // for (int i = 0; i < viewerCommands.length; i++) {
    // groupMenu.addCmd(viewerCommands[i], group);
    // }
    // }
    // groupVP.add(groupMenu);
    // }
    // return groupVP;
    // }

}
