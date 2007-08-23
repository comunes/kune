package org.ourproject.kune.chat.client.ui.rooms;

import org.ourproject.kune.platf.client.dto.RoomDTO;

import com.google.gwt.user.client.ui.HTML;

public class RoomPresenter {
    private RoomPanel roomView;
    private final RoomDTO room;
    private String input;
    private final String sessionUserAlias;

    public RoomPresenter(final RoomDTO room, final String sessionUserAlias) {
	this.room = room;
	this.sessionUserAlias = sessionUserAlias;
	this.input = "";
    }

    public void init(final RoomPanel roomView) {
	this.roomView = roomView;
    }

    public void addMessage(final String userAlias, final HTML message) {
	// userChat.add(new HTML("<span style=\"color: " + user.getColor() + ";
	// font-weight: bold;\">" + user.getNickName() + "</span>:&nbsp;"));

	roomView.addMessage(new HTML("<b>" + userAlias + "</b>: " + message.getHTML()));
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
	roomView.setRoomName(room.getName());

    }

    public String getSubject() {
	return room.getSubject();
    }

}
