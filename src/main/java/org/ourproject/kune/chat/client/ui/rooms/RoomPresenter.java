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
	roomView.addMessage(new HTML("<b>" + userAlias + "</b>: " + message.getHTML()));
    }

    public void crearSavedInput() {
	input = null;
    }

    public String getSessionUserAlias() {
	return sessionUserAlias;
    }

}
