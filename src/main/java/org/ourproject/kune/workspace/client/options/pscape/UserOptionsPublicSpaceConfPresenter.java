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
package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.options.EntityOptions;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.themes.WsThemeSelector;

import com.calclab.suco.client.events.Listener;

public class UserOptionsPublicSpaceConfPresenter extends EntityOptionsPublicSpaceConfPresenter implements
        UserOptionsPublicSpaceConf {

    private final WsThemeSelector themeSelector;

    public UserOptionsPublicSpaceConfPresenter(final Session session, final EntityOptions entityOptions,
            final WsThemeManager themeManager, final WsThemeSelector themeSelector) {
        super(entityOptions);
        this.themeSelector = themeSelector;
        themeSelector.addThemeSelected(new Listener<WsTheme>() {
            public void onEvent(final WsTheme theme) {
                themeManager.changeTheme(session.getCurrentUser().getStateToken(), theme);
            }
        });
        final UserInfoDTO userInfo = session.getCurrentUserInfo();
        if (userInfo != null) {
            setSelector(userInfo);
        }
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setSelector(userInfo);
            }
        });
    }

    private void setSelector(final UserInfoDTO userInfo) {
        themeSelector.select(userInfo.getUserGroup().getWorkspaceTheme());
    }

}
