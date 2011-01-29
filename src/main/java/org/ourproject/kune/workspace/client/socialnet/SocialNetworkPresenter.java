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
 \*/
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.gridmenu.CustomMenu;
import org.ourproject.kune.platf.client.ui.gridmenu.GridGroup;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class SocialNetworkPresenter {

    private static final String MEMBERS_BOTTON = "members_bottom";

    protected final ActionToolbarPosition membersBottom = new ActionToolbarPosition("sn-bottomtoolbar");
    protected final ActionToolbarPosition buddiesBottom = new ActionToolbarPosition("sn-bottomtoolbar");
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
    private final AccessRightsClientManager rightsManager;
    private final IconResources imgResources;

    private MenuDescriptor menuOptions;

    public SocialNetworkPresenter(final I18nTranslationService i18n, final StateManager stateManager,
            final AccessRightsClientManager rightsManager, final Session session,
            final Provider<SocialNetworkServiceAsync> snServiceProvider, final GroupActionRegistry groupActionRegistry,
            final Provider<FileDownloadUtils> downloadProvider, final IconResources imgResources) {
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.rightsManager = rightsManager;
        this.session = session;
        this.snServiceProvider = snServiceProvider;
        this.downloadProvider = downloadProvider;
        this.imgResources = imgResources;
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
            final AccessRights rights, final MenuItem<GroupDTO>... gridMenuItems) {
        final GridItem<GroupDTO> gridItem = createDefMemberMenu(group, groupCategory);
        final CustomMenu<GroupDTO> menu = gridItem.getMenu();
        if (rights.isAdministrable()) {
            for (final MenuItem<GroupDTO> item : gridMenuItems) {
                menu.addMenuItem(item);
            }
        }
        return gridItem;
    }

    protected String createTooltipWithLogo(final String shortName, final StateToken token, final boolean hasLogo,
            final boolean isPersonal) {
        return "<table><tr><td>"
                + (hasLogo ? downloadProvider.get().getLogoAvatarHtml(token, hasLogo, isPersonal,
                        FileConstants.LOGO_DEF_HEIGHT, 3) : "") + "</td><td>"
                + i18n.t(isPersonal ? "Nickname: [%s]" : "Group short name: [%s]", shortName) + "</td></tr></table>";
    }

    private void createButtons() {
        final ParticipateAction participateAction = new ParticipateAction(session, snServiceProvider, stateManager,
                rightsManager, i18n, imgResources);
        final ButtonDescriptor participateBtn = new ButtonDescriptor(participateAction);
        participateBtn.setLocation(MEMBERS_BOTTON);

        menuOptions = new MenuDescriptor(i18n.t("Options"));

        final UnjoinAction unjoinAction = new UnjoinAction(session, snServiceProvider, stateManager, rightsManager,
                i18n, imgResources) {
            @Override
            public String getGroupName() {
                return session.getContainerState().getGroup().getShortName();
            }
        };

        final MenuItemDescriptor unjoinBtn = new MenuItemDescriptor(menuOptions, unjoinAction);
        unjoinBtn.setLocation(MEMBERS_BOTTON);
    }

    private GridItem<GroupDTO> createDefMemberMenu(final GroupDTO group, final GridGroup gridGroup) {
        final CustomMenu<GroupDTO> menu = new CustomMenu<GroupDTO>(group);
        final String longName = group.getLongName();
        final boolean hasLogo = group.hasLogo();
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
                    @Override
                    public void onEvent(final GroupDTO groupDTO) {
                        stateManager.gotoToken(groupDTO.getShortName());
                    }
                });
        gotoMemberMenuItem = new MenuItem<GroupDTO>("images/group-home.gif", i18n.t("Visit this member homepage"),
                new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO groupDTO) {
                        stateManager.gotoToken(groupDTO.getShortName());
                    }
                });

        unJoinMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n.t("Do not participate anymore in this group"),
                new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO groupDTO) {
                        removeMemberAction(groupDTO);
                    }
                });
        changeToCollabMenuItem = new MenuItem<GroupDTO>("images/arrow-down-green.gif",
                i18n.t("Change to collaborator"), new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO group) {
                        NotifyUser.showProgressProcessing();
                        snServiceProvider.get().setAdminAsCollab(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    @Override
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Member type changed"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        removeMemberMenuItem = new MenuItem<GroupDTO>("images/del.gif", i18n.t("Remove this member"),
                new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO group) {
                        NotifyUser.showProgressProcessing();
                        snServiceProvider.get().deleteMember(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    @Override
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Member removed"));
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
                    @Override
                    public void onEvent(final GroupDTO group) {
                        NotifyUser.showProgressProcessing();
                        snServiceProvider.get().setCollabAsAdmin(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    @Override
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Member type changed"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        acceptJoinGroupMenuItem = new MenuItem<GroupDTO>("images/accept.gif", i18n.t("Accept this member"),
                new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO group) {
                        NotifyUser.showProgressProcessing();
                        snServiceProvider.get().acceptJoinGroup(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    @Override
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Member accepted"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
        denyJoinGroupMenuItem = new MenuItem<GroupDTO>("images/cancel.gif", i18n.t("Reject this member"),
                new Listener<GroupDTO>() {
                    @Override
                    public void onEvent(final GroupDTO group) {
                        NotifyUser.showProgressProcessing();
                        snServiceProvider.get().denyJoinGroup(session.getUserHash(),
                                session.getCurrentState().getStateToken(), group.getShortName(),
                                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                                    @Override
                                    public void onSuccess(final SocialNetworkDataDTO result) {
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Member rejected"));
                                        stateManager.setSocialNetwork(result);
                                    }
                                });
                    }
                });
    }

    private void removeMemberAction(final GroupDTO groupDTO) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().unJoinGroup(session.getUserHash(), new StateToken(groupDTO.getShortName()),
                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                    @Override
                    public void onSuccess(final SocialNetworkDataDTO result) {
                        NotifyUser.hideProgress();
                        NotifyUser.info(i18n.t("Removed as member"));
                        stateManager.reload();
                        // in the future with user
                        // info:
                        // services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                        // result);
                    }
                });
    }
}
