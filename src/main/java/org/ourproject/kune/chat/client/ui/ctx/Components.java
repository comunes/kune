package org.ourproject.kune.chat.client.ui.ctx;

import org.ourproject.kune.chat.client.ui.ChatFactory;
import org.ourproject.kune.chat.client.ui.ctx.rooms.RoomsAdmin;

class Components {

    private RoomsAdmin roomsAdmin;

    public RoomsAdmin getRoomsAdmin() {
	if (roomsAdmin == null) {
	    roomsAdmin = ChatFactory.createRoomsAdmin();
	}
	return roomsAdmin;
    }

}
