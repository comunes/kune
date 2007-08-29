package org.ourproject.kune.chat.client.rooms.ui;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.ui.RoomUser.UserType;

import com.calclab.gwtjsjac.client.mandioca.XmppRoom;

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

    private final RoomUserList userList;

    private XmppRoom handler;

    public RoomPresenter(final String roomName, final String userAlias, final UserType userType,
	    final RoomUserList userList) {
	this.roomName = roomName;
	this.sessionUserAlias = userAlias;
	this.userList = userList;
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

    // TODO: ¿no bastaría con saveInput(null)?
    public void clearSavedInput() {
	input = null;
    }

    public String getSessionUserAlias() {
	return sessionUserAlias;
    }

    public void saveInput(final String inputText) {
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
	return userList;
    }

    public RoomUserListView getUsersListView() {
	return userList.getView();
    }

    public void setHandler(final XmppRoom handler) {
	this.handler = handler;
    }

    public XmppRoom getHandler() {
	return handler;
    }

}
