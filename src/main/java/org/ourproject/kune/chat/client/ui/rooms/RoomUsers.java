package org.ourproject.kune.chat.client.ui.rooms;

public interface RoomUsers {
    public RoomUsersPanel getView();

    void add(RoomUser user);

    void del(RoomUser user);
}
