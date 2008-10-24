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
package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class EntityLogoPresenter implements EntityLogo {
    int GROUP_MEDIUM_NAME_LIMIT_SIZE = 90;
    int GROUP_LARGE_NAME_LIMIT_SIZE = 20;

    private EntityLogoView view;
    private final Session session;
    private final Provider<GroupServiceAsync> groupServiceProvider;

    public EntityLogoPresenter(final StateManager stateManager, final WsThemePresenter theme, final Session session,
            Provider<GroupServiceAsync> groupServiceProvider) {
        this.session = session;
        this.groupServiceProvider = groupServiceProvider;

        stateManager.onGroupChanged(new Listener2<GroupDTO, GroupDTO>() {
            public void onEvent(final GroupDTO oldGroup, final GroupDTO newGroup) {
                setGroupLogo(newGroup);
            }
        });
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                final boolean isAdmin = state.getGroupRights().isAdministrable();
                if (state.getGroup().hasLogo()) {
                    view.setChangeYourLogoText();
                    view.setSetYourLogoVisible(isAdmin);
                } else {
                    view.setPutYourLogoText();
                    view.setSetYourLogoVisible(isAdmin);
                }
            }
        });
        theme.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
    }

    public void init(final EntityLogoView view) {
        this.view = view;
    }

    public void refreshGroupLogo() {
        setGroupLogo(session.getCurrentState().getGroup());
    }

    public void reloadGroupLogo() {
        reloadGroupLogo(session.getCurrentStateToken());
    }

    public void reloadGroupLogo(StateToken groupToken) {
        groupServiceProvider.get().getGroup(session.getUserHash(), groupToken, new AsyncCallbackSimple<GroupDTO>() {
            public void onSuccess(GroupDTO group) {
                StateAbstractDTO currentState = session.getCurrentState();
                if (currentState.getGroup().getShortName().equals(group.getShortName())) {
                    // only if we are in the
                    // same group
                    view.reloadImage(group);
                    currentState.setGroup(group);
                    setGroupLogo(group);
                }
            }
        });
    }

    private void setGroupLogo(final GroupDTO group) {
        final ContentSimpleDTO groupFullLogo = group.getGroupFullLogo();
        if (groupFullLogo != null) {
            view.setFullLogo(groupFullLogo.getStateToken(), true);
        } else if (group.hasLogo()) {
            view.setLogoImage(group.getStateToken());
            setLogoText(group.getLongName());
            view.setLogoImageVisible(true);
        } else {
            setLogoText(group.getLongName());
            view.setLogoImageVisible(false);
        }
    }

    private void setLogoText(String name) {
        int length = name.length();
        if (length <= GROUP_LARGE_NAME_LIMIT_SIZE) {
            view.setLargeFont();
        } else if (length <= GROUP_MEDIUM_NAME_LIMIT_SIZE) {
            view.setMediumFont();
        } else {
            view.setSmallFont();
        }
        view.setLogoText(name);
    }
}
