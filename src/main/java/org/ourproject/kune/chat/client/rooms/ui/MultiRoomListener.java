package org.ourproject.kune.chat.client.rooms.ui;

import org.ourproject.kune.chat.client.rooms.Room;

public interface MultiRoomListener {
    void onSendMessage(Room room, String message);
}
