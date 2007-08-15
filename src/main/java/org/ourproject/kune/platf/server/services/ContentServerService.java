package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.AccessRightsManager;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.ContentManager;
import org.ourproject.kune.platf.server.manager.FolderManager;
import org.ourproject.kune.platf.server.manager.MetadataManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class ContentServerService implements ContentService {
    private final ContentManager contentManager;
    private final AccessRightsManager accessRightsManager;
    private final MetadataManager metadataManager;
    private final UserSession session;
    private final KuneProperties properties;
    private final UserManager userManager;
    private final Mapper mapper;
    private final FolderManager folderManager;
    private final ContentDescriptorManager contentDescriptorManager;

    @Inject
    public ContentServerService(final UserSession session, final ContentManager contentManager,
	    final AccessRightsManager accessRightManager, final MetadataManager metadaManager,
	    final UserManager userManager, final FolderManager folderManager,
	    final ContentDescriptorManager contentDescriptorManager, final KuneProperties properties,
	    final Mapper mapper) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessRightsManager = accessRightManager;
	this.metadataManager = metadaManager;
	this.userManager = userManager;
	this.folderManager = folderManager;
	this.contentDescriptorManager = contentDescriptorManager;
	this.properties = properties;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public ContentDTO getContent(final String userHash, final String groupName, final String toolName,
	    final String folderRef, final String contentRef) throws ContentNotFoundException {

	User user = session.getUser();
	if (user == null) {
	    String shortName = properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
	    user = userManager.getByShortName(shortName);
	}

	ContentDescriptor descriptor = contentManager.getContent(user, groupName, toolName, folderRef, contentRef);
	AccessLists accessLists = getAccessList(descriptor);
	AccessRights accessRights = accessRightsManager.get(user, accessLists);

	Content content = metadataManager.fill(descriptor, accessLists, accessRights);
	return mapper.map(content, ContentDTO.class);
    }

    @Authenticated
    public void save(final String userHash, final ContentDTO dto) throws AccessViolationException {

    }

    private AccessLists getAccessList(final ContentDescriptor content) {
	AccessLists accessLists = content.getAccessLists();
	if (accessLists == null) {
	    SocialNetwork socialNetwork = content.getFolder().getOwner().getSocialNetwork();
	    accessLists = socialNetwork.getAccessList();
	}
	return accessLists;
    }

    @Authenticated
    public ContentDTO addContent(final String userHash, final Long parentFolderId, final String name) {
	User user = session.getUser();
	Folder folder = folderManager.find(parentFolderId);
	contentDescriptorManager.createContent(user, folder);
	return null;
    }
}
