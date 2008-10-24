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
package org.ourproject.kune.chat.client.ctx;

import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;

import com.calclab.suco.client.ioc.Provider;

public class ChatContextPresenter implements ChatContext {

    private final WorkspaceDeckView view;
    private final Provider<RoomsAdmin> roomAdminsProvider;

    public ChatContextPresenter(final WorkspaceDeckView view, final Provider<RoomsAdmin> roomAdminsProvider) {
        this.view = view;
        this.roomAdminsProvider = roomAdminsProvider;
    }

    public View getView() {
        return view;
    }

    public void setState(final StateContainerDTO state) {
        final RoomsAdmin rooms = roomAdminsProvider.get();
        rooms.showRoom(state.getStateToken(), state.getContainer(), state.getContainerRights());
        view.show(rooms.getView());
    }
}
