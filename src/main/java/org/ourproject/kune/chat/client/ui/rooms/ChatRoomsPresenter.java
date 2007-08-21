package org.ourproject.kune.chat.client.ui.rooms;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.KeyboardListener;

public class ChatRoomsPresenter {

    private ChatRoomsDialog view;

    private String currentRoom;

    private Map userAlias;

    private String currentUserAlias;

    public void init(final ChatRoomsDialog view) {
	this.view = view;
	userAlias = new HashMap();
    }

    public void onRoomChanged(final String roomId) {
	currentRoom = roomId;
	currentUserAlias = ((String) userAlias.get(roomId));
    }

    public void onSend(final String message) {
	view.addMessage(currentRoom, currentUserAlias, view.getInputText());
	view.clearTextArea();
	view.sendBtnEnable(false);
    }

    public void onInput(final char key, final int mod) {
	GWT.log("" + key, null);
	if (view.sendBtnIsDisabled()) {
	    view.sendBtnEnable(true);
	}
	if (key == KeyboardListener.KEY_ENTER) {
	    if (mod == KeyboardListener.MODIFIER_CTRL) {
		view.insertReturnInInput();
	    } else {
		view.addMessage(currentRoom, currentUserAlias, view.getInputText());
		view.clearTextArea();
	    }
	}
    }

    public void onRoomSelected(final String room) {
	GWT.log("Current room: " + room, null);
	currentRoom = room;
    }

    public void onNoRooms() {
	// TODO
    }

}
