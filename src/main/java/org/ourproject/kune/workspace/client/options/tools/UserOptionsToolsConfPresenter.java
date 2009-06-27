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
package org.ourproject.kune.workspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.ToolSimpleDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsToolsConfPresenter extends EntityOptionsToolsConfPresenter implements UserOptionsToolsConf {

    public UserOptionsToolsConfPresenter(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final EntityOptions entityOptions,
            final Provider<GroupServiceAsync> groupService) {
        super(session, stateManager, i18n, entityOptions, groupService);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    protected boolean applicable() {
        return session.isLogged();
    }

    @Override
    protected Collection<ToolSimpleDTO> getAllTools() {
        return session.getUserTools();
    }

    @Override
    protected StateToken getDefContentToken() {
        final String homePage = session.getCurrentUserInfo().getHomePage();
        return homePage == null ? null : new StateToken(homePage);
    }

    @Override
    protected String getDefContentTooltip() {
        return i18n.t("You cannot disable this tool because it's where your home page is located. To do that you have to select other content as the default home page but in another tool.");
    }

    @Override
    protected List<String> getEnabledTools() {
        return session.getCurrentUserInfo().getEnabledTools();
    }

    @Override
    protected StateToken getOperationToken() {
        return session.getCurrentUser().getStateToken();
    }

    @Override
    protected void gotoDifLocationIfNecessary(final String toolName) {
        if (session.getCurrentStateToken().getGroup().equals(session.getCurrentUserInfo().getShortName())
                && session.getCurrentStateToken().getTool().equals(toolName)) {
            stateManager.gotoToken(session.getCurrentState().getGroup().getDefaultContent().getStateToken());
        }
    }
}
