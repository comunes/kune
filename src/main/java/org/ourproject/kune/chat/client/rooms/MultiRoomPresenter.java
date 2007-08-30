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

import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.chat.client.ui.ChatFactory;

public class MultiRoomPresenter implements MultiRoom {
    private MultiRoomView view;
    private Room currentRoom;
    private final MultiRoomListener listener;

    public MultiRoomPresenter(final MultiRoomListener listener) {
	this.listener = listener;
    }

    public void init(final MultiRoomView view) {
	this.view = view;
    }

    public Room createRoom(final String roomName, final String userAlias, final UserType type) {
	Room room = ChatFactory.createRoom(roomName, userAlias, type);
	view.createRoom(room);
	room.addUser(userAlias, type);
	view.addRoomUsersPanel(room.getUsersListView());
	currentRoom = room;
	return currentRoom;
    }

    public void show() {
	view.show();
    }

    public void onSend() {
	listener.onSendMessage(currentRoom, view.getInputText());
    }

    public void onSend(final int key, final boolean isCtrl) {
	if (key == 13 && !isCtrl) {
	    onSend();
	}
    }

    // TODO: vicente, mira esto
    // protected void onOLDSend() {
    // // TODO: Call to xmpp, meawhile:
    // String userAlias = currentRoom.getSessionUserAlias();
    //
    // currentRoom.addMessage(userAlias, view.getInputText());
    // currentRoom.clearSavedInput();
    // view.clearInputText();
    // // view.sendBtnEnable(false);
    //
    // // CUIDADO: DETALLE DE VISTA
    // // if (key == KeyboardListener.KEY_ENTER) {
    // // if (mod == KeyboardListener.MODIFIER_CTRL) {
    // // view.insertReturnInInput();
    // // } else {
    // // view.addMessage(currentRoom, currentUserAlias, view.getInputText());
    // // view.clearTextArea();
    // // }
    // // }
    // }

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
}
