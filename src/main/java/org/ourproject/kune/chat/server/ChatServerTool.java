
package org.ourproject.kune.chat.server;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;

public class ChatServerTool implements ServerTool {
    public static final String TYPE_ROOT = ChatClientTool.TYPE_ROOT;
    public static final String TYPE_ROOM = ChatClientTool.TYPE_ROOM;
    public static final String TYPE_CHAT = ChatClientTool.TYPE_CHAT;
    public static final String ROOT_NAME = "chat rooms";
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

    public void onCreateContainer(final Container container, final Container parent) {
        if (!parent.getTypeId().equals(TYPE_ROOT)) {
            throw new RuntimeException();
        }
        container.setTypeId(TYPE_ROOM);
    }

    public void onCreateContent(final Content content, final Container parent) {
        if (parent.getTypeId().equals(TYPE_ROOM)) {
            throw new RuntimeException();
        }
        content.setTypeId(TYPE_CHAT);
    }

    public Group initGroup(final User user, final Group group) {
        ToolConfiguration config = new ToolConfiguration();
        Container container = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
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
