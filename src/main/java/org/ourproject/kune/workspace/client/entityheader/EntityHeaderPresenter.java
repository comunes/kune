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
package org.ourproject.kune.workspace.client.entityheader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;

import com.calclab.suco.client.events.Listener2;

public class EntityHeaderPresenter implements EntityHeader {
    private static final int MEDIUM_NAME_LIMIT = 90;
    private static final int LARGE_NAME_LIMIT = 20;

    private EntityHeaderView view;
    private final Session session;

    public EntityHeaderPresenter(final StateManager stateManager, final WsThemeManager theme, final Session session) {
        this.session = session;

        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(final String oldGroup, final String newGroup) {
                setGroupLogo(session.getCurrentState().getGroup());
            }
        });
        theme.addOnThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
    }

    public void addAction(final GuiActionDescrip descriptor) {
        view.addAction(descriptor);
    }

    public void addWidget(final View widget) {
        view.addWidget(widget);
    }

    public void init(final EntityHeaderView view) {
        this.view = view;
    }

    public void refreshGroupLogo() {
        setGroupLogo(session.getCurrentState().getGroup());
    }

    public void reloadGroupLogoImage() {
        view.reloadImage(session.getCurrentState().getGroup());
    }

    void setGroupLogo(final GroupDTO group) {
        final ContentSimpleDTO groupFullLogo = group.getGroupFullLogo();
        if (groupFullLogo != null) {
            view.setFullLogo(groupFullLogo.getStateToken(), true);
        } else if (group.hasLogo()) {
            setLogoText(group.getLongName());
            view.setLogoImage(group.getStateToken());
            view.setLogoImageVisible(true);
        } else {
            setLogoText(group.getLongName());
            if (group.isPersonal()) {
                view.showDefUserLogo();
                view.setLogoImageVisible(true);
            } else {
                view.setLogoImageVisible(false);
            }
        }
    }

    void setLogoText(final String name) {
        final int length = name.length();
        if (length <= LARGE_NAME_LIMIT) {
            view.setLargeFont();
        } else if (length <= MEDIUM_NAME_LIMIT) {
            view.setMediumFont();
        } else {
            view.setSmallFont();
        }
        view.setLogoText(name);
    }
}
