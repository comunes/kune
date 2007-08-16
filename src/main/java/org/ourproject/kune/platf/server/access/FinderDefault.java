package org.ourproject.kune.platf.server.access;

import javax.persistence.PersistenceException;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FinderDefault implements Finder {
    private final GroupManager groupManager;
    private final ContentManager contentManager;
    private final ContainerManager containerManager;

    @Inject
    public FinderDefault(final GroupManager groupManager, final ContainerManager containerManager,
	    final ContentManager contentManager) {
	this.groupManager = groupManager;
	this.containerManager = containerManager;
	this.contentManager = contentManager;
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

    public Content getContent(final Group group, final StateToken token) throws ContentNotFoundException {
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
	    return findDefaultOfGroup(group);
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

    private Content findByContentReference(final String groupName, final String toolName,
	    final Long folderId, final Long contentId) throws ContentNotFoundException {
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

    private Content findByRootOnGroup(final String groupName, final String toolName) {
	Group group = groupManager.findByShortName(groupName);
	Container container = group.getRoot(toolName);
	return generateFolderFakeContent(container);
    }

    private Content generateFolderFakeContent(final Container container) {
	Content descriptor = new Content();
	descriptor.setFolder(container);
	AccessLists emptyAccessList = new AccessLists();
	descriptor.setAccessLists(emptyAccessList);
	return descriptor;
    }

    private Content findDefaultOfGroup(final String groupName) {
	Group group = groupManager.findByShortName(groupName);
	return findDefaultOfGroup(group);
    }

    private Content findDefaultOfGroup(final Group group) {
	Content defaultContent = group.getDefaultContent();
	return defaultContent;
    }

}
