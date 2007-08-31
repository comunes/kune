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

package org.ourproject.kune.chat.client;

import java.util.Date;

import to.tipit.gwtlib.FireLog;

import com.calclab.gwtjsjac.client.Debugger;
import com.calclab.gwtjsjac.client.XmppHttpBindingConnection;
import com.calclab.gwtjsjac.client.XmppPresence;
import com.calclab.gwtjsjac.client.XmppUser;
import com.calclab.gwtjsjac.client.mandioca.XmppRoom;
import com.calclab.gwtjsjac.client.mandioca.XmppSession;

class ChatEngineXmpp implements ChatEngine {
    private final XmppHttpBindingConnection connection;
    private XmppSession session;
    private final ChatState state;

    public ChatEngineXmpp(final ChatState state) {
	this.state = state;
	connection = new XmppHttpBindingConnection(state.httpBase, 2000);
	Debugger.debug(connection, new FirebugLoggerOutput());
    }

    public ChatState getState() {
	return state;
    }

    public void login(final String chatName, final String chatPassword) {
	FireLog.debug("LOGIN CHAT: " + chatName + "[" + chatPassword + "]");
	state.user = new XmppUser(state.domain, chatName, chatPassword);
	state.user.resource = "kuneClient" + new Date().getTime();
	session = new XmppSession(connection, state.user);
	session.login();
	// FIXME: hardcoded
	session.sendPresence(XmppPresence.CHAT, ":: ready ::");
    }

    public void logout() {
	if (session != null) {
	    session.logout();
	}
    }

    public XmppRoom joinRoom(final String roomName, final String userAlias) {
	return session.joinRoom(state.roomHost, roomName, userAlias);
    }

}
