/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.server.manager;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.ourproject.kune.server.xmpp.ServerGroupChat;


public interface XmppManager {
    public XMPPConnection connectAndLogin(String session, String login, String pass) throws XMPPException;

    public XMPPConnection getConnection(String session) throws XMPPException;

    public void createRoom(String session, String roomName, String owner) throws XMPPException;

    public ServerGroupChat getGroupChat(String session, String roomName) throws XMPPException;

    public void joinRoom(String session, String roomName, String nick) throws XMPPException;

    public void leaveRoom(String session, String roomName) throws XMPPException;

    public void changeSubject(String subject);

    public void sendMessage(String session, String roomName, String body) throws XMPPException;

}
