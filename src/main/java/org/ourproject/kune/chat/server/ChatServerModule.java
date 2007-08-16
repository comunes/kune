package org.ourproject.kune.chat.server;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ChatServerModule implements Module {

    public void configure(final Binder binder) {
	binder.bind(ChatServerTool.class).asEagerSingleton();
    }

}
