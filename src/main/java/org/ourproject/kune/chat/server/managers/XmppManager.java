
package org.ourproject.kune.chat.server.managers;

public interface XmppManager {

    ChatConnection login(String userName, String password, String resource);

    void disconnect(ChatConnection connection);

    Room createRoom(ChatConnection connection, String roomName, String ownerAlias);

    Room joinRoom(ChatConnection connection, String roomName, String alias);

    void sendMessage(Room room, String message);

    void destroyRoom(ChatConnection conn, String roomName);

}
