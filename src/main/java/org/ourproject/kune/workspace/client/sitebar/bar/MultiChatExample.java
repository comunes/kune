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

package org.ourproject.kune.workspace.client.sitebar.bar;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.rooms.MultiRoomListener;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomUser;

public class MultiChatExample {

    public static void show() {
        final MultiRoom rooms = ChatFactory.createChatMultiRoom(new MultiRoomListener() {
            public void onSendMessage(Room room, String message) {
                room.addMessage(room.getSessionAlias(), message);
            }

            public void onCloseRoom(Room room) {
            }
        });
        rooms.show();

        Room room1 = rooms.createRoom("chat1@talks.localhost", "luther.b", RoomUser.PARTICIPANT);
        room1.setSubject("Welcome to chat1, today topic: Cultural issues in Brazil");
        room1.addUser("otro usuario", RoomUser.MODERADOR);
        room1.addMessage("luther.b", "Mensaje de test en room1");

        Room room2 = rooms.createRoom("chat2@talks.localhost", "luther", RoomUser.PARTICIPANT);
        room2.setSubject("Welcome to this room: we are talking today about 2009 meeting");
        room2.addUser("luther", RoomUser.MODERADOR);
        room2.addMessage("luther", "Mensaje de test en room2");
        room2.addInfoMessage("Mensaje de evento en room2");
        room2.addDelimiter("17:35");

    }
}
