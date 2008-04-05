package org.ourproject.kune.chat.client.rooms;

public interface RoomUserList {

    public RoomUserListView getView();

    void add(RoomUser user);

    void remove(RoomUser user);

}
