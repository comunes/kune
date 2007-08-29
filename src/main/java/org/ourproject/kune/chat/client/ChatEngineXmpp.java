package org.ourproject.kune.chat.client;

import com.calclab.gwtjsjac.client.Debugger;
import com.calclab.gwtjsjac.client.XmppHttpBindingConnection;
import com.calclab.gwtjsjac.client.XmppUser;
import com.calclab.gwtjsjac.client.log.GWTLoggerOutput;
import com.calclab.gwtjsjac.client.mandioca.XmppSession;

class ChatEngineXmpp implements ChatEngine {
    private final String httpBase;
    private final XmppHttpBindingConnection connection;
    private final String domain;
    private XmppSession session;

    public ChatEngineXmpp() {
	// FIXME
	this.httpBase = "http-bind/";
	this.domain = "localhost";
	connection = new XmppHttpBindingConnection(httpBase, 2000);
	Debugger.debug(connection, new GWTLoggerOutput());
    }

    public void login(final String chatName, final String chatPassword) {
	XmppUser user = new XmppUser(domain, chatName, chatPassword);
	session = new XmppSession(connection, user);
	session.login();
    }
}
