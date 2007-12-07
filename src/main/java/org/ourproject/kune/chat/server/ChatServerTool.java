/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
    public static final String ROOT_NAME = "chats";
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
        // i18n
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
