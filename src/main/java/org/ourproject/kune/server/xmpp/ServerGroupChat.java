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
package org.ourproject.kune.server.xmpp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.server.log.Logger;
import org.ourproject.kune.server.servlet.EventQueueCont;

public class ServerGroupChat {

    private Log log = Logger.getLogger();

    private MultiUserChat muc;

    private String session;

    //  Some listeners
    //    InvitationRejectionListener
    //    SubjectUpdatedListener

    public ServerGroupChat(XMPPConnection conn, String roomName, String owner) throws XMPPException {
        // FIXME use properties
        muc = new MultiUserChat(conn, roomName + "@conference.ourproject.org");
        try {
            // Create the room
            muc.create(owner);
            // Send an empty room configuration form which indicates that we want an instant room
            muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
            muc.join(owner);
            muc.leave();
            muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
        } catch (XMPPException e) {
            if (e.getMessage() == "Creation failed - Missing acknowledge of room creation.") {
                // Room already exists
            } else {
                log.debug("Error creating room");
                throw e;
            }
        }
        setListeners();
    }

    public ServerGroupChat(XMPPConnection conn, String roomName) throws XMPPException {
        // FIXME use properties
        muc = new MultiUserChat(conn, roomName + "@conference.ourproject.org");
        setListeners();
    }

    private void setListeners() {
        muc.addMessageListener(new PacketListener() {
            public void processPacket(Packet packet) {
                processPacketInSessions(packet);
            }
        });
    }

    public void join(String session, String nick) throws XMPPException {
        try {
            // User joins the room
            // The room service will decide the amount of history to send
            muc.join(nick);
            this.session = session;
        } catch (XMPPException e) {
            log.debug("Error joining room " + getRoomName() + " with nick: " + nick + ": " + e.getXMPPError());
            throw e;
        }
    }

    public void leave() {
        muc.leave();
    }

    public MultiUserChat getMuc() {
        return muc;
    }

    public String getRoomName() {
        return muc.getRoom().replace("@conference.ourproject.org", "");
    }

    public void sendMessage(String body) throws XMPPException {
        Message message = muc.createMessage();
        message.setBody(body);
        message.setFrom(muc.getNickname());
        muc.sendMessage(message);
    }

    //  create
    //  configure
    //  join(String nick)
    //  join(String nick, String pass)
    //  invite(String nick, String reason)
    //  changeSubject(String subject)

    public void processPacketInSessions(final Packet packet) {
        if (packet instanceof Presence) {
            final Presence presence = (Presence) packet;
            final SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String time = formatter.format(new Date());
            if (presence.getType() == Presence.Type.unavailable) {
                EventQueueCont.getInstance().addEvent(session,
                        new Event("org.ourproject.kune.muc.room" + "." + this.getRoomName(),
                                "<span style='font-style: italic;'>tests" + time + "---" +
                                muc.getNickname() + "went offline" + "</spam>"));
               }
            else if (presence.getType() == Presence.Type.available) {
                EventQueueCont.getInstance().addEvent(session,
                        new Event("org.ourproject.kune.muc.room" + "." + this.getRoomName(),
                                "<span style='font-style: italic;'>tests" + time + "---" +
                                muc.getNickname() + "came online" + "</spam>"));
            }
        }
        else if (packet instanceof Message) {
            final Message message = (Message) packet;
            EventQueueCont.getInstance().addEvent(session,
                    new Event("org.ourproject.kune.muc.room" + "." + this.getRoomName(),
                            message.getFrom() + ":" + message.getBody()));
        }
    }

}