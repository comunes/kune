/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import java.util.List;

import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.workspace.client.socialnet.toolbar.ActionParticipationToolbar;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRightsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.ParticipationDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class ParticipationSummaryPresenter extends SocialNetworkPresenter implements ParticipationSummary {

    private ParticipationSummaryView view;
    private final GridGroup adminCategory;
    private GridGroup collabCategory;
    private final GridGroup collabOnlyCategory;

    public ParticipationSummaryPresenter(final I18nUITranslationService i18n, final StateManager stateManager,
            final ImageUtils imageUtils, final Session session,
            final Provider<SocialNetworkServiceAsync> snServiceProvider, final GroupActionRegistry groupActionRegistry,
            final ActionParticipationToolbar actionParticipationToolbar,
            final Provider<FileDownloadUtils> downloadProvider, final AccessRightsClientManager accessRightsManager,
            final ImgResources img) {
        super(i18n, stateManager, accessRightsManager, session, snServiceProvider, groupActionRegistry,
                downloadProvider, img);
        adminCategory = new GridGroup("admin in:", " ", i18n.tWithNT("Administrate these groups",
                "talking about a person"), false);
        collabCategory = new GridGroup(i18n.t("and as collaborator in:"), " ", i18n.t("Collaborate in these groups"),
                false);
        collabOnlyCategory = new GridGroup(i18n.t("collaborator in:"), " ", i18n.t("Collaborate in these groups"),
                false);
        super.addGroupOperation(gotoGroupMenuItem, false);
        final Listener<StateAbstractDTO> setStateListener = new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        };
        stateManager.onStateChanged(setStateListener);
        stateManager.onSocialNetworkChanged(setStateListener);
    }

    public void init(final ParticipationSummaryView view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    private void setState(final StateAbstractDTO state) {
        final ParticipationDataDTO participation = state.getParticipation();
        final AccessRightsDTO rights = state.getGroupRights();
        view.clear();
        final List<GroupDTO> groupsIsAdmin = participation.getGroupsIsAdmin();
        final List<GroupDTO> groupsIsCollab = participation.getGroupsIsCollab();
        final int numAdmins = groupsIsAdmin.size();
        final int numCollaborators = groupsIsCollab.size();
        if (numAdmins == 0) {
            collabCategory = collabOnlyCategory;
        }
        for (final GroupDTO group : groupsIsAdmin) {
            view.addItem(createGridItem(adminCategory, group, rights, unJoinMenuItem));
        }
        for (final GroupDTO group : groupsIsCollab) {
            view.addItem(createGridItem(collabCategory, group, rights, unJoinMenuItem));
        }
        if (numAdmins > 0 || numCollaborators > 0) {
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }
}
