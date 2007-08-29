package org.ourproject.kune.chat.client;

import com.calclab.gwtjsjac.client.Debugger;
import com.calclab.gwtjsjac.client.XmppHttpBindingConnection;
import com.calclab.gwtjsjac.client.XmppUser;
import com.calclab.gwtjsjac.client.log.GWTLoggerOutput;
import com.calclab.gwtjsjac.client.mandioca.XmppSession;

class ChatEngineXmpp implements ChatEngine {
    private final XmppHttpBindingConnection connection;
    private XmppSession session;
    private final ChatState state;

    public ChatEngineXmpp() {
	state = new ChatState();
	// FIXME
	state.httpBase = "/http-bind/";
	state.domain = "localhost";
	connection = new XmppHttpBindingConnection(state.httpBase, 2000);
	Debugger.debug(connection, new GWTLoggerOutput());
    }

    public ChatState getState() {
	return state;
    }

    public void login(final String chatName, final String chatPassword) {
	state.user = new XmppUser(state.domain, chatName, chatPassword);
	state.isConnected = false;
	reconnect();
    }

    public void reconnect() {
	if (!state.isConnected) {
	    session = new XmppSession(connection, state.user);
	    session.login();
	    state.isConnected = connection.isConnected();
	}
    }

}
