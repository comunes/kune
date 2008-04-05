package org.ourproject.kune.chat.client;

import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public interface ChatEngine {
    void login(String chatName, String chatPassword);

    ChatState getState();

    XmppRoom joinRoom(String roomName, String userAlias);

    void logout();
}
