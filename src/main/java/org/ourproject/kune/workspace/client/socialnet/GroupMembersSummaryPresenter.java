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
 */package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.ImageDescriptor;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.search.GroupLiveSearcher;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class GroupMembersSummaryPresenter extends SocialNetworkPresenter implements GroupMembersSummary {

    protected GridButton addMember;
    private GroupMembersSummaryView view;
    private final I18nUITranslationService i18n;
    private final GridGroup adminCategory;
    private final GridGroup collabCategory;
    private final GridGroup pendigCategory;
    private final Session session;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private final StateManager stateManager;

    public GroupMembersSummaryPresenter(final I18nUITranslationService i18n, final StateManager stateManager,
            final ImageUtils imageUtils, final Session session,
            final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final Provider<GroupLiveSearcher> liveSearcherProvider, final WsThemePresenter wsThemePresenter) {
        super(i18n, stateManager, imageUtils, session, snServiceProvider);
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.session = session;
        this.snServiceProvider = snServiceProvider;
        final Listener<StateAbstractDTO> setStateListener = new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                setState(state);
            }
        };
        stateManager.onStateChanged(setStateListener);
        stateManager.onSocialNetworkChanged(setStateListener);
        wsThemePresenter.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
        final String adminsTitle = i18n.t("Admins");
        final String collabsTitle = i18n.t("Collaborators");
        final String pendingTitle = i18n.t("Pending");
        adminCategory = new GridGroup(adminsTitle, adminsTitle, i18n.t("People that can admin this group"), true);
        collabCategory = new GridGroup(collabsTitle, collabsTitle,
                i18n.t("Other people that collaborate with this group"), true);
        pendigCategory = new GridGroup(pendingTitle, pendingTitle,
                i18n.t("People pending to be accepted in this group by the admins"),
                imageUtils.getImageHtml(ImageDescriptor.alert), true);
        // i18n.t("Add member")
        addMember = new GridButton("images/add-green.gif", "",
                i18n.t("Add a group or a person as member of this group"), new Listener<String>() {
                    public void onEvent(final String parameter) {
                        liveSearcherProvider.get().onSelection(new Listener<LinkDTO>() {
                            public void onEvent(final LinkDTO link) {
                                view.confirmAddCollab(link.getShortName(), link.getLongName());
                            }
                        });
                        liveSearcherProvider.get().show();
                    }
                });
        super.addGroupOperation(gotoGroupMenuItem, false);
        super.addUserOperation(gotoMemberMenuItem, false);
    }

    public void addCollab(final String groupShortName) {
        Site.showProgressProcessing();
        snServiceProvider.get().addCollabMember(session.getUserHash(), session.getCurrentState().getStateToken(),
                groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                    public void onSuccess(final SocialNetworkResultDTO result) {
                        Site.hideProgress();
                        Site.info(i18n.t("Member added as collaborator"));
                        stateManager.setSocialNetwork(result);
                    }

                });
    }

    public void init(final GroupMembersSummaryView view) {
        this.view = view;
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
        return userIsAdmin || userIsCollab;
    }

    @SuppressWarnings("unchecked")
    private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
        final AccessListsDTO accessLists = socialNetwork.getAccessLists();

        final List<GroupDTO> adminsList = accessLists.getAdmins().getList();
        final List<GroupDTO> collabList = accessLists.getEditors().getList();
        final List<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

        final int numAdmins = adminsList.size();

        boolean userIsAdmin = rights.isAdministrable();
        final boolean userIsCollab = !userIsAdmin && rights.isEditable();
        final boolean userCanView = rights.isVisible();
        boolean userIsMember = isMember(userIsAdmin, userIsCollab);

        view.clear();

        if (userIsAdmin) {
            view.addButton(addMember);
            view.addToolbarFill();
        }

        view.setDraggable(session.isLogged());

        if (!userIsMember) {
            view.addButton(requestJoin);
        } else if (userIsAdmin && numAdmins > 1 || userIsCollab) {
            view.addButton(unJoinButton);
        }

        if (userCanView) {
            for (final GroupDTO admin : adminsList) {
                view.addItem(createGridItem(adminCategory, admin, rights, changeToCollabMenuItem, removeMemberMenuItem));
            }
            for (final GroupDTO collab : collabList) {
                view.addItem(createGridItem(collabCategory, collab, rights, changeToAdminMenuItem, removeMemberMenuItem));
            }
            if (userIsAdmin) {
                for (final GroupDTO pendingCollab : pendingCollabsList) {
                    view.addItem(createGridItem(pendigCategory, pendingCollab, rights, acceptJoinGroupMenuItem,
                            denyJoinGroupMenuItem));
                }
            }
        }
        view.setVisible(true);
    }

    private void setState(final StateAbstractDTO state) {
        if (state.getGroup().getGroupType().equals(GroupType.PERSONAL)) {
            view.setVisible(false);
        } else {
            setGroupMembers(state.getGroupMembers(), state.getGroupRights());
        }
    }

}
