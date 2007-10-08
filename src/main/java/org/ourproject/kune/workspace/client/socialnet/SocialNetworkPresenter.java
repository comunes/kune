package org.ourproject.kune.workspace.client.socialnet;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.ui.MemberAction;
import org.ourproject.kune.workspace.client.workspace.SocialNetworkComponent;

public class SocialNetworkPresenter implements SocialNetworkComponent, AbstractPresenter {

    private SocialNetworkView view;

    public void init(final SocialNetworkView view) {
	this.view = view;
    }

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
	final AccessListsDTO accessLists = socialNetwork.getAccessLists();

	List adminsList = accessLists.getAdmins().getList();
	List collabList = accessLists.getEditors().getList();
	List pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

	int numAdmins = adminsList.size();
	int numCollaborators = collabList.size();
	int numPendingCollabs = pendingCollabsList.size();

	boolean userIsAdmin = rights.isAdministrable();
	boolean userIsCollab = rights.isEditable();
	boolean userCanView = rights.isVisible();
	boolean userIsMember = isMember(userIsAdmin, userIsCollab);

	view.setDropDownContentVisible(false);
	view.clear();

	if (userIsAdmin) {
	    view.addAddMemberLink();
	}

	if (!userIsMember) {
	    view.addJoinLink();
	}

	if (userCanView) {
	    if (rights.isAdministrable()) {
		MemberAction[] adminsActions = { new MemberAction("Remove this member", WorkspaceEvents.DEL_MEMBER),
			new MemberAction("Change to collaborator", WorkspaceEvents.SET_ADMIN_AS_COLLAB),
			gotoGroupCommand() };
		MemberAction[] collabActions = { new MemberAction("Remove this member", WorkspaceEvents.DEL_MEMBER),
			new MemberAction("Change to admin", WorkspaceEvents.SET_COLLAB_AS_ADMIN), gotoGroupCommand() };
		MemberAction[] pendingsActions = {
			new MemberAction("Accept this member", WorkspaceEvents.ACCEPT_JOIN_GROUP),
			new MemberAction("Don't accept this member", WorkspaceEvents.DENY_JOIN_GROUP),
			gotoGroupCommand() };
		MemberAction[] viewerActions = { gotoGroupCommand() };
		addMembers(adminsList, collabList, pendingCollabsList, numAdmins, numCollaborators, numPendingCollabs,
			userIsAdmin, adminsActions, collabActions, pendingsActions, viewerActions);
	    } else if (rights.isEditable() || rights.isVisible) {
		MemberAction[] adminsActions = { gotoGroupCommand() };
		MemberAction[] collabActions = { gotoGroupCommand() };
		MemberAction[] pendingsActions = { gotoGroupCommand() };
		MemberAction[] viewerActions = { gotoGroupCommand() };
		addMembers(adminsList, collabList, pendingCollabsList, numAdmins, numCollaborators, numPendingCollabs,
			userIsAdmin, adminsActions, collabActions, pendingsActions, viewerActions);
	    }
	}
	view.setDropDownContentVisible(true);
	view.show();
    }

    public void hide() {
	view.hide();
    }

    public void onJoin() {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_JOIN_GROUP, null, null);
    }

    public void onAddAdmin(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_ADMIN_MEMBER, group, this);
    }

    public void onAddCollab(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_COLLAB_MEMBER, group, this);
    }

    public void onAddViewer(final GroupDTO group) {
    }

    public void onAddMember() {
	// TODO Auto-generated method stub
    }

    public void doAction(final String action, final String group) {
	DefaultDispatcher.getInstance().fire(action, group, this);
    }

    public View getView() {
	return view;
    }

    private void addMembers(final List adminsList, final List collabList, final List pendingCollabsList,
	    final int numAdmins, final int numCollaborators, final int numPendingCollabs, final boolean isAdmin,
	    final MemberAction[] adminsActions, final MemberAction[] collabActions,
	    final MemberAction[] pendingsActions, final MemberAction[] viewerActions) {
	if (numAdmins > 0) {
	    // i18n
	    view.addCategory("Admins", "People that can admin this group");
	    iteraList("Admins", adminsList, adminsActions);
	}
	if (numCollaborators > 0) {
	    view.addCategory("Collaborators", "Other people that collaborate with this group");
	    iteraList("Collaborators", collabList, collabActions);
	}
	if (isAdmin) {
	    if (numPendingCollabs > 0) {
		view.addCategory("Pending", "People pending to be accepted in this group by the admins",
			SocialNetworkView.ICON_ALERT);
		iteraList("Pending", pendingCollabsList, pendingsActions);
	    }
	}

    }

    private void iteraList(final String categoryName, final List groupList, final MemberAction[] actions) {
	final Iterator iter = groupList.iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    view.addCategoryMember(categoryName, group.getShortName(), group.getLongName(), actions);
	}
    }

    private MemberAction gotoGroupCommand() {
	return new MemberAction("Visit this member homepage", WorkspaceEvents.GOTO_GROUP);
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

}
