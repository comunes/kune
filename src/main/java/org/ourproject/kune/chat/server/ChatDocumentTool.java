package org.ourproject.kune.chat.server;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;

public class ChatDocumentTool implements ServerTool {
    public static final String NAME = "chats";

    public String getName() {
	return NAME;
    }

    public Group initGroup(final User user, final Group group) {
	// TODO Auto-generated method stub
	return null;
    }

    @Inject
    public void register(final ToolRegistry registry) {
	registry.register(this);
    }

}
