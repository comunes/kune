package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.AccessListsManager;
import org.ourproject.kune.platf.server.manager.AccessRightsManager;
import org.ourproject.kune.platf.server.manager.ContentManager;
import org.ourproject.kune.platf.server.manager.MetadataManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.UserManagerDefault;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class ContentServerService implements ContentService {
    private final ContentManager contentManager;
    private final AccessListsManager accessListsManager;
    private final AccessRightsManager accessRightsManager;
    private final MetadataManager metadataManager;
    private final UserSession session;
    private final KuneProperties properties;
    private final UserManager userManager;

    @Inject
    public ContentServerService(final UserSession session, final ContentManager contentManager,
	    final AccessListsManager accessListsManager, final AccessRightsManager accessRightManager,
	    final MetadataManager metadaManager, final UserManagerDefault userManager, final KuneProperties properties) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessListsManager = accessListsManager;
	this.accessRightsManager = accessRightManager;
	this.metadataManager = metadaManager;
	this.userManager = userManager;
	this.properties = properties;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public ContentDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	User user = session.getUser();
	if (user == null) {
	    String shortName = properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
	    user = userManager.getByShortName(shortName);
	}

	Content content = contentManager.getContent(user, groupName, toolName, folderRef, contentRef);
	AccessLists accessLists = accessListsManager.get(content.getDescriptor());
	AccessRights accessRights = accessRightsManager.get(user, accessLists);
	metadataManager.fill(content, accessLists, accessRights);
	return map(content);
    }

    private ContentDTO map(final Content descriptor) {
	return null;
    }

}
