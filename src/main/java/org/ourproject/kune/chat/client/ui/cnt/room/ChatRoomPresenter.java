package org.ourproject.kune.chat.client.ui.cnt.room;

import org.ourproject.kune.platf.client.View;

public class ChatRoomPresenter implements ChatRoom {

    private final ChatRoomView view;

    public ChatRoomPresenter(ChatRoomView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

}
