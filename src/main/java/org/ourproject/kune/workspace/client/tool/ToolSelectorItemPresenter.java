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
package org.ourproject.kune.workspace.client.tool;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;

import com.calclab.suco.client.events.Listener2;

public class ToolSelectorItemPresenter implements ToolSelectorItem {

    private ToolSelectorItemView view;
    private final ToolSelector toolSelector;
    private final WsThemeManager wsThemePresenter;
    private final String shortName;
    private final String longName;

    public ToolSelectorItemPresenter(final String shortName, final String longName, final ToolSelector toolSelector,
            final WsThemeManager wsThemePresenter) {
        this.shortName = shortName;
        this.longName = longName;
        this.toolSelector = toolSelector;
        this.wsThemePresenter = wsThemePresenter;
    }

    public String getShortName() {
        return shortName;
    }

    public View getView() {
        return view;
    }

    public void init(final ToolSelectorItemView view) {
        this.view = view;
        toolSelector.addTool(this);
        wsThemePresenter.addOnThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                setTheme(oldTheme, newTheme);
            }
        });
    }

    public void setGroupShortName(final String groupShortName) {
        final StateToken token = new StateToken(groupShortName, getShortName(), null, null);
        view.setLink(longName, token.toString());
    }

    public void setSelected(final boolean selected) {
        view.setSelected(selected);
    }

    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    private void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        view.setTheme(oldTheme, newTheme);
    }
}
