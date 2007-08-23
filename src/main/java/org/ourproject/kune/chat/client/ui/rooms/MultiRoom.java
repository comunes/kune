package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.platf.client.dto.RoomDTO;

public interface MultiRoom {

    public MultiRoomPanel getView();

    void createRoom(RoomDTO room, String userAlias);

}
