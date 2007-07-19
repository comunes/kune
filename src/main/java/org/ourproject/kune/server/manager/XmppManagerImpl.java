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

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ourproject.kune.server.log.Logger;
import org.ourproject.kune.server.xmpp.ServerGroupChat;


public class XmppManagerImpl implements XmppManager {

    private Log log = Logger.getLogger();

    private ConcurrentHashMap<String, XMPPConnection> connections = new ConcurrentHashMap<String, XMPPConnection>();

    private ConcurrentHashMap<String, ConcurrentHashMap<String, ServerGroupChat>> mucs = new ConcurrentHashMap<String, ConcurrentHashMap<String, ServerGroupChat>>();

    public XMPPConnection connectAndLogin(final String session, final String login, String pass) throws XMPPException {
        ConnectionConfiguration config = new ConnectionConfiguration(
                "ourproject.org", 5222);

        XMPPConnection conn = new XMPPConnection(config);
        if (connections.contains(session)) {
            conn = connections.get(session);
            if (conn.isConnected() & conn.isAuthenticated()) {
                return conn;
            }
            else {
                connections.remove(session);
            }
        }
        try {
            conn.connect();
            conn.login(login, pass, session, true);
            log.info("Connecting with user: " + login);
            connections.put(session, conn);
            conn.addPacketListener(new PacketListener() {
                public void processPacket(Packet packet) {
                    // TODO Auto-generated method stub
                }

            }, new PacketFilter() {
                public boolean accept(Packet packet) {
                    // TODO Auto-generated method stub
                    return false;
                }});
            conn.addConnectionListener(new ConnectionListener() {

                public void connectionClosed() {
                    String message = "Connection closed";
                    log.debug("Connection listener: " + login + "/" + session + ":" + message);
                }

                public void connectionClosedOnError(Exception e) {
                    String message = "Connection closed on error";
                    log.debug("Connection listener: " + login + "/" + session + ":" + message);
                }

                public void reconnectingIn(int seconds) {
                    String message = "Reconnection In";
                    log.debug("Connection listener: " + login + "/" + session + ":" + message);
                }

                public void reconnectionFailed(Exception e) {
                    String message = "Reconnection Failed";
                    log.debug("Connection listener: " + login + "/" + session + ":" + message);
                }

                public void reconnectionSuccessful() {
                    String message = "Reconnection Successful";
                    log.debug("Connection listener: " + login + "/" + session + ":" + message);
                }});
            return conn;
        } catch (XMPPException e) {
            log.debug("Error connecting user " + login + ", error: " + e.getXMPPError());
            throw e;
        }

    }

    public XMPPConnection getConnection(String session) throws XMPPException {
        XMPPConnection conn = null;
        if (connections.containsKey(session)) {
            conn = connections.get(session);
            if (!conn.isConnected() | !conn.isAuthenticated()) {
                conn.connect();
                throw new XMPPException("User not connected or authenticated");
            }
        }
        else {
            throw new XMPPException("User not connected");
        }
        return conn;
    }

    ServerGroupChat getGroupChatRef(String session, String roomName) {
        ConcurrentHashMap<String, ServerGroupChat> mucsSession = mucs.get(session);
        if (mucsSession == null) return null; // no Mucs for this session
        return mucsSession.get(roomName);
    }

    void setGroupChatRef(String session, String roomName, ServerGroupChat chat) {
        ConcurrentHashMap<String, ServerGroupChat> mucsSession = mucs.get(session);
        if (mucsSession == null) {
            mucsSession = new ConcurrentHashMap<String, ServerGroupChat>();
        }
        mucsSession.put(roomName, chat);
        mucs.put(session, mucsSession);
    }

    void removeGroupChatRef(String session, String roomName) {
        ConcurrentHashMap<String, ServerGroupChat> mucsSession = mucs.get(session);
        if (mucsSession == null) {
            mucsSession = new ConcurrentHashMap<String, ServerGroupChat>();
        }
        mucsSession.remove(roomName);
        mucs.put(session, mucsSession);
    }

    public void createRoom(String session, String roomName, String owner) throws XMPPException {
        ServerGroupChat muc = getGroupChatRef(session, roomName);
        if (muc == null) {
            XMPPConnection conn = getConnection(session);
            muc = new ServerGroupChat(conn, roomName, owner);
            setGroupChatRef(session, roomName, muc);
        }
        else {
            // Room already exists
        }
    }

    public ServerGroupChat getGroupChat(String session, String roomName) throws XMPPException {
        ServerGroupChat muc = getGroupChatRef(session, roomName);
        if (muc == null) {
            // try to get chat from server
            XMPPConnection conn = getConnection(session);
            muc = new ServerGroupChat(conn, roomName);
            setGroupChatRef(session, roomName, muc);
        }
        return muc;
    }

    public void joinRoom(String session, String roomName, String nick) throws XMPPException {
        ServerGroupChat muc = getGroupChat(session, roomName);
        muc.join(session, nick);
    }

    public void leaveRoom(String session, String roomName) throws XMPPException {
        ServerGroupChat muc = getGroupChat(session, roomName);
        muc.leave();
        removeGroupChatRef(session, roomName);
    }

    public void sendMessage(String session, String roomName, String body) throws XMPPException {
        ServerGroupChat muc = getGroupChat(session, roomName);
        muc.sendMessage(body);
    }

    public void changeSubject(String subject) {
        ConnectionConfiguration config = new ConnectionConfiguration(
                "ourproject.org", 5222);
        XMPPConnection conn = new XMPPConnection(config);
        try {
            conn.connect();
            conn.login("test2345", "test2345");
        } catch (XMPPException e) {
            e.printStackTrace();
            System.err.println("Error : " + e.getXMPPError());
        }

        MultiUserChat muc = new MultiUserChat(conn, "kune"
                + "@conference.ourproject.org");

        try {
            // User joins the new room
            // The room service will decide the amount of history to send
            muc.join("test2345");
        } catch (XMPPException e) {
            e.printStackTrace();
            System.err.println("Error : " + e.getXMPPError());
        }

        try {
            muc.changeSubject(subject);
        } catch (XMPPException e) {
            e.printStackTrace();
            System.err.println("Error : " + e.getXMPPError());
        }
    }
}
