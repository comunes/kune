package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatProvider;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;

import com.calclab.gwtjsjac.client.XmppMessage;
import com.calclab.gwtjsjac.client.XmppMessageListener;
import com.calclab.gwtjsjac.client.mandioca.XmppRoom;

public class JoinRoomAction implements Action {
    private final ChatProvider provider;

    public JoinRoomAction(final ChatProvider provider) {
	this.provider = provider;
    }

    private void joinRoom(final Room room, final String userAlias) {
	// i18n
	room.addInfoMessage("connecting to the room...");
	XmppRoom handler = provider.getChat().joinRoom(room.getName(), room.getSessionAlias());
	handler.addMessageListener(new XmppMessageListener() {
	    public void onMessageReceived(final XmppMessage message) {
		room.addMessage(userAlias, message.getBody());
	    }

	    public void onMessageSent(final XmppMessage message) {
		room.addMessage(userAlias, message.getBody());
	    }
	});
	room.setHandler(handler);

	// i18n
	room.addInfoMessage("you have entered the room!");
    }

    public void execute(final Object value, final Object extra, final Services services) {
	joinRoom((Room) value, (String) extra);
    }

}
