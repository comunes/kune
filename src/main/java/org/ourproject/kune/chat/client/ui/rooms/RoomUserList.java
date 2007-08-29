package org.ourproject.kune.chat.client.ui.rooms;

interface RoomUserList {
    public RoomUserListView getView();

    void add(RoomUser user);

    void del(RoomUser user);
}
