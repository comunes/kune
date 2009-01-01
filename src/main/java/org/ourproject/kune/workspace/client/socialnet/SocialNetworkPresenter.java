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

import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.GroupActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.gridmenu.CustomMenu;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderView;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class SocialNetworkPresenter {

    protected MenuItem<GroupDTO> changeToCollabMenuItem;
    protected MenuItem<GroupDTO> removeMemberMenuItem;
    protected MenuItem<GroupDTO> changeToAdminMenuItem;
    protected MenuItem<GroupDTO> acceptJoinGroupMenuItem;
    protected MenuItem<GroupDTO> denyJoinGroupMenuItem;
    protected MenuItem<GroupDTO> gotoMemberMenuItem;
    protected MenuItem<GroupDTO> gotoGroupMenuItem;
    protected MenuItem<GroupDTO> unJoinMenuItem;
    private final I18nTranslationService i18n;
    private final StateManager stateManager;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private final Session session;
    private final MenuItemCollection<GroupDTO> otherOperations;
    private final MenuItemCollection<GroupDTO> otherLoggedOperations;
    private final MenuItemCollection<GroupDTO> otherOperationsUsers;
    private final MenuItemCollection<GroupDTO> otherLoggedOperationsUsers;

    protected ActionToolbarMenuDescriptor<StateToken> unJoin;
    protected ActionToolbarButtonDescriptor<StateToken> participate;
    private final Provider<FileDownloadUtils> downloadProvider;

    public SocialNetworkPresenter(final I18nTranslationService i18n, final StateManager stateManager,
            final Session session, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            GroupActionRegistry groupActionRegistry, final Provider<FileDownloadUtils> downloadProvider) {
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.session = session;
        this.snServiceProvider = snServiceProvider;
        this.downloadProvider = downloadProvider;
        createButtons();
        createMenuActions();
        otherOperationsUsers = new MenuItemCollection<GroupDTO>();
        otherLoggedOperationsUsers = new MenuItemCollection<GroupDTO>();
        otherOperations = new MenuItemCollection<GroupDTO>();
        otherLoggedOperations = new MenuItemCollection<GroupDTO>();
    }

    public void addGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
        MenuItemCollection<GroupDTO> collection;
        collection = mustBeLogged ? otherLoggedOperations : otherOperations;
        collection.add(operation);
    }

    public void addUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
        MenuItemCollection<GroupDTO> collection;
        collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
        collection.add(operation);
    }

    public void onDoubleClick(final String groupShortName) {
        stateManager.gotoToken(groupShortName);
    }

    public void removeGroupOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
        MenuItemCollection<GroupDTO> collection;
        collection = mustBeLogged ? otherLoggedOperations : otherOperations;
        collection.remove(operation);
    }

    public void removeUserOperation(final MenuItem<GroupDTO> operation, final boolean mustBeLogged) {
        MenuItemCollection<GroupDTO> collection;
        collection = mustBeLogged ? otherLoggedOperationsUsers : otherOperationsUsers;
        collection.remove(operation);
    }

    protected GridItem<GroupDTO> createGridItem(final GridGroup groupCategory, final GroupDTO group,
            final AccessRightsDTO rights, final MenuItem<GroupDTO>... gridMenuItems) {
        final GridItem<GroupDTO> gridItem = createDefMemberMenu(group, groupCategory);
        final CustomMenu<GroupDTO> menu = gridItem.getMenu();
        if (rights.isAdministrable()) {
            for (final MenuItem<GroupDTO> item : gridMenuItems) {
                menu.addMenuItem(item);
            }
        }
        return gridItem;
    }

    protected String createTooltipWithLogo(String shortName, StateToken token, boolean hasLogo, boolean isPersonal) {
        return "<table><tr><td>"
                + (hasLogo ? downloadProvider.get().getLogoAvatarHtml(token, hasLogo, isPersonal,
                        EntityHeaderView.LOGO_ICON_DEFAULT_HEIGHT, 3) : "") + "</td><td>"
                + i18n.t(isPersonal ? "Nickname: [%s]" : "Group short name: [%s]", shortName) + "</td></tr></table>";
    }

    protected boolean isMember(AccessRightsDTO rights) {
        boolean userIsAdmin = rights.isAdministrable();
        final boolean userIsCollab = !userIsAdmin && rights.isEditable();
        return isMember(userIsAdmin, userIsCollab);
    }

    private void createButtons() {
        participate = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Viewer,
                ActionToolbarPosition.bottombar, new Listener<StateToken>() {
                    public void onEvent(StateToken parameter) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().requestJoinGroup(session.getUserHash(),
                                session.getCurrentState().getStateToken(), new AsyncCallbackSimple<Object>() {
                                    public void onSuccess(final Object result) {
                                        Site.hideProgress();
                                        final SocialNetworkRequestResult resultType = (SocialNetworkRequestResult) result;
                                        switch (resultType) {
                                        case accepted:
                                            Site.info(i18n.t("You are now member of this group"));
                                            stateManager.reload();
                                            break;
                                        case denied:
                                            Site.important(i18n.t("Sorry this is a closed group"));
                                            break;
                                        case moderated:
                                            Site.info(i18n.t("Membership requested. Waiting for admins decision"));
                                            break;
                                        }
                                    }
                                });
                    }
                });
        participate.setIconUrl("images/add-green.gif");
        participate.setTextDescription(i18n.t("Participate"));
        participate.setToolTip(i18n.t("Request to participate in this group"));
        participate.setMustBeAuthenticated(false);
        participate.setAddCondition(new ActionAddCondition<StateToken>() {
            public boolean mustBeAdded(StateToken token) {
                return !isMember(session.getCurrentState().getGroupRights());
            }
        });

        unJoin = new ActionToolbarMenuDescriptor<StateToken>(AccessRolDTO.Editor, ActionToolbarPosition.bottombar,
                new Listener<StateToken>() {
                    public void onEvent(StateToken parameter) {
                        removeMemberAction();
                    }
                });
        unJoin.setIconUrl("images/del.gif");
        unJoin.setTextDescription(i18n.t("Leave this group"));
        unJoin.setToolTip(i18n.t("Do not participate anymore in this group"));
        unJoin.setParentMenuTitle(i18n.t("Options"));
        unJoin.setMustBeConfirmed(true);
        unJoin.setConfirmationTitle(i18n.t("Leave this group"));
        unJoin.setConfirmationText(i18n.t("Are you sure?"));
    }

    private GridItem<GroupDTO> createDefMemberMenu(final GroupDTO group, final GridGroup gridGroup) {
        final CustomMenu<GroupDTO> menu = new CustomMenu<GroupDTO>(group);
        final String longName = group.getLongName();
        boolean hasLogo = group.hasLogo();
        final String toolTip = createTooltipWithLogo(group.getShortName(), group.getStateToken(), hasLogo,
                group.isPersonal());
        final String imageHtml = downloadProvider.get().getLogoAvatarHtml(group.getStateToken(), hasLogo,
                group.isPersonal(), session.getImgIconsize(), 0);
        final GridItem<GroupDTO> gridItem = new GridItem<GroupDTO>(group, gridGroup, group.getShortName(), imageHtml,
                longName, longName, " ", longName, toolTip, menu);
        if (!group.isPersonal()) {
            menu.addMenuItemList(otherOperations);
        }
        if (session.isLogged() && !group.isPersonal()) {
            menu.addMenuItemList(otherLoggedOperations);
        }
        if (group.isPersonal()) {
            menu.addMenuItemList(otherOperationsUsers);
        }
        if (session.isLogged() && group.isPersonal()) {
            menu.addMenuItemList(otherLoggedOperationsUsers);
        }

        return gridItem;
    }

    private void createMenuActions() {
        gotoGroupMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this group homepage"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO groupDTO) {
                        stateManager.gotoToken(groupDTO.getShortName());
                    }
                });
        gotoMemberMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this member homepage"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO groupDTO) {
                        stateManager.gotoToken(groupDTO.getShortName());
                    }
                });
        unJoinMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n.t("Do not participate anymore in this group"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO groupDTO) {
                        removeMemberAction(groupDTO);
                    }
                });
        changeToCollabMenuItem = new MenuItem<GroupDTO>("images/arrow-down-green.gif",
                i18n.t("Change to collaborator"), new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO group) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().setAdminAsCollab(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        Site.hideProgress();
                                        Site.info(i18n.t("Member type changed"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        removeMemberMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n.t("Remove this member"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO group) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().deleteMember(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        Site.hideProgress();
                                        Site.info(i18n.t("Member removed"));
                                        stateManager.reload();
                                        // in the
                                        // future,
                                        // only if
                                        // I cannot
                                        // be
                                        // affected:
                                        // snService.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                                        // result);
                                    }
                                });
                    }
                });
        changeToAdminMenuItem = new MenuItem<GroupDTO>("images/arrow-up-green.gif", i18n.t("Change to admin"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO group) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().setCollabAsAdmin(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        Site.hideProgress();
                                        Site.info(i18n.t("Member type changed"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        acceptJoinGroupMenuItem = new MenuItem<GroupDTO>("images/accept.gif", i18n.t("Accept this member"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO group) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().acceptJoinGroup(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        Site.hideProgress();
                                        Site.info(i18n.t("Member accepted"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        denyJoinGroupMenuItem = new MenuItem<GroupDTO>("images/cancel.gif", i18n.t("Reject this member"),
                new Listener<GroupDTO>() {
                    public void onEvent(final GroupDTO group) {
                        Site.showProgressProcessing();
                        snServiceProvider.get().denyJoinGroup(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        Site.hideProgress();
                                        Site.info(i18n.t("Member rejected"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
        return userIsAdmin || userIsCollab;
    }

    private void removeMemberAction() {
        removeMemberAction(session.getCurrentState().getGroup());
    }

    private void removeMemberAction(final GroupDTO groupDTO) {
        Site.showProgressProcessing();
        snServiceProvider.get().unJoinGroup(session.getUserHash(), new StateToken(groupDTO.getShortName()),
                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                    public void onSuccess(final SocialNetworkDataDTO result) {
                        Site.hideProgress();
                        Site.info(i18n.t("Removed as member"));
                        stateManager.reload();
                        // in the future with user
                        // info:
                        // services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                        // result);
                    }
                });
    }
}
