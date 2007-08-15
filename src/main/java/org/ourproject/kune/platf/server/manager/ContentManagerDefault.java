package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.dto.StateToken;
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

    public ContentDescriptor getContent(final Group group, final StateToken token) throws ContentNotFoundException {
	Long contentId = checkAndParse(token.document);
	Long folderId = checkAndParse(token.folder);

	if (token.hasAll()) {
	    return findByContentReference(token.group, token.tool, folderId, contentId);
	} else if (token.hasGroupToolAndFolder()) {
	    return findByFolderReference(token.group, token.tool, folderId);
	} else if (token.hasGroupAndTool()) {
	    return findByRootOnGroup(token.group, token.tool);
	} else if (token.hasGroup()) {
	    return findDefaultOfGroup(token.group);
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

    public void save(final Content content, final User user, final String content2) {
	// TODO Auto-generated method stub

    }

}
