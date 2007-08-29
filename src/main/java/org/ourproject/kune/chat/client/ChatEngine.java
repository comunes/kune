package org.ourproject.kune.chat.client;

public interface ChatEngine {
    void login(String chatName, String chatPassword);

    ChatState getState();

    void reconnect();
}
