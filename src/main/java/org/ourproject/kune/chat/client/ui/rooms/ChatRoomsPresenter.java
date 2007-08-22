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

package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.KeyboardListener;

public class ChatRoomsPresenter {

    private ChatRoomsDialog view;

    private String currentRoom;

    private Map userAlias;

    private String currentUserAlias;

    public void init(final ChatRoomsDialog view) {
	this.view = view;
	userAlias = new HashMap();
    }

    public void onRoomChanged(final String roomId) {
	currentRoom = roomId;
	currentUserAlias = ((String) userAlias.get(roomId));
    }

    public void onSend(final String message) {
	view.addMessage(currentRoom, currentUserAlias, view.getInputText());
	view.clearTextArea();
	view.sendBtnEnable(false);
    }

    public void onInput(final char key, final int mod) {
	GWT.log("" + key, null);
	if (view.sendBtnIsDisabled()) {
	    view.sendBtnEnable(true);
	}
	if (key == KeyboardListener.KEY_ENTER) {
	    if (mod == KeyboardListener.MODIFIER_CTRL) {
		view.insertReturnInInput();
	    } else {
		view.addMessage(currentRoom, currentUserAlias, view.getInputText());
		view.clearTextArea();
	    }
	}
    }

    public void onRoomSelected(final String room) {
	GWT.log("Current room: " + room, null);
	currentRoom = room;
    }

    public void onNoRooms() {
	// TODO
    }

}
