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

package org.ourproject.kune.workspace.client.theme;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.ThemeMenuComponent;

public class ThemeMenuPresenter implements ThemeMenuComponent {

    private ThemeMenuView view;

    public void chooseTheme(final String theme) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.CHANGE_GROUP_WSTHEME, theme);
    }

    public void init(final ThemeMenuView view) {
        this.view = view;
    }

    public void setThemes(final String[] themes) {
        view.setThemes(themes);
    }

    public View getView() {
        return view;
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }
}
