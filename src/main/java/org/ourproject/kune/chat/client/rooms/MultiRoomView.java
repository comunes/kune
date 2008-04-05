
package org.ourproject.kune.chat.client.rooms;

public interface MultiRoomView {

    public static final int STATUS_ONLINE = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_INVISIBLE = 3;
    public static final int STATUS_XA = 4;
    public static final int STATUS_AWAY = 5;
    public static final int STATUS_MESSAGE = 6;

    void addRoomUsersPanel(RoomUserListView view);

    void show();

    String getInputText();

    void addRoom(Room room);

    void setInputText(String savedInput);

    void setSubject(String subject);

    void showUserList(RoomUserListView usersListView);

    void setSendEnabled(boolean enabled);

    void clearInputText();

    void highlightRoom(Room room);

    void setSubjectEditable(boolean editable);

    void setStatus(int statusOnline);

    void addPresenceBuddy(String buddyName, String title, int status);

    void removePresenceBuddy(String buddyName);

}
