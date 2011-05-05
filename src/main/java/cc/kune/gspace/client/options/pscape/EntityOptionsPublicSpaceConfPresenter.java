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
package cc.kune.gspace.client.options.pscape;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.gspace.client.options.EntityOptions;
import cc.kune.gspace.client.style.ClearBackImageEvent;
import cc.kune.gspace.client.style.GSpaceBackManager;
import cc.kune.gspace.client.style.SetBackImageEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

public abstract class EntityOptionsPublicSpaceConfPresenter implements EntityOptionsPublicSpaceConf {
    private final GSpaceBackManager backManager;
    private final EntityOptions entityOptions;
    private final EventBus eventBus;
    private final Provider<GroupServiceAsync> groupService;
    private final Session session;
    private final StateManager stateManager;
    private EntityOptionsPublicSpaceConfView view;

    protected EntityOptionsPublicSpaceConfPresenter(final EventBus eventBus, final Session session,
            final StateManager stateManager, final EntityOptions entityOptions,
            final Provider<GroupServiceAsync> groupService, final GSpaceBackManager backManager) {
        this.eventBus = eventBus;
        this.session = session;
        this.stateManager = stateManager;
        this.entityOptions = entityOptions;
        this.groupService = groupService;
        this.backManager = backManager;
    }

    public void clearBackImage() {
        groupService.get().clearGroupBackImage(session.getUserHash(), session.getCurrentStateToken(),
                new AsyncCallbackSimple<GroupDTO>() {
                    @Override
                    public void onSuccess(final GroupDTO result) {
                        view.clearBackImage();
                        backManager.clearBackImage();
                    }
                });
    }

    public IsWidget getView() {
        return view;
    }

    public void init(final EntityOptionsPublicSpaceConfView view) {
        this.view = view;
        entityOptions.addTab(view, view.getTabTitle());
        setBackImage(session.getCurrentState().getGroup().getGroupBackImage());
        view.getClearBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                clearBackImage();
            }
        });
        stateManager.onStateChanged(true, new StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                final StateAbstractDTO state = event.getState();
                final ContentSimpleDTO backImage = state.getGroup().getGroupBackImage();
                setBackImage(backImage);
            }
        });
        eventBus.addHandler(SetBackImageEvent.getType(), new SetBackImageEvent.SetBackImageHandler() {
            @Override
            public void onSetBackImage(final SetBackImageEvent event) {
                view.setBackImage(event.getToken());
            }
        });
        eventBus.addHandler(ClearBackImageEvent.getType(), new ClearBackImageEvent.ClearBackImageHandler() {
            @Override
            public void onClearBackImage(final ClearBackImageEvent event) {
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