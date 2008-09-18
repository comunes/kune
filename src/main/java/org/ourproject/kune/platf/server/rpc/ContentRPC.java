/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.ourproject.kune.chat.server.ChatServerTool;
import org.ourproject.kune.chat.server.managers.ChatConnection;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.ToolNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.access.AccessRightsService;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.CommentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.TagManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.state.State;
import org.ourproject.kune.platf.server.state.StateService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class ContentRPC implements ContentService, RPC {
    private final StateService stateService;
    private final Provider<UserSession> userSessionProvider;
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final AccessService accessService;
    private final CreationService creationService;
    private final XmppManager xmppManager;
    private final ContentManager contentManager;
    private final ContainerManager containerManager;
    private final TagManager tagManager;
    private final SocialNetworkManager socialNetworkManager;
    private final CommentManager commentManager;
    private final AccessRightsService rightsService;

    @Inject
    public ContentRPC(final Provider<UserSession> userSessionProvider, final AccessService accessService,
	    final AccessRightsService rightsService, final StateService stateService,
	    final CreationService creationService, final GroupManager groupManager, final XmppManager xmppManager,
	    final ContentManager contentManager, final ContainerManager containerManager, final TagManager tagManager,
	    final SocialNetworkManager socialNetworkManager, final CommentManager commentManager, final Mapper mapper) {
	this.userSessionProvider = userSessionProvider;
	this.accessService = accessService;
	this.rightsService = rightsService;
	this.stateService = stateService;
	this.creationService = creationService;
	this.groupManager = groupManager;
	this.xmppManager = xmppManager;
	this.contentManager = contentManager;
	this.containerManager = containerManager;
	this.tagManager = tagManager;
	this.socialNetworkManager = socialNetworkManager;
	this.commentManager = commentManager;
	this.mapper = mapper;
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public void addAuthor(final String userHash, final StateToken token, final String authorShortName)
	    throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	contentManager.addAuthor(user, contentId, authorShortName);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO addComment(final String userHash, final StateToken token, final Long parentCommentId,
	    final String commentText) throws DefaultException {
	final UserSession userSession = getUserSession();
	final User author = userSession.getUser();
	final Long contentId = parseId(token.getDocument());
	final Comment comment = commentManager.addComment(author, contentId, commentText, parentCommentId);
	return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO addComment(final String userHash, final StateToken token, final String commentText)
	    throws DefaultException {
	final UserSession userSession = getUserSession();
	final User author = userSession.getUser();
	final Long contentId = parseId(token.getDocument());
	final Comment comment = commentManager.addComment(author, contentId, commentText);
	return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addContent(final String userHash, final StateToken parentToken, final String title)
	    throws DefaultException {
	final Group group = groupManager.findByShortName(parentToken.getGroup());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final boolean userIsLoggedIn = userSession.isUserLoggedIn();
	final Container container = accessService.accessToContainer(parseId(parentToken.getFolder()), user,
		AccessRol.Editor);
	final Content addedContent = creationService.createContent(title, "", user, container);
	final Access access = accessService.getAccess(user, addedContent.getStateToken(), group, AccessRol.Editor);
	final State state = stateService.create(access);
	completeState(user, userIsLoggedIn, group, state);
	return mapState(state, user, group);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addFolder(final String userHash, final StateToken parentToken, final String title,
	    final String contentTypeId) throws DefaultException {
	final Group group = groupManager.findByShortName(parentToken.getGroup());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final boolean userIsLoggedIn = userSession.isUserLoggedIn();
	final State state = createFolder(parentToken.getGroup(), parseId(parentToken.getFolder()), title, contentTypeId);
	completeState(user, userIsLoggedIn, group, state);
	return mapState(state, user, group);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateDTO addRoom(final String userHash, final StateToken parentToken, final String roomName)
	    throws DefaultException {
	final String groupShortName = parentToken.getGroup();
	final Group group = groupManager.findByShortName(groupShortName);
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final boolean userIsLoggedIn = userSession.isUserLoggedIn();
	final String userShortName = user.getShortName();
	final ChatConnection connection = xmppManager.login(userShortName, userSession.getUser().getPassword(),
		userHash);
	xmppManager.createRoom(connection, roomName, userShortName + userHash);
	xmppManager.disconnect(connection);
	try {
	    final State state = createFolder(groupShortName, parseId(parentToken.getFolder()), roomName,
		    ChatServerTool.TYPE_ROOM);
	    completeState(user, userIsLoggedIn, group, state);
	    return mapState(state, user, group);
	} catch (final ContentNotFoundException e) {
	    xmppManager.destroyRoom(connection, roomName);
	    throw new ContentNotFoundException();
	} catch (final AccessViolationException e) {
	    xmppManager.destroyRoom(connection, roomName);
	    throw new AccessViolationException();
	} catch (final GroupNotFoundException e) {
	    xmppManager.destroyRoom(connection, roomName);
	    throw new GroupNotFoundException();
	}
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public void delContent(final String userHash, final StateToken token) throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	contentManager.delContent(user, contentId);
    }

    // Not using @Authorizated because accessService is doing this job and is
    // more complex than other access checks (we use getContent to get default
    // contents for instance)
    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_ONLY)
    public StateDTO getContent(final String userHash, final StateToken token) throws DefaultException {
	Group defaultGroup;
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final boolean userIsLoggedIn = userSession.isUserLoggedIn();
	if (userIsLoggedIn) {
	    defaultGroup = groupManager.getGroupOfUserWithId(user.getId());
	} else {
	    defaultGroup = groupManager.getDefaultGroup();
	}
	Access access;
	try {
	    access = accessService.getAccess(user, token, defaultGroup, AccessRol.Viewer);
	} catch (final NoResultException e) {
	    throw new ContentNotFoundException();
	} catch (final ToolNotFoundException e) {
	    throw new ContentNotFoundException();
	}
	final State state = stateService.create(access);
	final Group group = state.getGroup();
	completeState(user, userIsLoggedIn, group, state);
	return mapState(state, user, group);
    }

    @Authenticated(mandatory = false)
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_ONLY)
    public List<TagResultDTO> getSummaryTags(final String userHash, final StateToken groupToken) {
	final Group group = groupManager.findByShortName(groupToken.getGroup());
	return getSummaryTags(group);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO markCommentAsAbuse(final String userHash, final StateToken token, final Long commentId)
	    throws DefaultException {
	final UserSession userSession = getUserSession();
	final User informer = userSession.getUser();
	final Long contentId = parseId(token.getDocument());
	final Comment comment = commentManager.markAsAbuse(informer, contentId, commentId);
	return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public void rateContent(final String userHash, final StateToken token, final Double value) throws DefaultException {
	final UserSession userSession = getUserSession();
	final User rater = userSession.getUser();
	final Long contentId = parseId(token.getDocument());

	if (userSession.isUserLoggedIn()) {
	    contentManager.rateContent(rater, contentId, value);
	} else {
	    throw new AccessViolationException();
	}
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public void removeAuthor(final String userHash, final StateToken token, final String authorShortName)
	    throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	contentManager.removeAuthor(user, contentId, authorShortName);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public String renameContainer(final String userHash, final StateToken token, final String newName)
	    throws DefaultException {
	return renameFolder(token.getGroup(), parseId(token.getFolder()), newName);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public String renameContent(final String userHash, final StateToken token, final String newName)
	    throws DefaultException {
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	try {
	    accessService.accessToContent(parseId(token.getDocument()), user, AccessRol.Editor);
	} catch (final NoResultException e) {
	    throw new AccessViolationException();
	}
	return renameContent(token.getDocument(), newName);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public Integer save(final String userHash, final StateToken token, final String textContent)
	    throws DefaultException {

	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Content content = accessService.accessToContent(contentId, user, AccessRol.Editor);
	final Content descriptor = creationService.saveContent(user, content, textContent);
	return descriptor.getVersion();
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public ContentDTO setAsDefaultContent(final String userHash, final StateToken token) {
	final Content content = contentManager.find(parseId(token.getDocument()));
	groupManager.setDefaultContent(token.getGroup(), content);
	return mapper.map(content, ContentDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public I18nLanguageDTO setLanguage(final String userHash, final StateToken token, final String languageCode)
	    throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	return mapper.map(contentManager.setLanguage(user, contentId, languageCode), I18nLanguageDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setPublishedOn(final String userHash, final StateToken token, final Date publishedOn)
	    throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	contentManager.setPublishedOn(user, contentId, publishedOn);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public List<TagResultDTO> setTags(final String userHash, final StateToken token, final String tags)
	    throws DefaultException {
	final Long contentId = parseId(token.getDocument());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Group group = groupManager.findByShortName(token.getGroup());
	contentManager.setTags(user, contentId, tags);
	return getSummaryTags(group);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO voteComment(final String userHash, final StateToken token, final Long commentId,
	    final boolean votePositive) throws DefaultException {
	final UserSession userSession = getUserSession();
	final User voter = userSession.getUser();
	final Long contentId = parseId(token.getDocument());
	final Comment comment = commentManager.vote(voter, contentId, commentId, votePositive);
	return mapper.map(comment, CommentDTO.class);
    }

    private void completeState(final User user, final boolean userIsLoggedIn, final Group group, final State state) {
	if (state.isRateable()) {
	    final Long contentId = parseId(state.getDocumentId());
	    final Content content = contentManager.find(contentId);
	    if (userIsLoggedIn) {
		state.setCurrentUserRate(contentManager.getRateContent(user, content));
	    }
	    state.setRate(contentManager.getRateAvg(content));
	    state.setRateByUsers(contentManager.getRateByUsers(content));
	}

	state.setGroupTags(tagManager.getSummaryByGroup(group));
	state.setGroupMembers(socialNetworkManager.find(user, group));
	state.setParticipation(socialNetworkManager.findParticipation(user, group));
    }

    private State createFolder(final String groupShortName, final Long parentFolderId, final String title,
	    final String typeId) throws DefaultException {
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Group group = groupManager.findByShortName(groupShortName);

	accessService.accessToContainer(parentFolderId, user, AccessRol.Editor);

	final Container container = creationService.createFolder(group, parentFolderId, title, user.getLanguage(),
		typeId);
	final Access access = accessService.getAccess(user, container.getStateToken(), group, AccessRol.Editor);
	final State state = stateService.create(access);
	return state;
    }

    private List<TagResultDTO> getSummaryTags(final Group group) {
	return mapper.mapList(tagManager.getSummaryByGroup(group), TagResultDTO.class);
    }

    private UserSession getUserSession() {
	return userSessionProvider.get();
    }

    private void mapContentRightsInstate(final User user, final AccessLists groupAccessList, final ContentDTO siblingDTO) {
	final Content sibling = contentManager.find(siblingDTO.getId());
	final AccessLists lists = sibling.hasAccessList() ? sibling.getAccessLists() : groupAccessList;
	siblingDTO.setRights(mapper.map(rightsService.get(user, lists), AccessRightsDTO.class));
    }

    private StateDTO mapState(final State state, final User user, final Group group) {
	final StateDTO stateDTO = mapper.map(state, StateDTO.class);
	final AccessLists groupAccessList = group.getSocialNetwork().getAccessLists();
	for (final ContentDTO siblingDTO : stateDTO.getRootContainer().getContents()) {
	    mapContentRightsInstate(user, groupAccessList, siblingDTO);
	}
	for (final ContentDTO siblingDTO : stateDTO.getContainer().getContents()) {
	    mapContentRightsInstate(user, groupAccessList, siblingDTO);
	}
	return stateDTO;
    }

    private Long parseId(final String id) throws ContentNotFoundException {
	try {
	    return new Long(id);
	} catch (final NumberFormatException e) {
	    throw new ContentNotFoundException();
	}
    }

    private String renameContent(final String documentId, final String newName) throws ContentNotFoundException,
	    DefaultException {
	final Long contentId = parseId(documentId);
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	return contentManager.renameContent(user, contentId, newName);
    }

    private String renameFolder(final String groupShortName, final Long folderId, final String newName)
	    throws DefaultException {
	final Group group = groupManager.findByShortName(groupShortName);
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Container container = accessService.accessToContainer(folderId, user, AccessRol.Editor);
	return containerManager.renameFolder(group, container, newName);
    }

}
