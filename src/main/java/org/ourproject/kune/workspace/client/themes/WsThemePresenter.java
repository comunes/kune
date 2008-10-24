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
 */package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class WsThemePresenter {

    private WsThemeView view;
    private WsTheme previousTheme;
    private final Event2<WsTheme, WsTheme> onThemeChanged;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final Session session;
    private WsTheme defTheme;

    public WsThemePresenter(final Session session, final Provider<GroupServiceAsync> groupServiceProvider,
            final StateManager stateManager) {
        this.session = session;
        this.groupServiceProvider = groupServiceProvider;
        this.onThemeChanged = new Event2<WsTheme, WsTheme>("onThemeChanged");
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO initData) {
                view.setThemes(initData.getWsThemes());
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

    public void init(final WsThemeView view) {
        this.view = view;
    }

    public void onThemeChanged(final Listener2<WsTheme, WsTheme> listener) {
        onThemeChanged.add(listener);
    }

    protected void onChangeGroupWsTheme(final WsTheme newTheme) {
        Site.showProgressProcessing();
        groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(), session.getCurrentState().getStateToken(),
                newTheme.getName(), new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        setTheme(newTheme);
                        Site.hideProgress();
                    }
                });
    }

    private void setDefTheme(final InitDataDTO initData) {
        defTheme = new WsTheme(initData.getWsThemes()[0]);
    }

    private void setState(final StateAbstractDTO state) {
        setTheme(new WsTheme(state.getGroup().getWorkspaceTheme()));
        if (state.getGroupRights().isAdministrable()) {
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }

    private void setTheme(final WsTheme newTheme) {
        if (previousTheme == null || !previousTheme.equals(newTheme)) {
            onThemeChanged.fire(previousTheme, newTheme);
        }
        previousTheme = newTheme;
    }

}
