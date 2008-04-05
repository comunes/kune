package org.ourproject.kune.chat.client.rooms;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.chat.client.rooms.RoomUser.UserType;
import org.ourproject.kune.platf.client.View;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public class RoomPresenter implements Room {

    private final static String[] USERCOLORS = { "green", "navy", "black", "grey", "olive", "teal", "blue", "lime",
            "purple", "fuchsia", "maroon", "red" };

    private int currentColor;
    private RoomView view;
    private String input;
    private String subject;
    private String userAlias;
    // FIXME: this in RoomUserList?
    private final Map<String, RoomUser> users;
    private String roomName;
    private RoomUserList userList;
    private XmppRoom handler;
    private final RoomListener listener;
    private boolean closeConfirmed;
    private UserType userType;

    public RoomPresenter(final RoomListener listener) {
        this.listener = listener;
        this.input = "";
        this.currentColor = 0;
        this.subject = "Subject: " + roomName;
        users = new HashMap<String, RoomUser>();
    }

    public void setRoomName(final String roomName) {
        this.roomName = roomName;
        view.showRoomName(roomName);
    }

    public void setUserAlias(final String userAlias) {
        this.userAlias = userAlias;
    }

    public void setUserType(final UserType userType) {
        this.userType = userType;
    }

    public void setUserList(final RoomUserList userList) {
        this.userList = userList;
    }

    public void init(final RoomView view) {
        this.view = view;
        closeConfirmed = false;
    }

    public View getView() {
        return view;
    }

    public void addMessage(final String userAlias, final String message) {
        String userColor;

        RoomUser user = users.get(userAlias);
        if (user != null) {
            userColor = user.getColor();
        } else {
            Log.debug("User " + userAlias + " not in our users list");
            userColor = "black";
        }
        view.showMessage(userAlias, userColor, message);
        listener.onMessageReceived(this);
    }

    public void addInfoMessage(final String message) {
        view.showInfoMessage(message);
    }

    public void addUser(final String alias, final UserType type) {
        RoomUser user = new RoomUser(alias, getNextColor(), type);
        getUsersList().add(user);
        users.put(alias, user);
    }

    public void removeUser(final String alias) {
        getUsersList().remove(users.get(alias));
    }

    public void addDelimiter(final String datetime) {
        view.showDelimiter(datetime);
    }

    public void clearSavedInput() {
        saveInput(null);
    }

    public String getSessionAlias() {
        return userAlias;
    }

    public void saveInput(final String inputText) {
        input = inputText;
    }

    public String getSavedInput() {
        return input;
    }

    protected void doClose() {
        handler.logout();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getName() {
        return roomName;
    }

    private String getNextColor() {
        String color = USERCOLORS[currentColor++];
        if (currentColor >= USERCOLORS.length) {
            currentColor = 0;
        }
        return color;
    }

    public RoomUserList getUsersList() {
        return userList;
    }

    public RoomUserListView getUsersListView() {
        return userList.getView();
    }

    public void setHandler(final XmppRoom handler) {
        this.handler = handler;
        listener.onRoomReady(this);
    }

    public boolean isReady() {
        return handler != null;
    }

    public XmppRoom getHandler() {
        return handler;
    }

    public void onCloseConfirmed() {
        closeConfirmed = true;
    }

    public void onCloseNotConfirmed() {
        closeConfirmed = false;
    }

    public boolean isCloseConfirmed() {
        return closeConfirmed;
    }

    public void activate() {
        view.scrollDown();
    }

    public UserType getUserType() {
        return userType;
    }

}
