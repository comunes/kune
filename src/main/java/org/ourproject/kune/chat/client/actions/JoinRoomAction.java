/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.platf.client.services.Kune;
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

    public void execute(final Object value, final Object extra, final Services services) {
        joinRoom((Room) value, (String) extra);
    }

}
