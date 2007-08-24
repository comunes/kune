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

    private Map roomPresenters;

    private Map roomUsersPresenters;

    private Map roomUsersPanels;

    public void init(final MultiRoomPanel view) {
	this.view = view;
	roomPresenters = new HashMap();
	roomUsersPresenters = new HashMap();
	roomPanels = new HashMap();
	roomUsersPanels = new HashMap();
    }

    public MultiRoomPanel getView() {
	return view;
    }

    public RoomPresenter createRoom(final RoomDTO room, final String userAlias) {
	String roomName = room.getName();

	RoomPresenter presenter = new RoomPresenter(room, userAlias);
	String panelId = view.createRoom(presenter);

	presenter.setRoomName();
	roomPresenters.put(roomName, presenter);
	roomPanels.put(panelId, presenter);
	currentRoom = presenter;

	view.setSubject(room.getSubject());

	RoomUsers roomUsers = view.createRoomUsersPanel();
	roomUsersPresenters.put(roomName, roomUsers);
	int roomUsersIndex = view.addRoomUsersPanel((RoomUsersPresenter) roomUsers);
	roomUsersPanels.put(currentRoom, new Integer(roomUsersIndex));
	return currentRoom;
    }

    public void join(RoomDTO room, String alias, int roomUserType) {
	RoomUser user = getPresenter(room).addUser(alias, roomUserType);
	getUsersPresenter(room).add(user);
    }

    public void addTimeDelimiter(RoomDTO room, String datetime) {
	getPresenter(room).addDelimiter(datetime);
    }

    protected void onSend() {
	// TODO: Call to xmpp, meawhile:
	String userAlias = currentRoom.getSessionUserAlias();

	currentRoom.addMessage(userAlias, formatter(view.getInputText()));
	currentRoom.clearSavedInput();
	view.clearTextArea();
	// view.sendBtnEnable(false);

	// if (key == KeyboardListener.KEY_ENTER) {
	// if (mod == KeyboardListener.MODIFIER_CTRL) {
	// view.insertReturnInInput();
	// } else {
	// view.addMessage(currentRoom, currentUserAlias, view.getInputText());
	// view.clearTextArea();
	// }
	// }
    }

    protected void activateRoom(final String panelId) {
	RoomPresenter nextRoom = getRoomFromPanelId(panelId);
	currentRoom.saveInput(view.getInputText());
	currentRoom = nextRoom;
	view.setInputText(currentRoom.getSavedInput());
	view.setSubject(currentRoom.getSubject());
	Integer index = (Integer) roomUsersPanels.get(currentRoom);
	view.activeUsersPanel(index.intValue());
    }

    protected void onNoRooms() {
	// TODO
	view.hide();
    }

    protected void closeRoom(final String panelId) {
	RoomPresenter room = getRoomFromPanelId(panelId);
	room.doClose();
    }

    private RoomPresenter getPresenter(RoomDTO room) {
	return (RoomPresenter) roomPresenters.get(room.getName());
    }

    private RoomUsersPresenter getUsersPresenter(RoomDTO room) {
	return (RoomUsersPresenter) roomUsersPresenters.get(room.getName());
    }

    private RoomPresenter getRoomFromPanelId(final String panelId) {
	RoomPresenter nextRoom = ((RoomPresenter) roomPanels.get(panelId));
	return nextRoom;
    }

    private HTML formatter(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");
	// FIXME:
	// message = message.replaceAll(":)",
	// RoomImages.App.getInstance().smile().getHTML());
	message = message.replaceAll(":D", RoomImages.App.getInstance().grin().getHTML());
	// message = message.replaceAll(":(",
	// RoomImages.App.getInstance().sad().getHTML());
	message = message.replaceAll(":P", RoomImages.App.getInstance().tongue().getHTML());
	return new HTML(message);
    }

}
