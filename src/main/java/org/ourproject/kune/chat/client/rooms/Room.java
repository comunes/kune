package org.ourproject.kune.chat.client.rooms;

import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.View;

import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public interface Room {

    String getName();

    void setSubject(String subject);

    // FIXME: creo que sería más logico cambiarlo por addUser(RoomUser user);
    void addUser(String userAlias, UserType moderador);

    void addMessage(String userAlias, String body);

    void addInfoMessage(String message);

    void addDelimiter(String date);

    void setHandler(XmppRoom handler);

    XmppRoom getHandler();

    String getSessionAlias();

    void clearSavedInput();

    void saveInput(String inputText);

    String getSavedInput();

    String getSubject();

    RoomUserListView getUsersListView();

    View getView();

    boolean isReady();

    void removeUser(String alias);

    void activate();

    UserType getUserType();

    void setUserType(UserType userType);

    void setUserAlias(String userAlias);

    void setRoomName(String roomName);

}
