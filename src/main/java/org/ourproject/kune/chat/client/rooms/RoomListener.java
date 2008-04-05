package org.ourproject.kune.chat.client.rooms;

public interface RoomListener {

    void onRoomReady(Room room);

    void onMessageReceived(Room room);

}
