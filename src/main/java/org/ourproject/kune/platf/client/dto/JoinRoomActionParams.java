package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.chat.client.rooms.Room;

public class JoinRoomActionParams {

    private final Room room;
    private final String userAlias;

    public JoinRoomActionParams(final Room room, final String userAlias) {
        this.room = room;
        this.userAlias = userAlias;
    }

    public Room getRoom() {
        return room;
    }

    public String getUserAlias() {
        return userAlias;
    }

}
