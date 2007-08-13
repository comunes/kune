package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AccessList;
import org.ourproject.kune.platf.server.manager.AccessListManagerDefault;
import org.ourproject.kune.platf.server.manager.AccessRightManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.manager.MetadataManagerDefault;
import org.ourproject.kune.platf.server.model.AccessRight;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;

public class ContentServerService implements ContentService {
    private final ContentManagerDefault contentManager;
    private final AccessListManagerDefault accessListManager;
    private final AccessRightManagerDefault accessRightManager;
    private final MetadataManagerDefault metadataManager;
    private final UserSession session;

    @Inject
    public ContentServerService(final UserSession session, final ContentManagerDefault contentManager,
	    final AccessListManagerDefault accessListManager, final AccessRightManagerDefault accessRightManager,
	    final MetadataManagerDefault metadaManager) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessListManager = accessListManager;
	this.accessRightManager = accessRightManager;
	this.metadataManager = metadaManager;
    }

    public ContentDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	Content content = contentManager.getContent(session.getUser(), groupName, toolName, folderRef, contentRef);
	AccessList accessList = accessListManager.get(session.getUser(), content);
	AccessRight accessRight = accessRightManager.get(session.getUser(), accessList);
	metadataManager.fill(content, accessList, accessRight);
	return map(content);
    }

    private ContentDTO map(final Content descriptor) {
	return null;
    }

}
