package org.ourproject.kune.chat.client.rooms.ui;

import java.util.ArrayList;

class RoomUserListPresenter implements RoomUserList {
    private RoomUserListPanel view;
    private final ArrayList users;

    public RoomUserListPresenter() {
	users = new ArrayList();
    }

    public void init(final RoomUserListPanel view) {
	this.view = view;
    }

    public void add(final RoomUser user) {
	users.add(user);
	view.addUser(user);
    }

    public void del(final RoomUser user) {
	view.delUser(users.indexOf(user));
    }

    public RoomUserListView getView() {
	return view;
    }

}
