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
 */package org.ourproject.kune.workspace.client.sitebar.sitepublic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;

public class SitePublicSpaceLinkPresenter implements SitePublicSpaceLink {

    private SitePublicSpaceLinkView view;

    public SitePublicSpaceLinkPresenter(final StateManager stateManager) {
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContainerDTO) {
                    setState((StateContainerDTO) state);
                } else {
                    view.detach();
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final SitePublicSpaceLinkView view) {
        this.view = view;
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }

    private void setState(final StateContainerDTO state) {
        final StateToken token = state.getStateToken();
        if (state.getAccessLists().getViewers().getMode().equals(GroupListDTO.EVERYONE)) {
            final String publicUrl = token.getPublicUrl();
            view.setContentGotoPublicUrl(publicUrl);
            view.setContentPublic(true);
        } else {
            view.setContentPublic(false);
        }
        view.attach();
        view.setVisible(true);
    }

}
