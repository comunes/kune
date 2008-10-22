/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.platf.server.access;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.ToolNotFoundException;
import org.ourproject.kune.platf.server.content.CommentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.RateManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FinderServiceDefault implements FinderService {
    private final GroupManager groupManager;
    private final ContentManager contentManager;
    private final ContainerManager containerManager;
    private final RateManager rateManager;
    private final CommentManager commentManager;

    @Inject
    public FinderServiceDefault(final GroupManager groupManager, final ContainerManager containerManager,
            final ContentManager contentManager, final RateManager rateManager, final CommentManager commentManager) {
        this.groupManager = groupManager;
        this.containerManager = containerManager;
        this.contentManager = contentManager;
        this.rateManager = rateManager;
        this.commentManager = commentManager;
    }

    public Long checkAndParse(final String s) throws ContentNotFoundException {
        if (s != null) {
            try {
                return Long.parseLong(s);
            } catch (final NumberFormatException e) {
                throw new ContentNotFoundException();
            }
        }
        return null;
    }

    public Comment getComment(final Long commentId) throws ContentNotFoundException {
        try {
            return commentManager.find(commentId);
        } catch (final PersistenceException e) {
            throw new ContentNotFoundException();
        }
    }

    public Content getContent(final Long contentId) throws ContentNotFoundException {
        try {
            return contentManager.find(contentId);
        } catch (final PersistenceException e) {
            throw new ContentNotFoundException();
        }
    }

    public Content getContent(final StateToken token, final Group defaultGroup) throws DefaultException {
        final Long contentId = checkAndParse(token.getDocument());
        final Long folderId = checkAndParse(token.getFolder());

        String group = token.getGroup();
        if (token.hasAll()) {
            return findByContentReference(group, token.getTool(), folderId, contentId);
        } else if (token.hasGroupToolAndFolder()) {
            return findByFolderReference(group, token.getTool(), folderId);
        } else if (token.hasGroupAndTool()) {
            return findByRootOnGroup(group, token.getTool());
        } else if (token.hasGroup()) {
            return findDefaultContentOfGroup(group);
        } else if (token.hasNothing()) {
            return findDefaultContentOfGroup(defaultGroup);
        } else {
            throw new ContentNotFoundException();
        }
    }

    public Container getFolder(final Long folderId) throws ContentNotFoundException {
        try {
            return containerManager.find(folderId);
        } catch (final PersistenceException e) {
            throw new ContentNotFoundException();
        }
    }

    public Rate getRate(final User user, final Content content) {
        return rateManager.find(user, content);
    }

    public Double getRateAvg(final Content content) {

        return rateManager.getRateAvg(content);
    }

    public Long getRateByUsers(final Content content) {
        return rateManager.getRateByUsers(content);
    }

    private Content findByContentReference(final String groupName, final String toolName, final Long folderId,
            final Long contentId) throws ContentNotFoundException {
        final Content descriptor = contentManager.find(contentId);
        final Container container = descriptor.getContainer();

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
        final Container container = containerManager.find(folderId);
        return generateFolderFakeContent(container);
    }

    private Content findByRootOnGroup(final String groupName, final String toolName) throws DefaultException {
        try {
            final Group group = groupManager.findByShortName(groupName);
            if (!group.existToolConfig(toolName)) {
                throw new ToolNotFoundException();
            }
            final Container container = group.getRoot(toolName);
            return generateFolderFakeContent(container);
        } catch (final NoResultException e) {
            throw new GroupNotFoundException();
        }
    }

    private Content findDefaultContentOfGroup(final Group group) {
        final Content defaultContent = group.getDefaultContent();
        return defaultContent == null ? Content.NO_CONTENT : defaultContent;
    }

    private Content findDefaultContentOfGroup(final String groupName) throws GroupNotFoundException {
        final Group group = groupManager.findByShortName(groupName);
        return findDefaultContentOfGroup(group);
    }

    private Content generateFolderFakeContent(final Container container) {
        final Content descriptor = new Content();
        descriptor.setContainer(container);
        return descriptor;
    }

}
