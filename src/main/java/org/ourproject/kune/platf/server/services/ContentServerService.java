package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.manager.AccessListsManagerDefault;
import org.ourproject.kune.platf.server.manager.AccessRightsManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.manager.MetadataManagerDefault;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;

public class ContentServerService implements ContentService {
    private final ContentManagerDefault contentManager;
    private final AccessListsManagerDefault accessListsManager;
    private final AccessRightsManagerDefault accessRightsManager;
    private final MetadataManagerDefault metadataManager;
    private final UserSession session;

    @Inject
    public ContentServerService(final UserSession session, final ContentManagerDefault contentManager,
	    final AccessListsManagerDefault accessListsManager, final AccessRightsManagerDefault accessRightManager,
	    final MetadataManagerDefault metadaManager) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessListsManager = accessListsManager;
	this.accessRightsManager = accessRightManager;
	this.metadataManager = metadaManager;
    }

    public ContentDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	Content content = contentManager.getContent(session.getUser(), groupName, toolName, folderRef, contentRef);
	AccessLists accessLists = accessListsManager.get(content.getDescriptor());
	AccessRights accessRights = accessRightsManager.get(session.getUser(), accessLists);
	metadataManager.fill(content, accessLists, accessRights);
	return map(content);
    }

    private ContentDTO map(final Content descriptor) {
	return null;
    }

}
