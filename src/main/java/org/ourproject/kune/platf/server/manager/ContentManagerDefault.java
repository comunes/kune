package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
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

    public Content getContent(final UserSession session, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) {
	Content content;

	ContentDescriptor descriptor;
	if (groupName == null) {
	    descriptor = session.getUser().getUserGroup().getDefaultContent();
	    content = new Content(descriptor, descriptor.getFolder());
	} else {
	    if (contentRef != null) {
		descriptor = contentDescriptorManager.get(Long.parseLong(contentRef));
		content = new Content(descriptor, descriptor.getFolder());
	    } else {
		if (folderRef != null) {
		    Folder folder = folderManager.get(Long.parseLong(folderRef));
		    content = new Content(null, folder);
		} else {
		    if (toolName != null) {
			Group group = groupManager.get(groupName);
			content = new Content(null, group.getRoot(toolName));
		    } else {
			Group group = groupManager.get(groupName);
			content = new Content(group.getDefaultContent(), group.getDefaultContent().getFolder());
		    }
		}
	    }
	}
	return content;

    }
}
