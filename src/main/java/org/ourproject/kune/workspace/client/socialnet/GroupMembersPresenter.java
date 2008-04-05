/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
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
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.GroupMembersComponent;

public class GroupMembersPresenter extends AbstractPresenter implements GroupMembersComponent, EntityLiveSearchListener {

    private static final String ADMIN_CATEGORY = Kune.I18N.t("Admins");
    private static final String COLLAB_CATEGORY = Kune.I18N.t("Collaborators");
    private static final String PENDING_CATEGORY = Kune.I18N.t("Pending");
    private GroupMembersView view;

    public void init(final GroupMembersView view) {
        this.view = view;
    }

    public void setGroupMembers(final StateDTO state) {
        if (state.getGroup().getType() == GroupDTO.PERSONAL) {
            hide();
        } else {
            setGroupMembers(state.getGroupMembers(), state.getGroupRights());
        }
    }

    private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
        final AccessListsDTO accessLists = socialNetwork.getAccessLists();

        List<GroupDTO> adminsList = accessLists.getAdmins().getList();
        List<GroupDTO> collabList = accessLists.getEditors().getList();
        List<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

        int numAdmins = adminsList.size();
        int numCollaborators = collabList.size();
        int numPendingCollabs = pendingCollabsList.size();

        boolean userIsAdmin = rights.isAdministrable();
        boolean userIsCollab = !userIsAdmin && rights.isEditable();
        boolean userCanView = rights.isVisible();
        boolean userIsMember = isMember(userIsAdmin, userIsCollab);

        view.setDropDownContentVisible(false);
        view.clear();

        if (userIsAdmin) {
            view.addAddMemberLink();
        }

        if (!userIsMember) {
            view.addJoinLink();
        } else if (userIsAdmin && numAdmins > 1 || userIsCollab) {
            view.addUnjoinLink();
        }

        if (numAdmins == 0 && numCollaborators == 0) {
            view.addComment(Kune.I18N.t("This is an orphaned project, if you are interested "
                    + "please request to join to work on it"));
        }

        if (userCanView) {
            if (rights.isAdministrable()) {
                MemberAction[] adminsActions = {
                        new MemberAction(Kune.I18N.t("Remove this member"), WorkspaceEvents.DEL_MEMBER),
                        new MemberAction(Kune.I18N.t("Change to collaborator"), WorkspaceEvents.SET_ADMIN_AS_COLLAB),
                        MemberAction.GOTO_GROUP_COMMAND };
                MemberAction[] collabActions = {
                        new MemberAction(Kune.I18N.t("Remove this member"), WorkspaceEvents.DEL_MEMBER),
                        new MemberAction(Kune.I18N.t("Change to admin"), WorkspaceEvents.SET_COLLAB_AS_ADMIN),
                        MemberAction.GOTO_GROUP_COMMAND };
                MemberAction[] pendingsActions = {
                        new MemberAction(Kune.I18N.t("Accept this member"), WorkspaceEvents.ACCEPT_JOIN_GROUP),
                        new MemberAction(Kune.I18N.t("Don't accept this member"), WorkspaceEvents.DENY_JOIN_GROUP),
                        MemberAction.GOTO_GROUP_COMMAND };
                MemberAction[] viewerActions = { MemberAction.GOTO_GROUP_COMMAND };
                addMembers(adminsList, collabList, pendingCollabsList, numAdmins, numCollaborators, numPendingCollabs,
                        userIsAdmin, adminsActions, collabActions, pendingsActions, viewerActions);
            } else if (rights.isEditable() || rights.isVisible()) {
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

    public void showAdmins() {
        view.showCategory(ADMIN_CATEGORY);
    }

    public void showCollabs() {
        view.showCategory(COLLAB_CATEGORY);
    }

    public void hide() {
        view.hide();
    }

    public void onJoin() {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_JOIN_GROUP, null);
    }

    public void onSelection(final String groupShortName, final String groupLongName) {
        view.confirmAddCollab(groupShortName, groupLongName);
    }

    public View getView() {
        return view;
    }

    private void addMembers(final List<GroupDTO> adminsList, final List<GroupDTO> collabList,
            final List<GroupDTO> pendingCollabsList, final int numAdmins, final int numCollaborators,
            final int numPendingCollabs, final boolean isAdmin, final MemberAction[] adminsActions,
            final MemberAction[] collabActions, final MemberAction[] pendingsActions, final MemberAction[] viewerActions) {
        if (numAdmins > 0) {
            view.addCategory(ADMIN_CATEGORY, Kune.I18N.t("People that can admin this group"));
            iteraList(ADMIN_CATEGORY, adminsList, adminsActions);
        }
        if (numCollaborators > 0) {
            view.addCategory(COLLAB_CATEGORY, Kune.I18N.t("Other people that collaborate with this group"));
            iteraList(COLLAB_CATEGORY, collabList, collabActions);
        }
        if (isAdmin) {
            if (numPendingCollabs > 0) {
                view.addCategory(PENDING_CATEGORY, Kune.I18N
                        .t("People pending to be accepted in this group by the admins"), GroupMembersView.ICON_ALERT);
                iteraList(PENDING_CATEGORY, pendingCollabsList, pendingsActions);
            }
        }

    }

    private void iteraList(final String categoryName, final List<GroupDTO> groupList, final MemberAction[] actions) {
        final Iterator<GroupDTO> iter = groupList.iterator();
        while (iter.hasNext()) {
            final GroupDTO group = iter.next();
            view.addCategoryMember(categoryName, group.getShortName(), group.getLongName(), actions);
        }
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
        return userIsAdmin || userIsCollab;
    }

    public void addCollab(final String groupShortName) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_COLLAB_MEMBER, groupShortName);
    }

}
