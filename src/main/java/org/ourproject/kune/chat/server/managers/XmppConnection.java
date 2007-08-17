package org.ourproject.kune.chat.server.managers;

import org.jivesoftware.smack.XMPPConnection;

public class XmppConnection implements ChatConnection {
    private final XMPPConnection conn;
    private final String userName;

    public XmppConnection(final String userName, final XMPPConnection conn) {
	this.userName = userName;
	this.conn = conn;
    }

    public XMPPConnection getConn() {
	return conn;
    }

    public String getUserName() {
	return userName;
    }

}
