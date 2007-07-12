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

import java.util.Hashtable;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;


public class XmppManagerImpl implements XmppManager {
    private Hashtable<String, XMPPConnection> connections = new Hashtable<String, XMPPConnection>();

    public XMPPConnection connectAndLogin(String login, String pass) {
        ConnectionConfiguration config = new ConnectionConfiguration(
                "ourproject.org", 5223);
        XMPPConnection conn = new XMPPConnection(config);
        if (connections.contains(login)) {
            conn = connections.get(login);
            if (conn.isConnected() & conn.isAuthenticated()) {
                return conn;
            }
            else {
                connections.remove(login);
            }
        }
        try {
            conn.connect();
            conn.login(login, pass);
            System.out.println("Connecting : " + login);
            connections.put(login, conn);
            return conn;
        } catch (XMPPException e) {
            e.printStackTrace();
            System.err.println("Error : " + e.getXMPPError());
            return null;
        }
    }

    public XMPPConnection getConnection(String login) throws XMPPException {
        XMPPConnection conn = null;
        if (connections.contains(login)) {
            conn = connections.get(login);
            if (!conn.isConnected() | !conn.isAuthenticated()) {
                throw new XMPPException("User not connected or authenticated");
            }
        }
        else {
            throw new XMPPException("User not connected");
        }
        return conn;
    }

    public void createRoom(String Owner, String RoomName) {
        //try {
            //XMPPConnection conn = getConnection(Owner);
        //}

//      MultiUserChat muc = new MultiUserChat(conn, RoomName
//              + "@conference.ourproject.org");
//
//      try {
//          // Create the room
//          muc.create(Owner);
//          // Send an empty room configuration form which indicates that we
//          // want
//          // an instant room
//          muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
//      } catch (XMPPException e) {
//          e.printStackTrace();
//          System.err.println("Error : " + e.getXMPPError());
//      }
    }

    public void joinRoom(String RoomName, String UserName) {
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

        MultiUserChat muc = new MultiUserChat(conn, RoomName
                + "@conference.ourproject.org");

        try {
            // User joins the new room
            // The room service will decide the amount of history to send
            muc.join(UserName);
        } catch (XMPPException e) {
            e.printStackTrace();
            System.err.println("Error : " + e.getXMPPError());
        }

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
