package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.platf.client.dto.RoomDTO;

public interface MultiRoom {

    RoomPresenter createRoom(RoomDTO room, String userAlias);

    public void join(RoomDTO room, String alias, int roomUserType);

    void addTimeDelimiter(RoomDTO room, String datetime);

    public void show();

}
