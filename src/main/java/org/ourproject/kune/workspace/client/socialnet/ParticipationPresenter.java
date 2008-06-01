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
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.ParticipationComponent;

public class ParticipationPresenter extends AbstractPresenter implements ParticipationComponent {

    private ParticipationView view;

    private final I18nTranslationService i18n;

    private final String admin_subtitle;

    private final MemberAction goto_group_command;

    public ParticipationPresenter(final I18nTranslationService i18n) {
        this.i18n = i18n;
        admin_subtitle = i18n.t("admin in:");
        goto_group_command = new MemberAction(i18n.t("Visit this group homepage"), PlatformEvents.GOTO);
    }

    public View getView() {
        return view;
    }

    public void init(final ParticipationView view) {
        this.view = view;
    }

    public void setParticipation(final StateDTO state) {
        ParticipationDataDTO participation = state.getParticipation();
        AccessRightsDTO rights = state.getGroupRights();
        view.setDropDownContentVisible(false);
        view.clear();
        MemberAction[] adminsActions = {
                new MemberAction(i18n.t("Don't participate more in this group"), WorkspaceEvents.UNJOIN_GROUP),
                goto_group_command };
        MemberAction[] collabActions = adminsActions;
        MemberAction[] viewerActions = { goto_group_command };
        List<LinkDTO> groupsIsAdmin = participation.getGroupsIsAdmin();
        List<LinkDTO> groupsIsCollab = participation.getGroupsIsCollab();
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

    private void addParticipants(final List<LinkDTO> groupsIsAdmin, final List<LinkDTO> groupsIsCollab,
            final int numAdmins, final int numCollaborators, final boolean userIsAdmin, boolean userIsMember,
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
            view.addCategory(admin_subtitle, i18n.tWithNT("Administrate these groups", "talking about a person"));
            iteraList(admin_subtitle, groupsIsAdmin, actions);
            collabTitle = i18n.t("and as collaborator in:");
        } else {
            collabTitle = i18n.t("collaborator in:");
        }
        if (numCollaborators > 0) {
            view.addCategory(collabTitle, i18n.t("Collaborate in these groups"));
            iteraList(collabTitle, groupsIsCollab, actions);
        }

    }

    private void hide() {
        view.hide();
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
        return userIsAdmin || userIsCollab;
    }

    private void iteraList(final String categoryName, final List<LinkDTO> groupList, final MemberAction[] actions) {
        final Iterator<LinkDTO> iter = groupList.iterator();
        while (iter.hasNext()) {
            final LinkDTO group = iter.next();
            view.addCategoryMember(categoryName, group.getShortName(), group.getLongName(), actions);
        }
    }
}
