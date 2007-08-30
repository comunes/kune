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

import java.util.HashMap;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.chat.client.ChatEvents;
import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.rooms.MultiRoomListener;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomUser;
import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.calclab.gwtjsjac.client.mandioca.XmppRoom;

public class ChatContentPresenter implements ChatContent, ChatRoomListener, MultiRoomListener {

    private final WorkspaceDeckView view;
    private final Components components;
    private final ChatEngine engine;
    private final HashMap roomNamesToTooms;

    public ChatContentPresenter(final ChatEngine engine, final WorkspaceDeckView view) {
	this.engine = engine;
	this.view = view;
	this.components = new Components(this);
	this.roomNamesToTooms = new HashMap();
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setState(final StateDTO state) {
	ChatRoom viewer = components.getChatRoom();
	viewer.setState(engine.getState());
	view.show(viewer.getView());
    }

    public void onReconnect() {
	engine.reconnect();
    }

    public void onEnterRoom() {
	MultiRoom rooms = components.getRooms();
	Room room = getRoom("room name", "user alias", RoomUser.VISITOR);
	rooms.activateRoom(room);
	rooms.show();
    }

    private Room getRoom(final String roomName, final String userAlias, final UserType userType) {
	Room room = (Room) roomNamesToTooms.get(roomName);
	if (room == null) {
	    room = createRoom(roomName, userAlias, userType);
	    roomNamesToTooms.put(roomName, room);
	}
	return room;
    }

    private Room createRoom(final String roomName, final String userAlias, final UserType userType) {
	MultiRoom rooms = components.getRooms();
	final Room room = rooms.createRoom(roomName, userAlias, userType);
	Dispatcher.App.instance.fireDeferred(ChatEvents.JOIN_ROOM, room, null);
	return room;
    }

    public void onSendMessage(final Room room, final String message) {
	XmppRoom handler = room.getHandler();
	if (handler != null) {
	    handler.sendMessage(message);
	}
    }
}
