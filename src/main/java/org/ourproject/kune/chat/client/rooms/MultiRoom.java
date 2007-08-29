package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.rooms.ui.RoomUser.UserType;

public interface MultiRoom {
    public void show();

    /**
     * @param roomName
     * @param userAlias
     * @param userType
     * @return
     */

    Room createRoom(String roomName, String userAlias, UserType userType);

    public void activateRoom(Room room);

}
