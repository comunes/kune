package org.ourproject.kune.chat.client;

import com.calclab.gwtjsjac.client.mandioca.XmppRoom;

public interface ChatEngine {
    void login(String chatName, String chatPassword);

    ChatState getState();

    void reconnect();

    XmppRoom joinRoom(String roomName, String userAlias);
}
