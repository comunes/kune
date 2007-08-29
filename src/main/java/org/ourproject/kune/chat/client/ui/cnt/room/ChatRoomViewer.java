package org.ourproject.kune.chat.client.ui.cnt.room;

import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.View;

public interface ChatRoomViewer {
    public View getView();

    public void setState(ChatState state);
}
