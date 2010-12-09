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
package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateToken;

import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class WsThemeManager {

    private WsTheme previousTheme;
    private final Event2<WsTheme, WsTheme> onThemeChanged;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final Session session;
    private WsTheme defTheme;
    private final WsBackManager wsBackManager;

    public WsThemeManager(final Session session, final Provider<GroupServiceAsync> groupServiceProvider,
            final StateManager stateManager, final WsBackManager wsBackManager) {
        this.session = session;
        this.groupServiceProvider = groupServiceProvider;
        this.wsBackManager = wsBackManager;
        this.onThemeChanged = new Event2<WsTheme, WsTheme>("onThemeChanged");
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO initData) {
                setDefTheme(initData);
                setTheme(defTheme);
            }
        });
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
    }

    public void addOnThemeChanged(final Listener2<WsTheme, WsTheme> listener) {
        onThemeChanged.add(listener);
    }

    public void changeTheme(final StateToken token, final WsTheme newTheme) {
        NotifyUser.showProgressProcessing();
        groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(), token, newTheme.getName(),
                new AsyncCallbackSimple<Void>() {
                    public void onSuccess(final Void result) {
                        if (session.getCurrentState().getStateToken().getGroup().equals(token.getGroup())) {
                            setTheme(newTheme);
                        }
                        NotifyUser.hideProgress();
                    }
                });
    }

    protected void onChangeGroupWsTheme(final WsTheme newTheme) {
        NotifyUser.showProgressProcessing();
        groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(), session.getCurrentState().getStateToken(),
                newTheme.getName(), new AsyncCallbackSimple<Void>() {
                    public void onSuccess(final Void result) {
                        setTheme(newTheme);
                        NotifyUser.hideProgress();
                    }
                });
    }

    private void setDefTheme(final InitDataDTO initData) {
        defTheme = new WsTheme(initData.getWsThemes()[0]);
    }

    private void setState(final StateAbstractDTO state) {
        setTheme(new WsTheme(state.getGroup().getWorkspaceTheme()));
        final ContentSimpleDTO groupBackImage = state.getGroup().getGroupBackImage();
        if (groupBackImage == null) {
            wsBackManager.clearBackImage();
        } else {
            wsBackManager.setBackImage(groupBackImage.getStateToken());
        }
    }

    private void setTheme(final WsTheme newTheme) {
        if (previousTheme == null || !previousTheme.equals(newTheme)) {
            onThemeChanged.fire(previousTheme, newTheme);
        }
        previousTheme = newTheme;
    }

}
