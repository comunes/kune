
package org.ourproject.kune.chat.server;

import com.google.inject.AbstractModule;

public class ChatServerModule extends AbstractModule {

    public void configure() {
	// To debug Smack, descomment this
	// XMPPConnection.DEBUG_ENABLED = true;

	// FIXME: Dani: check this please
	bind(ChatServerTool.class).asEagerSingleton();
    }

}
