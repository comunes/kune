package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

class RoomUsersPresenter implements RoomUsers {
    private RoomUsersPanel view;
    private Map users;

    public void init(RoomUsersPanel view) {
	this.view = view;
	users = new HashMap();
    }

    public void add(RoomUser user) {
	int index = view.addUser(user);
	users.put(user.getAlias(), new Integer(index));
    }

    public void del(RoomUser user) {
	Integer index = (Integer) users.get(user.getAlias());
	view.delUser(index.intValue());
    }

    public RoomUsersPanel getView() {
	return view;
    }

}
