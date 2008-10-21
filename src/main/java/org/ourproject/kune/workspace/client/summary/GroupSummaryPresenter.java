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
package org.ourproject.kune.workspace.client.summary;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class GroupSummaryPresenter implements GroupSummary {

    private GroupSummaryView view;

    public GroupSummaryPresenter(final StateManager stateManager, final WsThemePresenter wsThemePresenter) {
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                setState(state);
            }
        });
        wsThemePresenter.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final GroupSummaryView view) {
        this.view = view;
    }

    private void setState(final StateDTO state) {
        view.setComment("Summary about this group (in development)");
    }

}
