/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.chat.server;

import org.ourproject.kune.chat.client.ChatClientTool;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

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

    public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
        checkContainerTypeId(parentTypeId, typeId);
    }

    public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
        if (!parentTypeId.equals(TYPE_ROOM)) {
            throw new ContainerNotPermittedException();
        }
        // in the future chat history checks
    }

    public String getName() {
        return NAME;
    }

    public String getRootName() {
        return ROOT_NAME;
    }

    public ServerToolTarget getTarget() {
        return ServerToolTarget.forGroups;
    }

    public Group initGroup(final User user, final Group group) {
        final ToolConfiguration config = new ToolConfiguration();
        final Container container = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
        config.setRoot(container);
        group.setToolConfig(NAME, config);
        configurationManager.persist(config);
        return group;
    }

    public void onCreateContainer(final Container container, final Container parent) {
    }

    public void onCreateContent(final Content content, final Container parent) {
    }

    @Inject
    public void register(final ServerToolRegistry registry) {
        registry.register(this);
    }

    private void checkContainerTypeId(final String parentTypeId, final String typeId) {
        if (typeId.equals(TYPE_ROOM)) {
            if (!parentTypeId.equals(TYPE_ROOT)) {
                throw new ContainerNotPermittedException();
            }
            // ok valid container
        } else {
            throw new ContainerNotPermittedException();
        }
    }

}
