package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.rooms.ui.RoomUser.UserType;

public interface Room {
    void setSubject(String subject);

    void addUser(String userAlias, UserType moderador);

    void addMessage(String userAlias, String body);

    void addInfoMessage(String message);

    void addDelimiter(String date);

}
