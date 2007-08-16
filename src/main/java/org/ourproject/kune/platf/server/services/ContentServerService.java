package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.access.AccessType;
import org.ourproject.kune.platf.server.access.Accessor;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.state.State;
import org.ourproject.kune.platf.server.state.StateService;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class ContentServerService implements ContentService {
    private final StateService stateService;
    private final UserSession session;
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final Accessor accessor;
    private final CreationService creationService;

    @Inject
    public ContentServerService(final UserSession session, final Accessor contentAccess,
	    final StateService stateService, final CreationService creationService, final GroupManager groupManager,
	    final Mapper mapper) {
	this.session = session;
	this.accessor = contentAccess;
	this.stateService = stateService;
	this.creationService = creationService;
	this.groupManager = groupManager;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public StateDTO getContent(final String userHash, final StateToken token) throws ContentNotFoundException,
	    AccessViolationException {
	User user = session.getUser();
	Group contentGroup = user != null ? user.getUserGroup() : groupManager.getDefaultGroup();
	Group loggedGroup = user != null ? user.getUserGroup() : null;

	Access access = accessor.getAccess(token, contentGroup, loggedGroup, AccessType.READ);
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public int save(final String user, final String documentId, final String content) throws AccessViolationException,
	    ContentNotFoundException {

	Long contentId = parseId(documentId);
	Group userGroup = session.getUser().getUserGroup();
	Access access = accessor.getContentAccess(contentId, userGroup, AccessType.EDIT);
	Content descriptor = creationService.saveContent(userGroup, access.getDescriptor(), content);
	return descriptor.getVersion();
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addContent(final String userHash, final Long parentFolderId, final String title)
	    throws AccessViolationException, ContentNotFoundException {

	User user = session.getUser();
	Access access = accessor.getFolderAccess(parentFolderId, user.getUserGroup(), AccessType.EDIT);
	access.setDescriptorWidthFolderRights(creationService.createContent(title, user, access.getFolder()));
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addFolder(final String hash, final String groupShotName, final Long parentFolderId,
	    final String title) throws ContentNotFoundException, AccessViolationException {
	Group group = groupManager.findByShortName(groupShotName);

	Access access = accessor.getFolderAccess(parentFolderId, group, AccessType.EDIT);
	Container container = creationService.createFolder(group, parentFolderId, title);
	String toolName = container.getToolName();
	StateToken token = new StateToken(group.getShortName(), toolName, container.getId().toString(), null);
	access = accessor.getAccess(token, group, group, AccessType.READ);
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    private Long parseId(final String documentId) throws ContentNotFoundException {
	try {
	    return new Long(documentId);
	} catch (NumberFormatException e) {
	    throw new ContentNotFoundException();
	}
    }

}
