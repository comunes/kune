package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.AccessListManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.manager.MetadataManagerDefault;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;

import com.google.inject.Inject;

public class ContentServerService implements ContentService {
    private final ContentManagerDefault contentManager;
    private final AccessListManagerDefault accessListManager;
    private final MetadataManagerDefault metadataManager;
    private final UserSession session;

    @Inject
    public ContentServerService(final UserSession session, final ContentManagerDefault contentManager,
	    final AccessListManagerDefault accessManager, final MetadataManagerDefault metadaManager) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessListManager = accessManager;
	this.metadataManager = metadaManager;
    }

    public ContentDataDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	// TODO: session -> session.getUser()
	Content content = contentManager.getContent(session, groupName, toolName, folderRef, contentRef);
	accessListManager.check(session.getUser(), content);
	metadataManager.fill(content);
	return map(content);
    }

    private ContentDataDTO map(final Content descriptor) {
	return null;
    }

}
