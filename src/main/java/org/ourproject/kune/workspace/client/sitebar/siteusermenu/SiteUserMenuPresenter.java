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
 */package org.ourproject.kune.workspace.client.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.MenuItem;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class SiteUserMenuPresenter implements SiteUserMenu {

    private SiteUserMenuView view;
    private final MenuItemCollection<GroupDTO> participateInGroups;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<EntityOptions> entityOptions;
    private final Provider<FileDownloadUtils> downloadProvider;

    public SiteUserMenuPresenter(final Session session, final StateManager stateManager,
            final Provider<EntityOptions> entityOptions, final Provider<FileDownloadUtils> downloadProvider) {
        this.session = session;
        this.stateManager = stateManager;
        this.entityOptions = entityOptions;
        this.downloadProvider = downloadProvider;
        participateInGroups = new MenuItemCollection<GroupDTO>();
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

    public View getView() {
        return view;
    }

    public void init(final SiteUserMenuView view) {
        this.view = view;
    }

    public void onUserHomePage() {
        stateManager.gotoToken(session.getCurrentUserInfo().getShortName());
    }

    public void onUserPreferences() {
        onUserHomePage();
        entityOptions.get().show();
    }

    private void addPartipation(final GroupDTO group) {
        String logoImageUrl = group.hasLogo() ? downloadProvider.get().getLogoImageUrl(group.getStateToken())
                : "images/group-def-icon.gif";
        participateInGroups.add(new MenuItem<GroupDTO>(logoImageUrl, group.getShortName(), new Listener<GroupDTO>() {
            public void onEvent(final GroupDTO param) {
                stateManager.gotoToken(group.getShortName());
            }
        }));
    }

    private void onUserSignIn(final UserInfoDTO userInfoDTO) {
        view.setVisible(true);
        view.setLoggedUserName(userInfoDTO.getShortName());
        participateInGroups.clear();
        for (final GroupDTO group : userInfoDTO.getGroupsIsAdmin()) {
            addPartipation(group);
        }
        for (final GroupDTO group : userInfoDTO.getGroupsIsCollab()) {
            addPartipation(group);
        }
        view.setParticipation(participateInGroups);
    }

}
