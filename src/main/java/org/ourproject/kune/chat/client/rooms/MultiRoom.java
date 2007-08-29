package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.rooms.ui.RoomUser;
import org.ourproject.kune.chat.client.rooms.ui.RoomUser.UserType;

public interface MultiRoom {
    public void show();

    Room createRoom(String roomName, String userAlias, UserType userType);

}
