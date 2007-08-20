package org.ourproject.kune.chat.client.ui.rooms;

public interface ChatRoomsDialogView {

    void createRoom(String roomId, String longName);

    void closeRoom(String roomId);

    void addUser(String roomId, String userAlias, String color);

    void removeUser(String roomId, String userAlias);

    void addMessage(String roomId, String userAlias, String message);

    void clearMessages(String roomId);

    void clearTextArea();

    void show();

    void hide();

}
