package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;

import com.google.inject.Inject;

public class ContentServerService implements ContentService {
    private final ContentManagerDefault contentManager;
    private final UserSession session;

    @Inject
    public ContentServerService(final UserSession session, final ContentManagerDefault contentManager) {
	this.session = session;
	this.contentManager = contentManager;
    }

    public ContentDataDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) {

	Content descriptor = contentManager.getContent(session, groupName, toolName, folderRef, contentRef);

	return map(descriptor);
    }

    private ContentDataDTO map(final Content descriptor) {
	return null;
    }

}
