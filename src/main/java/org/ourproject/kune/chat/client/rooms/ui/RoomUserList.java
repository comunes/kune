package org.ourproject.kune.chat.client.rooms.ui;

interface RoomUserList {
    public RoomUserListView getView();

    void add(RoomUser user);

    void del(RoomUser user);
}
