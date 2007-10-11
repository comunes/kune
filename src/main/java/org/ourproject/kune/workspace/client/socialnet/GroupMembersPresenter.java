/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.GroupMembersComponent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GroupMembersPresenter implements GroupMembersComponent, AbstractPresenter {

    private GroupMembersView view;

    public void init(final GroupMembersView view) {
	this.view = view;
    }

    public void getGroupMembers(final String user, final GroupDTO group, final AccessRightsDTO accessRightsDTO) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();

	server.getGroupMembers(user, group.getShortName(), new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		Site.hideProgress();
	    }

	    public void onSuccess(final Object result) {
		SocialNetworkDTO sn = (SocialNetworkDTO) result;
		if (group.getType() != GroupDTO.PERSONAL) {
		    setSocialNetworkOfGroup(sn, accessRightsDTO);

		} else {
		    hide();
		}
		Site.hideProgress();
	    }
	});
    }

    public void setSocialNetworkOfGroup(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
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
			MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] collabActions = { new MemberAction("Remove this member", WorkspaceEvents.DEL_MEMBER),
			new MemberAction("Change to admin", WorkspaceEvents.SET_COLLAB_AS_ADMIN),
			MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] pendingsActions = {
			new MemberAction("Accept this member", WorkspaceEvents.ACCEPT_JOIN_GROUP),
			new MemberAction("Don't accept this member", WorkspaceEvents.DENY_JOIN_GROUP),
			MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] viewerActions = { MemberAction.GOTO_GROUP_COMMAND };
		addMembers(adminsList, collabList, pendingCollabsList, numAdmins, numCollaborators, numPendingCollabs,
			userIsAdmin, adminsActions, collabActions, pendingsActions, viewerActions);
	    } else if (rights.isEditable() || rights.isVisible) {
		MemberAction[] adminsActions = { MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] collabActions = { MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] pendingsActions = { MemberAction.GOTO_GROUP_COMMAND };
		MemberAction[] viewerActions = { MemberAction.GOTO_GROUP_COMMAND };
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
			GroupMembersView.ICON_ALERT);
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

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

}
