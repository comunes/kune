/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.sitebar;

import java.util.List;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SiteUserOptionsPresenter implements SiteUserOptions {

    public static final MenuDescriptor LOGGED_USER_MENU = new MenuDescriptor();
    public static final String LOGGED_USER_MENU_ID = "kune-sump-lum";
    private final Provider<FileDownloadUtils> downloadProvider;
    private final GotoGroupAction gotoGroupAction;
    private final I18nTranslationService i18n;
    private final IconResources img;
    private SubMenuDescriptor partiMenu;
    private ToolbarSeparatorDescriptor separator;
    private final Session session;
    private final SitebarActionsPresenter siteOptions;
    private final StateManager stateManager;

    @Inject
    public SiteUserOptionsPresenter(final Session session, final StateManager stateManager,
            final Provider<FileDownloadUtils> downloadProvider, final I18nTranslationService i18n,
            final IconResources img, final SitebarActionsPresenter siteOptions, final GotoGroupAction gotoGroupAction) {
        super();
        this.session = session;
        this.stateManager = stateManager;
        this.downloadProvider = downloadProvider;
        this.i18n = i18n;
        this.img = img;
        this.siteOptions = siteOptions;
        this.gotoGroupAction = gotoGroupAction;
        createActions();
        separator.setVisible(false);
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                SiteUserOptionsPresenter.this.onUserSignIn(event.getUserInfo());
                separator.setVisible(true);
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                LOGGED_USER_MENU.setVisible(false);
                separator.setVisible(false);
                SiteUserOptionsPresenter.this.setLoggedUserName("");
            }
        });
    }

    @Override
    public void addAction(final GuiActionDescrip descriptor) {
        addActionImpl(descriptor);
    }

    private void addActionImpl(final GuiActionDescrip descriptor) {
        descriptor.setParent(LOGGED_USER_MENU);
        siteOptions.getRightToolbar().addAction(descriptor);
    }

    private void addPartipation(final GroupDTO group) {
        final String logoImageUrl = group.hasLogo() ? downloadProvider.get().getLogoImageUrl(group.getStateToken())
                : "images/group-def-icon.gif";
        final MenuItemDescriptor participant = new MenuItemDescriptor(gotoGroupAction);
        participant.setTarget(group);
        participant.putValue(Action.NAME, group.getLongName());
        participant.putValue(Action.SMALL_ICON, logoImageUrl);
        participant.setParent(partiMenu);
        siteOptions.getRightToolbar().addAction(participant);
    }

    private void createActions() {
        LOGGED_USER_MENU.setId(LOGGED_USER_MENU_ID);
        LOGGED_USER_MENU.setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
        LOGGED_USER_MENU.setStyles("k-no-backimage, k-btn-sitebar, k-fl");
        siteOptions.getRightToolbar().addAction(LOGGED_USER_MENU);
        separator = new ToolbarSeparatorDescriptor(Type.separator, SitebarActionsPresenter.RIGHT_TOOLBAR);
        siteOptions.getRightToolbar().addAction(separator);
        partiMenu = new SubMenuDescriptor(i18n.t("Your groups"));
        addActionImpl(partiMenu);

        final AbstractExtendedAction userHomeAction = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                goUserHome();
            }
        };
        userHomeAction.putValue(Action.NAME, i18n.t(CoreMessages.YOUR_HOMEPAGE));
        userHomeAction.putValue(Action.SMALL_ICON, img.groupHome());
        final MenuItemDescriptor item = new MenuItemDescriptor(userHomeAction);
        item.setPosition(0);
        addActionImpl(item);
    }

    private void goUserHome() {
        stateManager.gotoToken(session.getCurrentUserInfo().getShortName());
    }

    private void onUserSignIn(final UserInfoDTO userInfoDTO) {
        LOGGED_USER_MENU.setVisible(true);
        LOGGED_USER_MENU.setEnabled(true);
        setLoggedUserName(userInfoDTO.getShortName());
        partiMenu.clear();
        final List<GroupDTO> groupsIsAdmin = userInfoDTO.getGroupsIsAdmin();
        final List<GroupDTO> groupsIsCollab = userInfoDTO.getGroupsIsCollab();
        for (final GroupDTO group : groupsIsAdmin) {
            addPartipation(group);
        }
        for (final GroupDTO group : groupsIsCollab) {
            addPartipation(group);
        }
        partiMenu.setEnabled((groupsIsAdmin.size() + groupsIsCollab.size()) > 0);
    }

    private void setLoggedUserName(final String shortName) {
        LOGGED_USER_MENU.putValue(Action.NAME, shortName);
    }

}
