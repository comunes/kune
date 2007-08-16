package org.ourproject.kune.chat.server;

import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;

public class ChatServerTool implements ServerTool {
    public static final String NAME = "chats";
    private final ToolConfigurationManager configurationManager;
    private final ContainerManager containerManager;

    @Inject
    public ChatServerTool(final ToolConfigurationManager configurationManager, final ContainerManager containerManager) {
	this.configurationManager = configurationManager;
	this.containerManager = containerManager;
    }

    public String getName() {
	return NAME;
    }

    public Group initGroup(final User user, final Group group) {
	ToolConfiguration config = new ToolConfiguration();
	// i18n: salas
	Container container = containerManager.createRootFolder(group, NAME, "salas", "chats.chats");
	config.setRoot(container);
	group.setToolConfig(NAME, config);
	configurationManager.persist(config);
	return group;
    }

    @Inject
    public void register(final ToolRegistry registry) {
	registry.register(this);
    }

}
