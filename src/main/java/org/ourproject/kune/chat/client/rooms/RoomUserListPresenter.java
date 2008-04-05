package org.ourproject.kune.chat.client.rooms;

import java.util.ArrayList;

import org.ourproject.kune.chat.client.rooms.ui.RoomUserListPanel;

public class RoomUserListPresenter implements RoomUserList {
    private RoomUserListPanel view;
    private final ArrayList<RoomUser> users;

    public RoomUserListPresenter() {
        users = new ArrayList<RoomUser>();
    }

    public void init(final RoomUserListPanel view) {
        this.view = view;
    }

    public void add(final RoomUser user) {
        users.add(user);
        view.addUser(user);
    }

    public void remove(final RoomUser user) {
        view.delUser(users.indexOf(user));
    }

    public RoomUserListView getView() {
        return view;
    }

}
