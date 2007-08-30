package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

import com.calclab.gwtjsjac.client.XmppMessage;
import com.calclab.gwtjsjac.client.XmppMessageListener;
import com.calclab.gwtjsjac.client.mandioca.XmppRoom;
import com.google.gwt.core.client.GWT;

public class JoinRoomAction extends WorkspaceAction {
    private final ChatEngine engine;

    public JoinRoomAction(final ChatEngine chatEngine) {
	this.engine = chatEngine;
    }

    private void joinRoom(final Room room) {
	// i18n
	room.addInfoMessage("connecting to the room...");
	GWT.log("a ver!!!", null);
	// FIXME: hardcoded
	XmppRoom handler = engine.joinRoom("kune", "kuneClientAlias");
	handler.addMessageListener(new XmppMessageListener() {
	    public void onMessageReceived(final XmppMessage message) {
		room.addMessage(message.getFrom(), message.getBody());
	    }

	    public void onMessageSent(final XmppMessage message) {
		room.addMessage(message.getFrom(), message.getBody());
	    }
	});
	room.setHandler(handler);

	// i18n
	room.addInfoMessage("you have entered!");
    }

    public void execute(final Object value, final Object extra) {
	joinRoom((Room) value);
    }

}
