/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.options.EntityOptions;
import org.ourproject.kune.workspace.client.themes.WsBackManager;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public abstract class EntityOptionsPublicSpaceConfPresenter implements EntityOptionsPublicSpaceConf {
    private EntityOptionsPublicSpaceConfView view;
    private final EntityOptions entityOptions;
    private final Provider<GroupServiceAsync> groupService;
    private final Session session;
    private final WsBackManager backManager;
    private final StateManager stateManager;

    protected EntityOptionsPublicSpaceConfPresenter(final Session session, final StateManager stateManager,
            final EntityOptions entityOptions, final Provider<GroupServiceAsync> groupService,
            final WsBackManager backManager) {
        this.session = session;
        this.stateManager = stateManager;
        this.entityOptions = entityOptions;
        this.groupService = groupService;
        this.backManager = backManager;
    }

    public void clearBackImage() {
        groupService.get().clearGroupBackImage(session.getUserHash(), session.getCurrentStateToken(),
                new AsyncCallbackSimple<GroupDTO>() {
                    public void onSuccess(final GroupDTO result) {
                        view.clearBackImage();
                        backManager.clearBackImage();
                    }
                });
    }

    public View getView() {
        return view;
    }

    public void init(final EntityOptionsPublicSpaceConfView view) {
        this.view = view;
        entityOptions.addTab(view);
        setBackImage(session.getContainerState().getGroup().getGroupBackImage());
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                final ContentSimpleDTO backImage = state.getGroup().getGroupBackImage();
                setBackImage(backImage);
            }
        });
        backManager.addSetBackImage(new Listener<StateToken>() {
            public void onEvent(final StateToken token) {
                view.setBackImage(token);
            }
        });
        backManager.addBackClear(new Listener0() {
            public void onEvent() {
                view.clearBackImage();
            }
        });
    }

    private void setBackImage(final ContentSimpleDTO backImage) {
        if (backImage == null) {
            view.clearBackImage();
        } else {
            view.setBackImage(backImage.getStateToken());
        }
    }
}
