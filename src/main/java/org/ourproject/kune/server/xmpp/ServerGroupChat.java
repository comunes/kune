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
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;
import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.server.log.Logger;
import org.ourproject.kune.server.servlet.EventQueueCont;

public class ServerGroupChat {

    private Log log = Logger.getLogger();

    private MultiUserChat muc;

    final SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

    private String session;

    final public String server = "conference.ourproject.org";
    //final public String server = "conference.jabber.org";


    //  Some listeners
    //    InvitationRejectionListener
    //    SubjectUpdatedListener

    public ServerGroupChat(XMPPConnection conn, String roomName, String owner) throws XMPPException {
        // FIXME use properties
        muc = new MultiUserChat(conn, roomName + "@" + server);
        try {
            // Create the room
            muc.create(owner);
            // Send an empty room configuration form which indicates that we want an instant room
            Form formGet = muc.getConfigurationForm();
            log.debug("Form instructions: " + formGet.getInstructions());
            for (Iterator it = formGet.getFields(); it.hasNext();) {
                FormField formField = (FormField) it.next();
                log.debug("Field label: " + formField.getLabel());
                log.debug("Field variable: " + formField.getVariable());
                log.debug("Field type: " + formField.getType());
                log.debug("Field desc: " + formField.getDescription());
                log.debug("Field options: " + formField.getOptions().toString());
                log.debug("Field values: " + formField.getValues().toString());
                log.debug("Field end -------------------");
            }
//            Form formSet = formGet.createAnswerForm();
//            formSet.setAnswer("muc#owner_roomname", roomName);
//            formSet.setAnswer("muc#owner_roomdesc", "Here the " + roomName + " description.");
//            formSet.setAnswer("leave", "bye");
//            formSet.setAnswer("join", "hi");
//            formSet.setAnswer("rename", "rename nick message");
//            formSet.setAnswer("muc#owner_changesubject", false);
//            MAL//formSet.setAnswer("muc#owner_maxusers", 50);
//            formSet.setAnswer("privacy", true);
//            formSet.setAnswer("muc#owner_publicroom", true);
//            formSet.setAnswer("muc#owner_persistentroom", false);
//            formSet.setAnswer("legacy", false);
//            formSet.setAnswer("muc#owner_moderatedroom", false);
//            formSet.setAnswer("defaulttype", false);
//            MAL//formSet.setAnswer("muc#owner_whois", true);
//            formSet.setAnswer("muc#owner_inviteonly", false);
//            formSet.setAnswer("muc#owner_allowinvites", true);
//            formSet.setAnswer("muc#owner_passwordprotectedroom", false);
//            formSet.setAnswer("muc#owner_enablelogging", true);

            //muc.sendConfigurationForm(formSet);

            muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
//            muc.join(owner);
//            muc.leave();
//            muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
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
        muc = new MultiUserChat(conn, roomName + "@" + server);
        setListeners();
    }

    private void setListeners() {
        muc.addMessageListener(new PacketListener() {
            public void processPacket(Packet packet) {
                processPacketInSessions(packet);
            }
        });
        muc.addParticipantStatusListener(new ParticipantStatusListener() {
            HashMap<String, String>response = new HashMap<String, String>();

            public void adminGranted(String arg0) {
                // TODO Auto-generated method stub
            }

            public void adminRevoked(String participant) {
                // TODO Auto-generated method stub
            }

            public void banned(String participant, String actor, String reason) {
                // TODO Auto-generated method stub
            }

            public void joined(String participant) {
                response.clear();
                String nick = participant; //.split("/")[1];
                String time = formatter.format(new Date());
                response.put("server-msg", "<span style='font-style: italic;'>" + time + " --- " +
                        nick + " came online" + "</spam>");
                EventQueueCont.getInstance().addEvent(session,
                    new Event("org.ourproject.kune.muc.room.server-msg" + "." + getRoomName(), response));
                response.clear();
                response.put("user", nick);
                response.put("status", "joined");
                EventQueueCont.getInstance().addEvent(session,
                        new Event("org.ourproject.kune.muc.room.presence" + "." + getRoomName(), response));
            }

            public void kicked(String participant, String actor, String reason) {
                // TODO Auto-generated method stub
            }

            public void left(String participant) {
                String nick = participant; //.split("/")[1];
                response.clear();
                String time = formatter.format(new Date());
                response.put("server-msg", "<span style='font-style: italic;'>" + time + " --- " +
                        nick + " went offline" + "</spam>");
                EventQueueCont.getInstance().addEvent(session,
                        new Event("org.ourproject.kune.muc.room.server-msg" + "." + getRoomName(), response));
                response.clear();
                response.put("user", nick);
                response.put("status", "left");
                EventQueueCont.getInstance().addEvent(session,
                        new Event("org.ourproject.kune.muc.room.presence" + "." + getRoomName(), response));
            }

            public void membershipGranted(String participant) {
                // TODO Auto-generated method stub
            }

            public void membershipRevoked(String participant) {
                // TODO Auto-generated method stub
            }

            public void moderatorGranted(String participant) {
                // TODO Auto-generated method stub
            }

            public void moderatorRevoked(String participant) {
                // TODO Auto-generated method stub
            }

            public void nicknameChanged(String participant, String newNickname) {
                // TODO Auto-generated method stub
            }

            public void ownershipGranted(String participant) {
                // TODO Auto-generated method stub
            }

            public void ownershipRevoked(String participant) {
                // TODO Auto-generated method stub
            }

            public void voiceGranted(String participant) {
                // TODO Auto-generated method stub
            }

            public void voiceRevoked(String participant) {
                // TODO Auto-generated method stub
            }} );
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
        return muc.getRoom().replace("@" + server, "");
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
        HashMap<String, String>response = new HashMap<String, String>();
        if (packet instanceof Message) {
            final Message message = (Message) packet;
            response.put("from", message.getFrom());
            log.debug(message.getFrom());
            response.put("body", message.getBody());
            EventQueueCont.getInstance().addEvent(session,
                    new Event("org.ourproject.kune.muc.room" + "." + this.getRoomName(),
                            response));
        }
    }
}