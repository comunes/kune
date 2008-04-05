package org.ourproject.kune.chat.client;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.gwtjsjac.client.Debugger;
import com.calclab.gwtjsjac.client.PresenceShow;
import com.calclab.gwtjsjac.client.XmppConnection;
import com.calclab.gwtjsjac.client.XmppFactory;
import com.calclab.gwtjsjac.client.XmppUserSettings;
import com.calclab.gwtjsjac.client.impl.JsJacFactory;
import com.calclab.gwtjsjac.client.log.GWTLoggerOutput;
import com.calclab.gwtjsjac.client.mandioca.XmppSession;
import com.calclab.gwtjsjac.client.mandioca.rooms.XmppRoom;

class ChatEngineXmpp implements ChatEngine {
    private XmppSession session;
    private final ChatState state;
    private final XmppConnection connection;

    public ChatEngineXmpp(final ChatState state) {
        this.state = state;
        XmppFactory factory = JsJacFactory.getInstance();
        connection = factory.createBindingConnection(state.httpBase, 2000, GWTLoggerOutput.instance);
        Debugger.debug(connection, new LoggerOutputImpl());
    }

    public ChatState getState() {
        return state;
    }

    public void login(final String chatName, final String chatPassword) {
        Log.debug("LOGIN CHAT: " + chatName + "[" + chatPassword + "]");
        state.user = new XmppUserSettings(state.domain, chatName, chatPassword, XmppUserSettings.NON_SASL);
        state.user.resource = "kuneClient" + new Date().getTime();
        session = new XmppSession(connection, true);
        session.login(state.user);
        // FIXME: hardcoded
        session.getUser().sendPresence(PresenceShow.CHAT, ":: ready ::");
    }

    public void logout() {
        // FIXME: bug
        // this$static has no properties
        // [Break on this error] if (this$static.session !== null) {
        if (session != null) {
            session.logout();
        }
    }

    public XmppRoom joinRoom(final String roomName, final String userAlias) {
        return session.joinRoom(state.roomHost, roomName, userAlias);
    }

}
