package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.SocialNetworkComponent;

public class SocialNetworkPresenter implements SocialNetworkComponent {

    private SocialNetworkView view;

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
	view.setDropDownContentVisible(false);
	view.clearGroups();

	view.setVisibleAddMemberLink(userIsAdmin);
	view.setVisibleJoinLink(!isMember(userIsAdmin, userIsCollab));

	if (userCanView) {
	    if (numAdmins > 0) {
		view.addAdminsItems(numAdmins, adminsList, userIsAdmin);
	    }
	    if (numCollaborators > 0) {
		view.addCollabItems(numCollaborators, collabList, userIsAdmin);
	    }
	    if (numPendingCollabs > 0) {
		view.addPendingCollabsItems(numPendingCollabs, pendingCollabsList, userIsAdmin);
	    }
	}
	view.setDropDownContentVisible(true);
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

    public void init(final SocialNetworkView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void onJoin() {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_JOIN_GROUP, null, null);
    }

    public void onDelGroup(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.DEL_MEMBER, group, this);
    }

    public void onGoToGroup(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.GOTO_GROUP, group, this);
    }

    public void onDenyMember(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.DENY_JOIN_GROUP, group, this);
    }

    public void onAcceptMember(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.ACCEPT_JOIN_GROUP, group, this);
    }

    public void onAddMember() {
	// TODO Auto-generated method stub
    }

}
