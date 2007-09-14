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

package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;

public class MultiRoomPresenter implements MultiRoom, RoomListener {
    private MultiRoomView view;
    private Room currentRoom;
    private final MultiRoomListener listener;
    private boolean closeAllConfirmed;

    public MultiRoomPresenter(final MultiRoomListener listener) {
	this.listener = listener;
    }

    public void init(final MultiRoomView view) {
	this.view = view;
	closeAllConfirmed = false;
    }

    public Room createRoom(final String roomName, final String userAlias, final UserType type) {
	Room room = ChatFactory.createRoom(this, roomName, userAlias, type);
	view.addRoom(room);
	view.addRoomUsersPanel(room.getUsersListView());
	currentRoom = room;
	return currentRoom;
    }

    public void show() {
	view.show();
	closeAllConfirmed = false;
    }

    public void onSend() {
	listener.onSendMessage(currentRoom, view.getInputText());
	// view.setSendEnabled(false);
	view.clearInputText();
    }

    public void onNoRooms() {
	// TODO
	view.hideRooms();
    }

    public void closeRoom(final RoomPresenter room) {
	room.doClose();
    }

    public void activateRoom(final Room nextRoom) {
	currentRoom.saveInput(view.getInputText());
	view.setSendEnabled(nextRoom.isReady());
	view.setInputText(nextRoom.getSavedInput());
	view.setSubject(nextRoom.getSubject());
	view.showUserList(nextRoom.getUsersListView());
	currentRoom = nextRoom;
    }

    public void onRoomReady(final Room room) {
	if (currentRoom == room) {
	    view.setSendEnabled(true);
	}
    }

    public void onMessageReceived(final Room room) {
	// TODO: hacer algo!! mostrar un mensaje, abrir la room... lo que sea!!
    }

    public void closeAllRooms() {
	// TODO xmpp: Close all the rooms;
	closeAllConfirmed = true;
	view.hideRooms();
    }

    public void onCloseAllNotConfirmed() {
	closeAllConfirmed = false;
    }

    public boolean isCloseAllConfirmed() {
	return closeAllConfirmed;
    }
}
