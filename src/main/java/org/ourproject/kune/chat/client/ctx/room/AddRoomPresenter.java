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
 */
package org.ourproject.kune.chat.client.ctx.room;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;

public class AddRoomPresenter implements AddRoom {

    private AddRoomView view;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final StateManager stateManager;

    public AddRoomPresenter(Session session, final Provider<ContentServiceAsync> contentServiceProvider,
            StateManager stateManager) {
        this.session = session;
        this.contentServiceProvider = contentServiceProvider;
        this.stateManager = stateManager;
    }

    public void addRoom(String roomName) {
        Site.showProgressProcessing();
        final String groupShortName = session.getCurrentState().getGroup().getShortName();
        contentServiceProvider.get().addRoom(session.getUserHash(),
                session.getContainerState().getRootContainer().getStateToken(), groupShortName + "-" + roomName,
                new AsyncCallbackSimple<StateContainerDTO>() {
                    public void onSuccess(final StateContainerDTO state) {
                        stateManager.setRetrievedState(state);
                        Site.hideProgress();
                    }
                });
    }

    public View getView() {
        return view;
    }

    public void init(AddRoomView view) {
        this.view = view;
    }

    public void show() {
        view.show();
    }
}
