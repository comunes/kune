
package org.ourproject.kune.chat.client.ctx;

import org.ourproject.kune.chat.client.ChatFactory;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;

class ChatContextComponents {

    private RoomsAdmin roomsAdmin;

    public ChatContextComponents(final ChatContextPresenter chatContextPresenter) {
    }

    public RoomsAdmin getRoomsAdmin() {
        if (roomsAdmin == null) {
            roomsAdmin = ChatFactory.createRoomsAdmin();
        }
        return roomsAdmin;
    }

}
