package org.ourproject.kune.chat.client.rooms;

public interface MultiRoomListener {

    void onSendMessage(Room room, String message);

    void onCloseRoom(Room room);

}
