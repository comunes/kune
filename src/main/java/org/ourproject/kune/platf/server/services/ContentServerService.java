package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.AccessManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.manager.MetadataManagerDefault;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;

import com.google.inject.Inject;

public class ContentServerService implements ContentService {
    private final ContentManagerDefault contentManager;
    private final AccessManagerDefault accessManager;
    private final MetadataManagerDefault metadataManager;
    private final UserSession session;

    @Inject
    public ContentServerService(final UserSession session, final ContentManagerDefault contentManager,
	    final AccessManagerDefault accessManager, final MetadataManagerDefault metadaManager) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessManager = accessManager;
	this.metadataManager = metadaManager;
    }

    public ContentDataDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	Content content = contentManager.getContent(session, groupName, toolName, folderRef, contentRef);
	accessManager.check(session.getUser(), content);
	metadataManager.fillOtherContents(content);
	return map(content);
    }

    private ContentDataDTO map(final Content descriptor) {
	return null;
    }

}
