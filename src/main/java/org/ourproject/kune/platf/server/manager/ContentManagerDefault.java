package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentManagerDefault implements ContentManager {
    private final GroupManager groupManager;
    private final ContentDescriptorManager contentDescriptorManager;
    private final FolderManager folderManager;

    @Inject
    public ContentManagerDefault(final GroupManager groupManager, final FolderManager folderManager,
	    final ContentDescriptorManager contentDescriptorManager) {
	this.groupManager = groupManager;
	this.folderManager = folderManager;
	this.contentDescriptorManager = contentDescriptorManager;
    }

    public ContentDescriptor getContent(final User user, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

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

    private ContentDescriptor findByContentReference(final String groupName, final String toolName,
	    final Long folderId, final Long contentId) throws ContentNotFoundException {
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
	return descriptor;
    }

    private ContentDescriptor findByFolderReference(final String groupName, final String toolName, final Long folderId) {
	Folder folder = folderManager.find(folderId);
	return generateFolderFakeContent(folder);
    }

    private ContentDescriptor findByRootOnGroup(final String groupName, final String toolName) {
	Group group = groupManager.findByShortName(groupName);
	Folder folder = group.getRoot(toolName);
	return generateFolderFakeContent(folder);
    }

    private ContentDescriptor generateFolderFakeContent(final Folder folder) {
	ContentDescriptor descriptor = new ContentDescriptor();
	descriptor.setFolder(folder);
	AccessLists emptyAccessList = new AccessLists();
	descriptor.setAccessLists(emptyAccessList);
	return descriptor;
    }

    private ContentDescriptor findDefaultOfGroup(final String groupName) {
	Group group = groupManager.findByShortName(groupName);
	return findDefaultOfGroup(group);
    }

    private ContentDescriptor findDefaultOfGroup(final Group group) {
	ContentDescriptor defaultContent = group.getDefaultContent();
	return defaultContent;
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

    public void save(final Content content, final User user, final String content2) {
	// TODO Auto-generated method stub

    }

}
