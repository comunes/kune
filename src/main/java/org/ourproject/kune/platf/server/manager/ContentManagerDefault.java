package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public class ContentManagerDefault implements ContentManager {
    private final GroupManager groupManager;
    private final ContentDescriptorManager contentDescriptorManager;
    private final FolderManager folderManager;

    public ContentManagerDefault(final GroupManager groupManager, final FolderManager folderManager,
	    final ContentDescriptorManager contentDescriptorManager) {
	this.groupManager = groupManager;
	this.folderManager = folderManager;
	this.contentDescriptorManager = contentDescriptorManager;
    }

    public Content getContent(final User user, final String groupName, final String toolName, final String folderRef,
	    final String contentRef) throws ContentNotFoundException {

	Long contentId = checkAndParse(contentRef);
	Long folderId = checkAndParse(folderRef);

	if (noneNull(groupName, toolName, folderRef, contentRef)) {
	    return findByContentReference(groupName, toolName, folderId, contentId);
	} else if (noneNull(groupName, toolName, folderRef)) {
	    return findByFolderReference(groupName, toolName, folderId);
	} else if (noneNull(groupName, toolName)) {
	    return findByRootOnGroup(groupName, toolName);
	} else if (noneNull(groupName)) {
	    return findDefaultOfGroup(groupName);
	} else if (allNull(groupName, toolName, folderRef, contentRef)) {
	    return findDefaultOfGroup(user.getUserGroup());
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
	ContentDescriptor descriptor = contentDescriptorManager.get(contentId);
	Folder folder = descriptor.getFolder();
	if (!folder.getId().equals(folderId)) {
	    throw new ContentNotFoundException();
	}
	if (!folder.getToolName().equals(toolName)) {
	    throw new ContentNotFoundException();
	}
	if (!folder.getOwner().getShortName().equals(groupName)) {
	    throw new ContentNotFoundException();
	}
	return new Content(descriptor, folder);
    }

    private Content findByFolderReference(final String groupName, final String toolName, final Long folderId) {
	Folder folder = folderManager.get(folderId);
	return new Content(null, folder);
    }

    private Content findByRootOnGroup(final String groupName, final String toolName) {
	Group group = groupManager.get(groupName);
	return new Content(null, group.getRoot(toolName));
    }

    private Content findDefaultOfGroup(final String groupName) {
	Group group = groupManager.get(groupName);
	return findDefaultOfGroup(group);
    }

    private Content findDefaultOfGroup(final Group group) {
	return new Content(group.getDefaultContent(), group.getDefaultContent().getFolder());
    }

    private boolean allNull(final String... args) {
	for (String value : args) {
	    if (value != null) {
		return false;
	    }
	}
	return true;
    }

    private boolean noneNull(final String... args) {
	for (String value : args) {
	    if (value == null) {
		return false;
	    }
	}
	return true;
    }

}
