package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.ui.rooms.RoomUser.UserType;

public class RoomPresenter implements Room {

    private final static String[] USERCOLORS = { "green", "navy", "black", "grey", "olive", "teal", "blue", "lime",
	    "purple", "fuchsia", "maroon", "red" };

    private int currentColor;

    private RoomPanel view;
    private String input;
    private String subject;
    private final String sessionUserAlias;
    private final Map users;
    private final String roomName;

    private RoomUserListPresenter userListPresenter;

    public RoomPresenter(final String roomName, final String userAlias, final UserType userType) {
	this.roomName = roomName;
	this.sessionUserAlias = userAlias;
	this.input = "";
	this.currentColor = 0;
	users = new HashMap();
    }

    public void init(final RoomPanel view) {
	this.view = view;
	view.showRoomName(roomName);
    }

    public void addMessage(final String userAlias, final String message) {
	RoomUser user = (RoomUser) users.get(userAlias);
	if (user == null) {
	    throw new RuntimeException("Trying to send a chat message with a user not in this room");
	}
	view.addMessage(user.getAlias(), user.getColor(), message);
    }

    public void addInfoMessage(final String message) {
	view.addEventMessage(message);
    }

    public void addUser(final String alias, final UserType type) {
	RoomUser user = new RoomUser(alias, getNextColor(), type);
	getUsersList().add(user);
	users.put(alias, user);
    }

    public void addDelimiter(final String datetime) {
	view.addTimeDelimiter(datetime);
    }

    public void clearSavedInput() {
	input = null;
    }

    public String getSessionUserAlias() {
	return sessionUserAlias;
    }

    protected void saveInput(final String inputText) {
	input = inputText;
    }

    public String getSavedInput() {
	return input;
    }

    protected void doClose() {
	// TODO: xmpp: send bye in room
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(final String subject) {
	this.subject = subject;
    }

    public String getRoomName() {
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
	if (userListPresenter == null) {
	    userListPresenter = new RoomUserListPresenter();
	    RoomUserListPanel panel = new RoomUserListPanel();
	    userListPresenter.init(panel);
	}
	return userListPresenter;
    }
}
