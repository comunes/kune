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

import org.ourproject.kune.platf.client.dto.RoomDTO;

import com.google.gwt.user.client.ui.HTML;

public class MultiRoomPresenter implements MultiRoom {

    private MultiRoomPanel view;

    private RoomPresenter currentRoom;

    private Map roomPanels;

    public void init(final MultiRoomPanel view) {
	this.view = view;
	roomPanels = new HashMap();
    }

    public MultiRoomPanel getView() {
	return view;
    }

    public void createRoom(final RoomDTO room, final String userAlias) {
	RoomPresenter presenter = new RoomPresenter(room, userAlias);
	String panelId = view.createRoom(presenter);

	presenter.setRoomName();

	roomPanels.put(panelId, presenter);
	currentRoom = presenter;
    }

    protected void onSend(final String message) {
	// TODO: Call to xmpp, meawhile:
	String userAlias = currentRoom.getSessionUserAlias();

	currentRoom.addMessage(userAlias, formatter(message));
	currentRoom.crearSavedInput();
	view.clearTextArea();
	view.sendBtnEnable(false);

	// if (key == KeyboardListener.KEY_ENTER) {
	// if (mod == KeyboardListener.MODIFIER_CTRL) {
	// view.insertReturnInInput();
	// } else {
	// view.addMessage(currentRoom, currentUserAlias, view.getInputText());
	// view.clearTextArea();
	// }
	// }
    }

    protected void onNoRooms() {
	// TODO
	view.hide();
    }

    private HTML formatter(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");
	return new HTML(message);
    }

    protected void closeRoom(final String panelId) {
	RoomPresenter room = getRoomFromPanelId(panelId);
	room.doClose();
    }

    protected void activateRoom(final String panelId) {
	RoomPresenter nextRoom = getRoomFromPanelId(panelId);
	currentRoom.saveInput(view.getInputText());
	currentRoom = nextRoom;
	view.setInputText(currentRoom.getSavedInput());
    }

    private RoomPresenter getRoomFromPanelId(final String panelId) {
	RoomPresenter nextRoom = ((RoomPresenter) roomPanels.get(panelId));
	return nextRoom;
    }

}
