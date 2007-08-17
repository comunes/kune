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

import com.google.inject.Singleton;

@Singleton
public class Xmpp2ManagerDefault implements Xmpp2Manager {

    public Xmpp2ManagerDefault() {
    }

    public ChatConnection login(final String userName, final String password) {
	// FIXME: externalizar
	ConnectionConfiguration config = new ConnectionConfiguration(getServerName(), 5222);
	XMPPConnection conn = new XMPPConnection(config);
	try {
	    conn.connect();
	    conn.login(userName, password, "nose", true);
	    return new XmppConnection(userName, conn);
	} catch (XMPPException e) {
	    throw new ChatException(e);
	}
    }

    private String getServerName() {
	return "localhost";
    }

    private String getRoomName(final String room) {
	return room + "@conference." + getServerName();
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
}
