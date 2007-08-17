package org.ourproject.kune.chat.server;

import org.jivesoftware.smack.XMPPConnection;
import org.ourproject.kune.chat.server.managers.Xmpp2Manager;
import org.ourproject.kune.chat.server.managers.Xmpp2ManagerDefault;

import com.google.inject.AbstractModule;

public class ChatServerModule extends AbstractModule {

    public void configure() {
	XMPPConnection.DEBUG_ENABLED = true;
	bind(ChatServerTool.class).asEagerSingleton();
	bind(Xmpp2Manager.class).to(Xmpp2ManagerDefault.class);
    }

}
