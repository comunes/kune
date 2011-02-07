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
 */
package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.workspace.client.options.EntityOptions;
import org.ourproject.kune.workspace.client.themes.WsBackManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.themes.WsThemeSelector;

import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class GroupOptionsPublicSpaceConfPresenter extends EntityOptionsPublicSpaceConfPresenter implements
        GroupOptionsPublicSpaceConf {

    public GroupOptionsPublicSpaceConfPresenter(final Session session, final StateManager stateManager,
            final EntityOptions entityOptions, final WsThemeManager themeManager, final WsThemeSelector themeSelector,
            final Provider<GroupServiceAsync> groupService, final WsBackManager backManager) {
        super(session, stateManager, entityOptions, groupService, backManager);
        themeSelector.addThemeSelected(new Listener<WsTheme>() {
            public void onEvent(final WsTheme theme) {
                themeManager.changeTheme(session.getCurrentStateToken(), theme);
            }
        });
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                final String theme = state.getGroup().getWorkspaceTheme();
                themeSelector.select(theme);
            }
        });
    }

}