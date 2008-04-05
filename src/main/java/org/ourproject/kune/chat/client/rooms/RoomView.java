package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.platf.client.View;

public interface RoomView extends View {

    void showRoomName(String roomName);

    void showMessage(String alias, String color, String message);

    void showInfoMessage(String message);

    void showDelimiter(String datetime);

    void scrollDown();

}
