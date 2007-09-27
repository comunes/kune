package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatProvider;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomUser;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;

import com.calclab.gwtjsjac.client.XmppMessage;
import com.calclab.gwtjsjac.client.XmppMessageListener;
import com.calclab.gwtjsjac.client.mandioca.rooms.RoomPresenceListener;
import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public class JoinRoomAction implements Action {
    private final ChatProvider provider;

    public JoinRoomAction(final ChatProvider provider) {
	this.provider = provider;
    }

    private void joinRoom(final Room room, final String userAlias) {
	// i18n
	room.addInfoMessage("Connecting to the room...");
	XmppRoom handler = provider.getChat().joinRoom(room.getName(), room.getSessionAlias());
	handler.addMessageListener(new XmppMessageListener() {
	    public void onMessageReceived(final XmppMessage message) {
		String fromPrefix = room.getName() + "@" + provider.getChat().getState().roomHost.toString() + "/";
		String fromAbrev = message.getFrom();
		fromAbrev = fromAbrev.replaceAll(fromPrefix, "");
		room.addMessage(fromAbrev, message.getBody());
	    }

	    public void onMessageSent(final XmppMessage message) {
	    }
	});
	handler.addRoomPresenceListener(new RoomPresenceListener() {
	    public void onUserEntered(final String alias, final String status) {
		room.addUser(alias, RoomUser.MODERADOR);
		// i18n
		room.addInfoMessage(alias + " came online");
	    }

	    public void onUserLeft(final String alias) {
		room.removeUser(alias);
		// i18n
		room.addInfoMessage(alias + " went offline");
	    }
	});
	room.setHandler(handler);

	// i18n
	room.addInfoMessage("You have entered the room!");
    }

    public void execute(final Object value, final Object extra, final Services services) {
	joinRoom((Room) value, (String) extra);
    }

}
