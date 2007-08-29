/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.ui.cnt;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomViewerListener;
import org.ourproject.kune.chat.client.ui.rooms.MultiRoom;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class ChatContentPresenter implements ChatContent, ChatRoomViewerListener {

    private final WorkspaceDeckView view;
    private final Components components;
    private final ChatEngine engine;

    public ChatContentPresenter(final ChatEngine engine, final WorkspaceDeckView view) {
	this.engine = engine;
	this.view = view;
	this.components = new Components(this);
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setState(final StateDTO state) {
	view.show(components.getChatRoomViewer().getView());
    }

    public void onEnterRoom() {
	MultiRoom rooms = components.getRooms();

    }

}
