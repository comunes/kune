package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.platf.client.dto.RoomDTO;

public class RoomPresenter {

    private final static String[] USERCOLORS = { "green", "navy", "black", "grey", "olive", "teal", "blue", "lime",
	    "purple", "fuchsia", "maroon", "red" };

    private int currentColor;

    private RoomPanel view;
    private final RoomDTO room;
    private String input;
    private final String sessionUserAlias;

    private final Map users;

    public RoomPresenter(final RoomDTO room, final String sessionUserAlias) {
	this.room = room;
	this.sessionUserAlias = sessionUserAlias;
	this.input = "";
	this.currentColor = 0;
	users = new HashMap();
    }

    public void init(final RoomPanel view) {
	this.view = view;
    }

    public void addMessage(final String userAlias, final String message) {
	RoomUser user = (RoomUser) users.get(userAlias);
	if (user == null) {
	    throw new RuntimeException("Trying to send a chat message with a user not in this room");
	}
	view.addMessage(user.getAlias(), user.getColor(), message);
    }

    public void addEventMessage(final String message) {
	view.addEventMessage(message);
    }

    public RoomUser addUser(final String alias, final int type) {
	RoomUser user = new RoomUser(alias, getNextColor(), type);
	users.put(alias, user);
	return user;
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

    public void setRoomName() {
	view.setRoomName(room.getName());

    }

    public String getSubject() {
	return room.getSubject();
    }

    private String getNextColor() {
	String color = USERCOLORS[currentColor++];
	if (currentColor >= USERCOLORS.length) {
	    currentColor = 0;
	}
	return color;
    }
}
