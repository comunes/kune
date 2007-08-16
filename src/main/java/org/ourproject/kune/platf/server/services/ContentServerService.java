package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.AccessRightsManager;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.ContentManager;
import org.ourproject.kune.platf.server.manager.FolderManager;
import org.ourproject.kune.platf.server.manager.GroupManager;
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
    private final GroupManager groupManager;

    @Inject
    public ContentServerService(final UserSession session, final ContentManager contentManager,
	    final AccessRightsManager accessRightManager, final MetadataManager metadaManager,
	    final UserManager userManager, final FolderManager folderManager, final GroupManager groupManager,
	    final ContentDescriptorManager contentDescriptorManager, final KuneProperties properties,
	    final Mapper mapper) {
	this.session = session;
	this.contentManager = contentManager;
	this.accessRightsManager = accessRightManager;
	this.metadataManager = metadaManager;
	this.userManager = userManager;
	this.folderManager = folderManager;
	this.groupManager = groupManager;
	this.contentDescriptorManager = contentDescriptorManager;
	this.properties = properties;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public ContentDTO getContent(final String userHash, final StateToken token) throws ContentNotFoundException {
	// TODO Auto-generated method stub

	// siempre tenenemos un grupo (aunque sea por defecto)
	// pero no siempre tenemos un usuario loggeado!
	Group group;
	User user = session.getUser();
	if (user == null) {
	    String shortName = properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
	    group = userManager.getByShortName(shortName).getUserGroup();
	} else {
	    group = user.getUserGroup();
	}

	ContentDescriptor descriptor = contentManager.getContent(group, token);
	return buildResponse(user, descriptor);
    }

    private ContentDTO buildResponse(final User user, final ContentDescriptor descriptor) {
	Group group = user != null ? user.getUserGroup() : null;
	AccessLists contentAccessLists = getContentAccessList(descriptor);
	AccessLists folderAccessLists = getFolderAccessLists(descriptor.getFolder());

	AccessRights contentRights = accessRightsManager.get(group, contentAccessLists);
	AccessRights folderRights;
	if (contentAccessLists != folderAccessLists) {
	    folderRights = accessRightsManager.get(group, folderAccessLists);
	} else {
	    folderRights = contentRights;
	}
	// FIXME: si accRights.isVisible == false > throw Exception!!

	Content content = metadataManager.fill(descriptor, contentAccessLists, contentRights, folderRights);
	return mapper.map(content, ContentDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public int save(final String user, final String documentId, final String content) throws AccessViolationException {
	Long id = new Long(documentId);
	Group userGroup = session.getUser().getUserGroup();
	ContentDescriptor descriptor = contentDescriptorManager.find(id);
	AccessRights accessRights = accessRightsManager.get(userGroup, getContentAccessList(descriptor));
	if (accessRights.isEditable()) {
	    descriptor = contentDescriptorManager.save(userGroup, descriptor, content);
	    return descriptor.getVersion();
	} else {
	    throw new AccessViolationException();
	}
    }

    private AccessLists getContentAccessList(final ContentDescriptor descriptor) {
	AccessLists accessLists = descriptor.getAccessLists();
	if (accessLists == null) {
	    SocialNetwork socialNetwork = descriptor.getFolder().getOwner().getSocialNetwork();
	    accessLists = socialNetwork.getAccessList();
	}
	return accessLists;
    }

    private AccessLists getFolderAccessLists(final Folder folder) {
	return folder.getOwner().getSocialNetwork().getAccessList();
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public ContentDTO addContent(final String userHash, final Long parentFolderId, final String title) {
	User user = session.getUser();
	Folder folder = folderManager.find(parentFolderId);
	ContentDescriptor descriptor = contentDescriptorManager.createContent(title, user, folder);
	return buildResponse(user, descriptor);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public ContentDTO addFolder(final String hash, final String groupShotName, final Long parentFolderId,
	    final String title) throws ContentNotFoundException {
	Group group = groupManager.findByShortName(groupShotName);
	Folder folder = folderManager.createFolder(group, parentFolderId, title);
	String toolName = folder.getToolName();
	StateToken token = new StateToken(group.getShortName(), toolName, folder.getId().toString(), null);
	ContentDescriptor descriptor = contentManager.getContent(group, token);
	return buildResponse(session.getUser(), descriptor);
    }
}
