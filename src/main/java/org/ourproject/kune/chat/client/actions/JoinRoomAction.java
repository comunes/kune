package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatProvider;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomUser;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.JoinRoomActionParams;
import org.ourproject.kune.platf.client.services.Kune;

import com.calclab.gwtjsjac.client.XmppMessage;
import com.calclab.gwtjsjac.client.XmppMessageListener;
import com.calclab.gwtjsjac.client.mandioca.rooms.RoomPresenceListener;
import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

public class JoinRoomAction implements Action<JoinRoomActionParams> {
    private final ChatProvider provider;

    public JoinRoomAction(final ChatProvider provider) {
        this.provider = provider;
    }

    public void execute(final JoinRoomActionParams params) {
        joinRoom(params);
    }

    private void joinRoom(final JoinRoomActionParams params) {
        final Room room = params.getRoom();
        // FIXME and params.userAlias() ????
        room.addInfoMessage(Kune.I18N.t("Connecting to the room..."));
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
                // FIXME: Put correct user type
                room.addUser(alias, RoomUser.MODERADOR);
                room.addInfoMessage(Kune.I18N.t("[%s] came online", alias));
            }

            public void onUserLeft(final String alias) {
                room.removeUser(alias);
                room.addInfoMessage(Kune.I18N.t("[%s] went offline", alias));
            }
        });
        room.setHandler(handler);

        room.addInfoMessage(Kune.I18N.t("You have entered the room!"));
    }

}
