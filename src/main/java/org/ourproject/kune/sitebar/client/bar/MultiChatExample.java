package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.chat.client.rooms.MultiRoom;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.ui.MultiRoomListener;
import org.ourproject.kune.chat.client.rooms.ui.RoomUser;
import org.ourproject.kune.chat.client.ui.ChatFactory;

public class MultiChatExample {

    public static void show() {
	final MultiRoom rooms = ChatFactory.createChatMultiRoom(new MultiRoomListener() {
	    public void onSendMessage(Room room, String message) {
		room.addMessage(room.getSessionUserAlias(), message);
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
