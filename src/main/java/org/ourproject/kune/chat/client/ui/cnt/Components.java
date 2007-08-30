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

import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.chat.client.ui.cnt.room.ChatRoom;

class Components {

    private final ChatContentPresenter owner;
    private MultiRoom multiRoom;
    private ChatRoom chatRoom;
    private ChatInfo chatInfo;

    public Components(final ChatContentPresenter owner) {
	this.owner = owner;
    }

    public ChatRoom getChatRoom() {
	if (chatRoom == null) {
	    chatRoom = ChatFactory.createChatRoomViewer(owner);
	}
	return chatRoom;
    }

    public MultiRoom getRooms() {
	if (multiRoom == null) {
	    multiRoom = ChatFactory.createChatMultiRoom(owner);
	}
	return multiRoom;
    }

    public ChatInfo getChatInfo() {
	if (chatInfo == null) {
	    chatInfo = ChatFactory.createChatInfo(owner);
	}
	return chatInfo;
    }

}
