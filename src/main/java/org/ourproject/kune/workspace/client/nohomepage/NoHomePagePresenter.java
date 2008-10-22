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
 */
package org.ourproject.kune.workspace.client.nohomepage;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener0;

public class NoHomePagePresenter implements NoHomePage {

    private NoHomePageView view;

    public NoHomePagePresenter(final Session session, final StateManager stateManager, KuneErrorHandler errorHandler,
            EntityLogo entityLogo, final Provider<GroupServiceAsync> groupServiceProvider,
            final Provider<EntityLogo> entityLogoProvider, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final HistoryWrapper history) {

        errorHandler.onNotDefaultContent(new Listener0() {
            public void onEvent() {
                view.clearWs();
                StateToken groupToken = new StateToken(history.getToken());
                groupServiceProvider.get().getGroup(session.getUserHash(), groupToken,
                        new AsyncCallbackSimple<GroupDTO>() {
                            public void onSuccess(GroupDTO group) {
                                StateDTO currentState = new StateDTO();
                                currentState.setStateToken(group.getStateToken());
                                session.setCurrentState(currentState);
                                session.getCurrentState().setGroup(group);
                                entityLogoProvider.get().refreshGroupLogo();
                                snServiceProvider.get().getSocialNetwork(session.getUserHash(),
                                        session.getCurrentStateToken(),
                                        new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                                            public void onSuccess(SocialNetworkResultDTO result) {
                                                session.getCurrentState().setGroupRights(result.getGroupRights());
                                                stateManager.setSocialNetwork(result);
                                            }
                                        });
                            }
                        });
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(NoHomePageView view) {
        this.view = view;
    }
}
