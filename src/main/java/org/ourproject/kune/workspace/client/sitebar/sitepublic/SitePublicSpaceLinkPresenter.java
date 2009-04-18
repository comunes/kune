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
package org.ourproject.kune.workspace.client.sitebar.sitepublic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.StateTokenUtils;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener;

public class SitePublicSpaceLinkPresenter implements SitePublicSpaceLink {

    private SitePublicSpaceLinkView view;
    private final StateTokenUtils stateTokenUtils;

    public SitePublicSpaceLinkPresenter(final StateManager stateManager, final StateTokenUtils stateTokenUtils) {
        this.stateTokenUtils = stateTokenUtils;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final SitePublicSpaceLinkView view) {
        this.view = view;
    }

    public void setState(final StateAbstractDTO state) {
        if (state instanceof StateContainerDTO) {
            final StateToken token = state.getStateToken();
            if (((StateContainerDTO) state).getAccessLists().getViewers().getMode().equals(GroupListDTO.EVERYONE)) {
                final String publicUrl = stateTokenUtils.getPublicUrl(token);
                view.setContentGotoPublicUrl(publicUrl);
                if (state instanceof StateContentDTO) {
                    StateContentDTO content = (StateContentDTO) state;
                    if (content.getStatus().equals(ContentStatusDTO.publishedOnline)) {
                        view.setContentPublic(true);
                    } else {
                        view.setContentPublic(false);
                    }
                } else {
                    view.setContentPublic(true);
                }
            } else {
                view.setContentPublic(false);
            }
            view.attach();
            view.setVisible(true);
        } else {
            view.detach();
        }
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }

}
