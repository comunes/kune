package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.ui.rooms.RoomUser.UserType;

public interface MultiRoom {
    public void show();

    Room createRoom(String roomName, String userAlias, UserType userType);

}
