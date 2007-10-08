/*
 *
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

package org.ourproject.kune.chat.server.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ourproject.kune.platf.server.properties.ChatProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XmppManagerDefault implements XmppManager {

    private final ChatProperties chatProperties;

    @Inject
    public XmppManagerDefault(final ChatProperties chatProperties) {
	this.chatProperties = chatProperties;
    }

    public ChatConnection login(final String userName, final String password, final String resource) {
	ConnectionConfiguration config = new ConnectionConfiguration(getServerName(), 5222);
	XMPPConnection conn = new XMPPConnection(config);
	try {
	    conn.connect();
	    conn.login(userName, password, resource, true);
	    return new XmppConnection(userName, conn);
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    private String getServerName() {
	return chatProperties.getDomain();
    }

    private String getRoomName(final String room) {
	return room + "@" + chatProperties.getRoomHost();
    }

    public Room createRoom(final ChatConnection conn, final String roomName, final String alias) {
	XmppConnection xConn = (XmppConnection) conn;
	MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
	try {
	    muc.create(alias);
	    configure(muc);
	    XmppRoom room = new XmppRoom(muc, alias);
	    muc.addMessageListener(room);
	    return room;
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    public void destroyRoom(final ChatConnection conn, final String roomName) {
	XmppConnection xConn = (XmppConnection) conn;
	MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
	try {
	    muc.destroy("Room removed by kune server", "");
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    public Room joinRoom(final ChatConnection connection, final String roomName, final String alias) {
	XmppConnection xConn = (XmppConnection) connection;
	MultiUserChat muc = new MultiUserChat(xConn.getConn(), getRoomName(roomName));
	try {
	    muc.join(alias);
	    // configure(muc);
	    XmppRoom room = new XmppRoom(muc, alias);
	    muc.addMessageListener(room);
	    return room;
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    public void sendMessage(final Room room, final String body) {
	XmppRoom xAccess = (XmppRoom) room;
	MultiUserChat muc = xAccess.getMuc();
	Message message = muc.createMessage();
	message.setBody(body);
	message.setFrom(muc.getNickname());
	try {
	    muc.sendMessage(body);
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    private void configure(final MultiUserChat muc) throws XMPPException {
	Form form = muc.getConfigurationForm();
	Form answer = form.createAnswerForm();

	for (Iterator fields = form.getFields(); fields.hasNext();) {
	    FormField field = (FormField) fields.next();
	    String type = field.getType();
	    if (isVisible(type) && isNotEmpty(field) && isNotList(type)) {
		List values = new ArrayList();
		for (Iterator it = field.getValues(); it.hasNext();) {
		    values.add(it.next());
		}
		answer.setAnswer(field.getVariable(), values);
	    }
	}
	answer.setAnswer("muc#roomconfig_persistentroom", true);
	muc.sendConfigurationForm(answer);
    }

    private boolean isNotEmpty(final FormField field) {
	return field.getVariable() != null;
    }

    private boolean isVisible(final String type) {
	return !FormField.TYPE_HIDDEN.equals(type);
    }

    private boolean isNotList(final String type) {
	return !FormField.TYPE_JID_MULTI.equals(type) && !FormField.TYPE_LIST_MULTI.equals(type)
		&& !FormField.TYPE_LIST_SINGLE.equals(type) && !isVisible(type);
    }

    public void disconnect(final ChatConnection connection) {
	XmppConnection xConn = (XmppConnection) connection;
	xConn.getConn().disconnect();

    }
}
