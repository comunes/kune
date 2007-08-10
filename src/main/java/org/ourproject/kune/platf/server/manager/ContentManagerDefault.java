package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;

public class ContentManagerDefault implements ContentManager {
    private final GroupManager groupManager;
    private final ContentDescriptorManager contentDescriptorManager;

    public ContentManagerDefault(final GroupManager groupManager,
	    final ContentDescriptorManager contentDescriptorManager) {
	this.groupManager = groupManager;
	this.contentDescriptorManager = contentDescriptorManager;
    }

    public ContentDescriptor getContent(final UserSession session, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) {
	ContentDescriptor descriptor = null;

	if (groupName == null) {
	    descriptor = session.getUser().getUserGroup().getDefaultContent();
	} else {
	    descriptor = contentDescriptorManager.get(Long.parseLong(contentRef));
	}
	return descriptor;
    }

}
