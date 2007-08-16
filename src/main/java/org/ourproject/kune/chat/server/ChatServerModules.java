package org.ourproject.kune.chat.server;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ChatServerModules implements Module {

    public void configure(final Binder binder) {
	binder.bind(ChatDocumentTool.class).asEagerSingleton();
    }

}
