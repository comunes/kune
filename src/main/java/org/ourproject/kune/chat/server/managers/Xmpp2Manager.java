package org.ourproject.kune.chat.server.managers;

public interface Xmpp2Manager {
    ChatConnection login(String userName, String password);

    Room createRoom(ChatConnection connection, String roomName, String ownerAlias);

    Room joinRoom(ChatConnection connection, String roomName, String alias);

    void sendMessage(Room room, String message);

}
