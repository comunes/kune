/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.chat.server.managers.ChatConnection;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.access.AccessType;
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
public class ContentRPC implements ContentService, RPC {
    private final StateService stateService;
    private final UserSession session;
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final AccessService accessManager;
    private final CreationService creationService;
    private final XmppManager xmppManager;

    @Inject
    public ContentRPC(final UserSession session, final AccessService contentAccess, final StateService stateService,
	    final CreationService creationService, final GroupManager groupManager, final XmppManager xmppManager,
	    final Mapper mapper) {
	this.session = session;
	this.accessManager = contentAccess;
	this.stateService = stateService;
	this.creationService = creationService;
	this.groupManager = groupManager;
	this.xmppManager = xmppManager;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public StateDTO getContent(final String userHash, final StateToken token) throws ContentNotFoundException,
	    AccessViolationException, GroupNotFoundException {
	Group contentGroup;
	Group loggedGroup;

	if (session.isUserLoggedIn()) {
	    contentGroup = groupManager.getGroupOfUserWithId(session.getUserId());
	    loggedGroup = contentGroup;
	} else {
	    contentGroup = groupManager.getDefaultGroup();
	    loggedGroup = Group.NO_GROUP;
	}

	Access access = accessManager.getAccess(token, contentGroup, loggedGroup, AccessType.READ);
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public int save(final String userHash, final String documentId, final String textContent)
	    throws AccessViolationException, ContentNotFoundException {

	Long contentId = parseId(documentId);
	User user = session.getUser();
	Content content = accessManager.accessToContent(contentId, user, AccessType.EDIT);
	Content descriptor = creationService.saveContent(user, content, textContent);
	return descriptor.getVersion();
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addContent(final String userHash, final Long parentFolderId, final String title)
	    throws AccessViolationException, ContentNotFoundException {

	User user = session.getUser();
	Group group = groupManager.getGroupOfUserWithId(session.getUser().getId());
	Access access = accessManager.getFolderAccess(parentFolderId, group, AccessType.EDIT);
	access.setContentWidthFolderRights(creationService.createContent(title, user, access.getFolder()));
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addFolder(final String userHash, final String groupShotName, final Long parentFolderId,
	    final String title) throws ContentNotFoundException, AccessViolationException, GroupNotFoundException {
	return createFolder(groupShotName, parentFolderId, title);
    }

    private StateDTO createFolder(final String groupShortName, final Long parentFolderId, final String title)
	    throws AccessViolationException, ContentNotFoundException, GroupNotFoundException {
	Group group = groupManager.findByShortName(groupShortName);

	Access access = accessManager.getFolderAccess(parentFolderId, group, AccessType.EDIT);
	Container container = creationService.createFolder(group, parentFolderId, title);
	String toolName = container.getToolName();
	StateToken token = new StateToken(group.getShortName(), toolName, container.getId().toString(), null);
	access = accessManager.getAccess(token, group, group, AccessType.READ);
	State state = stateService.create(access);
	return mapper.map(state, StateDTO.class);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addRoom(final String userHash, final String groupShotName, final Long parentFolderId,
	    final String roomName) throws ContentNotFoundException, AccessViolationException, GroupNotFoundException {
	String userShortName = session.getUser().getShortName();
	ChatConnection connection = xmppManager.login(userShortName, session.getUser().getPassword(), userHash);
	xmppManager.createRoom(connection, roomName, userShortName + userHash);
	xmppManager.disconnect(connection);
	return createFolder(groupShotName, parentFolderId, roomName);
    }

    private Long parseId(final String documentId) throws ContentNotFoundException {
	try {
	    return new Long(documentId);
	} catch (NumberFormatException e) {
	    throw new ContentNotFoundException();
	}
    }

}
