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

package org.ourproject.kune.platf.server.access;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.ToolNotFoundException;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.RateManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FinderServiceDefault implements FinderService {
    private final GroupManager groupManager;
    private final ContentManager contentManager;
    private final ContainerManager containerManager;
    private final RateManager rateManager;

    @Inject
    public FinderServiceDefault(final GroupManager groupManager, final ContainerManager containerManager,
            final ContentManager contentManager, final RateManager rateManager) {
        this.groupManager = groupManager;
        this.containerManager = containerManager;
        this.contentManager = contentManager;
        this.rateManager = rateManager;
    }

    public Content getContent(final Long contentId) throws ContentNotFoundException {
        try {
            return contentManager.find(contentId);
        } catch (PersistenceException e) {
            throw new ContentNotFoundException();
        }
    }

    public Container getFolder(final Long folderId) throws ContentNotFoundException {
        try {
            return containerManager.find(folderId);
        } catch (PersistenceException e) {
            throw new ContentNotFoundException();
        }

    }

    public Content getContent(final StateToken token, final Group defaultGroup) throws SerializableException {
        Long contentId = checkAndParse(token.getDocument());
        Long folderId = checkAndParse(token.getFolder());

        if (token.hasAll()) {
            return findByContentReference(token.getGroup(), token.getTool(), folderId, contentId);
        } else if (token.hasGroupToolAndFolder()) {
            return findByFolderReference(token.getGroup(), token.getTool(), folderId);
        } else if (token.hasGroupAndTool()) {
            return findByRootOnGroup(token.getGroup(), token.getTool());
        } else if (token.hasGroup()) {
            return findDefaultOfGroup(token.getGroup());
        } else if (token.hasNothing()) {
            return findDefaultOfGroup(defaultGroup);
        } else {
            throw new ContentNotFoundException();
        }
    }

    public Long checkAndParse(final String s) throws ContentNotFoundException {
        if (s != null) {
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                throw new ContentNotFoundException();
            }
        }
        return null;
    }

    private Content findByContentReference(final String groupName, final String toolName, final Long folderId,
            final Long contentId) throws ContentNotFoundException {
        Content descriptor = contentManager.find(contentId);
        Container container = descriptor.getFolder();

        if (!container.getId().equals(folderId)) {
            throw new ContentNotFoundException();
        }
        if (!container.getToolName().equals(toolName)) {
            throw new ContentNotFoundException();
        }
        if (!container.getOwner().getShortName().equals(groupName)) {
            throw new ContentNotFoundException();
        }
        return descriptor;
    }

    private Content findByFolderReference(final String groupName, final String toolName, final Long folderId) {
        Container container = containerManager.find(folderId);
        return generateFolderFakeContent(container);
    }

    private Content findByRootOnGroup(final String groupName, final String toolName) throws SerializableException {
        try {
            Group group = groupManager.findByShortName(groupName);
            if (!group.existToolConfig(toolName)) {
                throw new ToolNotFoundException();
            }
            Container container = group.getRoot(toolName);
            return generateFolderFakeContent(container);
        } catch (NoResultException e) {
            throw new GroupNotFoundException();
        }
    }

    private Content generateFolderFakeContent(final Container container) {
        Content descriptor = new Content();
        descriptor.setFolder(container);
        return descriptor;
    }

    private Content findDefaultOfGroup(final String groupName) throws GroupNotFoundException {
        Group group = groupManager.findByShortName(groupName);
        return findDefaultOfGroup(group);
    }

    private Content findDefaultOfGroup(final Group group) {
        Content defaultContent = group.getDefaultContent();
        return defaultContent;
    }

    public Double getRateAvg(final Content content) {

        return rateManager.getRateAvg(content);
    }

    public Long getRateByUsers(final Content content) {
        return rateManager.getRateByUsers(content);
    }

    public Rate getRate(final User user, final Content content) {
        return rateManager.find(user, content);
    }

}
