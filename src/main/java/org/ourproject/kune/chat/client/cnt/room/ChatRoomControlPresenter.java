package org.ourproject.kune.chat.client.cnt.room;

import org.ourproject.kune.platf.client.View;

public class ChatRoomControlPresenter implements ChatRoomControl {

    private final ChatRoomControlView view;

    public ChatRoomControlPresenter(final ChatRoomControlView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

}
