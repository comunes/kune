package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.rooms.ui.RoomUserListView;
import org.ourproject.kune.chat.client.rooms.ui.RoomUser.UserType;

import com.calclab.gwtjsjac.client.mandioca.XmppRoom;

public interface Room {
    void setSubject(String subject);

    void addUser(String userAlias, UserType moderador);

    void addMessage(String userAlias, String body);

    void addInfoMessage(String message);

    void addDelimiter(String date);

    void setHandler(XmppRoom handler);

    XmppRoom getHandler();

    String getSessionUserAlias();

    void clearSavedInput();

    void saveInput(String inputText);

    String getSavedInput();

    String getSubject();

    RoomUserListView getUsersListView();

}
