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
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.workspace.ParticipationComponent;

public class ParticipationPresenter extends AbstractPresenter implements ParticipationComponent {
    private static final String ADMIN_SUBTITLE = Kune.I18N.t("admin in:");

    private final static MemberAction GOTO_GROUP_COMMAND = new MemberAction(Kune.I18N.t("Visit this group homepage"),
            WorkspaceEvents.GOTO);

    private ParticipationView view;

    public void init(final ParticipationView view) {
        this.view = view;
    }

    public void doAction(final String action, final String group) {
        DefaultDispatcher.getInstance().fire(action, group, this);
    }

    public View getView() {
        return view;
    }

    public void setParticipation(final StateDTO state) {
        ParticipationDataDTO participation = state.getParticipation();
        AccessRightsDTO rights = state.getGroupRights();
        view.setDropDownContentVisible(false);
        view.clear();
        MemberAction[] adminsActions = {
                new MemberAction(Kune.I18N.t("Don't participate more in this group"), WorkspaceEvents.UNJOIN_GROUP),
                GOTO_GROUP_COMMAND };
        MemberAction[] collabActions = adminsActions;
        MemberAction[] viewerActions = { GOTO_GROUP_COMMAND };
        List groupsIsAdmin = participation.getGroupsIsAdmin();
        List groupsIsCollab = participation.getGroupsIsCollab();
        boolean userIsAdmin = rights.isAdministrable();
        boolean userIsCollab = !userIsAdmin && rights.isEditable();
        boolean userIsMember = isMember(userIsAdmin, userIsCollab);
        int numAdmins = groupsIsAdmin.size();
        int numCollaborators = groupsIsCollab.size();
        if (numAdmins > 0 || numCollaborators > 0) {
            addParticipants(groupsIsAdmin, groupsIsCollab, numAdmins, numCollaborators, userIsAdmin, userIsMember,
                    adminsActions, collabActions, viewerActions);
            view.setDropDownContentVisible(true);
            view.show();
        } else {
            hide();
        }

    }

    private void hide() {
        view.hide();
    }

    private void addParticipants(final List groupsIsAdmin, final List groupsIsCollab, final int numAdmins,
            final int numCollaborators, final boolean userIsAdmin, boolean userIsMember,
            final MemberAction[] adminsActions, final MemberAction[] collabActions, final MemberAction[] viewerActions) {
        MemberAction[] actions;
        String collabTitle;

        if (!userIsMember) {
            actions = viewerActions;
        } else {
            if (userIsAdmin) {
                actions = adminsActions;
            } else {
                actions = collabActions;
            }
        }
        if (numAdmins > 0) {
            view.addCategory(ADMIN_SUBTITLE, Kune.I18N.tWithNT("Administrate these groups", "talking about a person"));
            iteraList(ADMIN_SUBTITLE, groupsIsAdmin, actions);
            collabTitle = Kune.I18N.t("and as collaborator in:");
        } else {
            collabTitle = Kune.I18N.t("collaborator in:");
        }
        if (numCollaborators > 0) {
            view.addCategory(collabTitle, Kune.I18N.t("Collaborate in these groups"));
            iteraList(collabTitle, groupsIsCollab, actions);
        }

    }

    private void iteraList(final String categoryName, final List groupList, final MemberAction[] actions) {
        final Iterator iter = groupList.iterator();
        while (iter.hasNext()) {
            final LinkDTO group = (LinkDTO) iter.next();
            view.addCategoryMember(categoryName, group.getShortName(), group.getLongName(), actions);
        }
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
        return userIsAdmin || userIsCollab;
    }
}
