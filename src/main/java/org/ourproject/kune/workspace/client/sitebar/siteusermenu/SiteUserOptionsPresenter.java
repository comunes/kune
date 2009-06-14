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
package org.ourproject.kune.workspace.client.sitebar.siteusermenu;

import java.util.List;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.common.GotoGroupAction;
import org.ourproject.kune.platf.client.actions.ui.AbstractActionExtensiblePresenter;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.workspace.client.options.UserOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class SiteUserOptionsPresenter extends AbstractActionExtensiblePresenter implements SiteUserOptions {

    private SiteUserOptionsView view;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<UserOptions> userOptions;
    private final Provider<FileDownloadUtils> downloadProvider;
    private MenuDescriptor menuDescriptor;
    private final I18nTranslationService i18n;
    private final ImgResources img;

    private MenuDescriptor partiMenu;;

    public SiteUserOptionsPresenter(final Session session, final StateManager stateManager,
            final Provider<UserOptions> userOptions, final Provider<FileDownloadUtils> downloadProvider,
            final I18nTranslationService i18n, final ImgResources img) {
        super();
        this.session = session;
        this.stateManager = stateManager;
        this.userOptions = userOptions;
        this.downloadProvider = downloadProvider;
        this.i18n = i18n;
        this.img = img;
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO userInfoDTO) {
                onUserSignIn(userInfoDTO);
            }

        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                view.setVisible(false);
                view.setLoggedUserName("");
            }
        });
    }

    @Override
    public void addAction(final GuiActionDescrip descriptor) {
        descriptor.setParent(menuDescriptor);
        view.addAction(descriptor);
    }

    public View getView() {
        return view;
    }

    public void init(final SiteUserOptionsView view) {
        this.view = view;
        createActions();
    }

    private void addPartipation(final GroupDTO group) {
        final String logoImageUrl = group.hasLogo() ? downloadProvider.get().getLogoImageUrl(group.getStateToken())
                : "images/group-def-icon.gif";
        final GotoGroupAction gotoGroupAction = new GotoGroupAction(logoImageUrl, group.getLongName(), stateManager);
        view.addAction(new MenuItemDescriptor(partiMenu, gotoGroupAction));
    }

    private void createActions() {
        menuDescriptor = new MenuDescriptor();
        menuDescriptor.setStandalone(true);
        view.addAction(menuDescriptor);
        view.setMenu(menuDescriptor);

        partiMenu = new MenuDescriptor(i18n.t("Your groups"));
        addAction(partiMenu);

        final AbstractExtendedAction userHomeAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                goUserHome();
            }
        };
        userHomeAction.putValue(Action.NAME, i18n.t(PlatfMessages.YOUR_HOMEPAGE));
        userHomeAction.putValue(Action.SMALL_ICON, img.groupHome());
        final MenuItemDescriptor item = new MenuItemDescriptor(userHomeAction);
        item.setPosition(0);
        addAction(item);

        final AbstractExtendedAction userPrefsAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                goUserHome();
                userOptions.get().show();
            }
        };
        userPrefsAction.putValue(Action.NAME, i18n.t("Your preferences"));
        userPrefsAction.putValue(Action.SMALL_ICON, img.prefs());
        final MenuItemDescriptor prefsItem = new MenuItemDescriptor(userPrefsAction);
        prefsItem.setPosition(1);
        addAction(prefsItem);
    }

    private void goUserHome() {
        stateManager.gotoToken(session.getCurrentUserInfo().getShortName());
    }

    private void onUserSignIn(final UserInfoDTO userInfoDTO) {
        view.setVisible(true);
        view.setLoggedUserName(userInfoDTO.getShortName());
        partiMenu.clear();
        final List<GroupDTO> groupsIsAdmin = userInfoDTO.getGroupsIsAdmin();
        final List<GroupDTO> groupsIsCollab = userInfoDTO.getGroupsIsCollab();
        for (final GroupDTO group : groupsIsAdmin) {
            addPartipation(group);
        }
        for (final GroupDTO group : groupsIsCollab) {
            addPartipation(group);
        }
        partiMenu.setVisible(!groupsIsAdmin.isEmpty() || !groupsIsCollab.isEmpty());
    }

}
