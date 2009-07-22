/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import javax.persistence.NoResultException;

import org.ourproject.kune.chat.server.ChatServerTool;
import org.ourproject.kune.chat.server.managers.ChatConnection;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.RateResultDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateNoContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagCloudResultDTO;
import org.ourproject.kune.platf.client.dto.TagCountDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.NoDefaultContentException;
import org.ourproject.kune.platf.client.errors.ToolNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRightsService;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.CommentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.ContentStatus;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.RateResult;
import org.ourproject.kune.platf.server.domain.TagCloudResult;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.TagUserContentManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.state.StateContainer;
import org.ourproject.kune.platf.server.state.StateContent;
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
    private final TagUserContentManager tagManager;
    private final CommentManager commentManager;
    private final AccessRightsService rightsService;
    private final FinderService finderService;

    @Inject
    public ContentRPC(final FinderService finderService, final Provider<UserSession> userSessionProvider,
            final AccessService accessService, final AccessRightsService rightsService,
            final StateService stateService, final CreationService creationService, final GroupManager groupManager,
            final XmppManager xmppManager, final ContentManager contentManager,
            final ContainerManager containerManager, final TagUserContentManager tagManager,
            final CommentManager commentManager, final Mapper mapper) {
        this.finderService = finderService;
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
        this.commentManager = commentManager;
        this.mapper = mapper;
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public void addAuthor(final String userHash, final StateToken token, final String authorShortName)
            throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        contentManager.addAuthor(user, contentId, authorShortName);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO addComment(final String userHash, final StateToken token, final Long parentCommentId,
            final String commentText) throws DefaultException {
        final User author = getCurrentUser();
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final Comment comment = commentManager.addComment(author, contentId, commentText, parentCommentId);
        return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO addComment(final String userHash, final StateToken token, final String commentText)
            throws DefaultException {
        final User author = getCurrentUser();
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final Comment comment = commentManager.addComment(author, contentId, commentText);
        return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateContentDTO addContent(final String userHash, final StateToken parentToken, final String title,
            final String typeId) throws DefaultException {
        final String body = "";
        return createContent(parentToken, title, typeId, body);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateContainerDTO addFolder(final String userHash, final StateToken parentToken, final String title,
            final String contentTypeId) throws DefaultException {
        final User user = getCurrentUser();
        final Container container = createFolder(parentToken.getGroup(), ContentUtils.parseId(parentToken.getFolder()),
                title, contentTypeId);
        return getState(user, container);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateContainerDTO addRoom(final String userHash, final StateToken parentToken, final String roomName)
            throws DefaultException {
        final String groupShortName = parentToken.getGroup();
        final User user = getCurrentUser();
        final String userShortName = user.getShortName();
        final ChatConnection connection = xmppManager.login(userShortName, user.getPassword(), userHash);
        xmppManager.createRoom(connection, roomName, userShortName + userHash);
        xmppManager.disconnect(connection);
        try {
            final Container container = createFolder(groupShortName, ContentUtils.parseId(parentToken.getFolder()),
                    roomName, ChatServerTool.TYPE_ROOM);
            return getState(user, container);
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
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateContentDTO addWave(final String userHash, final StateToken parentToken, final String typeId,
            final String waveId) throws DefaultException {
        return createContent(parentToken, "Wave embeded test", typeId, waveId);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateContentDTO delContent(final String userHash, final StateToken token) throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        return getState(getCurrentUser(), contentManager.setStatus(contentId, ContentStatus.inTheDustbin));
    }

    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_ONLY)
    public StateAbstractDTO getContent(final String userHash, final StateToken token) throws DefaultException {
        Group defaultGroup;
        final User user = getCurrentUser();
        if (isUserLoggedIn()) {
            defaultGroup = groupManager.getGroupOfUserWithId(user.getId());
            if (groupManager.findEnabledTools(defaultGroup.getId()).size() == 0) {
                // Groups with no homepage
                defaultGroup = groupManager.getSiteDefaultGroup();
            }
        } else {
            defaultGroup = groupManager.getSiteDefaultGroup();
        }
        try {
            final Content content = finderService.getContent(token, defaultGroup);
            return getContentOrDefContent(userHash, content.getStateToken(), user, content);
        } catch (final NoResultException e) {
            throw new ContentNotFoundException();
        } catch (final ToolNotFoundException e) {
            throw new ContentNotFoundException();
        } catch (final NoDefaultContentException e) {
            return mapper.map(stateService.createNoHome(user, token.getGroup()), StateNoContentDTO.class);
        }
    }

    @Authenticated(mandatory = false)
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_ONLY)
    public TagCloudResultDTO getSummaryTags(final String userHash, final StateToken groupToken) {
        final Group group = groupManager.findByShortName(groupToken.getGroup());
        return getSummaryTags(group);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO markCommentAsAbuse(final String userHash, final StateToken token, final Long commentId)
            throws DefaultException {
        final User informer = getCurrentUser();
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final Comment comment = commentManager.markAsAbuse(informer, contentId, commentId);
        return mapper.map(comment, CommentDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public RateResultDTO rateContent(final String userHash, final StateToken token, final Double value)
            throws DefaultException {
        final User rater = getCurrentUser();
        final Long contentId = ContentUtils.parseId(token.getDocument());

        if (isUserLoggedIn()) {
            final RateResult result = contentManager.rateContent(rater, contentId, value);
            return mapper.map(result, RateResultDTO.class);
        } else {
            throw new AccessViolationException();
        }
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public void removeAuthor(final String userHash, final StateToken token, final String authorShortName)
            throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        contentManager.removeAuthor(user, contentId, authorShortName);
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateAbstractDTO renameContainer(final String userHash, final StateToken token, final String newName)
            throws DefaultException {
        renameFolder(token.getGroup(), ContentUtils.parseId(token.getFolder()), newName);
        return getContent(userHash, token);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateAbstractDTO renameContent(final String userHash, final StateToken token, final String newName)
            throws DefaultException {
        final User user = getCurrentUser();
        try {
            accessService.accessToContent(ContentUtils.parseId(token.getDocument()), user, AccessRol.Editor);
        } catch (final NoResultException e) {
            throw new AccessViolationException();
        }
        renameContent(token.getDocument(), newName);
        return getContent(userHash, token);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(final String userHash, final StateToken token, final String textContent) throws DefaultException {

        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        final Content content = accessService.accessToContent(contentId, user, AccessRol.Editor);
        creationService.saveContent(user, content, textContent);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public ContentSimpleDTO setAsDefaultContent(final String userHash, final StateToken token) {
        final Content content = contentManager.find(ContentUtils.parseId(token.getDocument()));
        groupManager.setDefaultContent(token.getGroup(), content);
        return mapper.map(content, ContentSimpleDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public I18nLanguageDTO setLanguage(final String userHash, final StateToken token, final String languageCode)
            throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        return mapper.map(contentManager.setLanguage(user, contentId, languageCode), I18nLanguageDTO.class);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setPublishedOn(final String userHash, final StateToken token, final Date publishedOn)
            throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        contentManager.setPublishedOn(user, contentId, publishedOn);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateAbstractDTO setStatus(final String userHash, final StateToken token, final ContentStatusDTO status) {
        if (status.equals(ContentStatusDTO.publishedOnline) || status.equals(ContentStatusDTO.rejected)
                || status.equals(ContentStatusDTO.inTheDustbin)) {
            throw new AccessViolationException();
        }
        final Content content = contentManager.setStatus(ContentUtils.parseId(token.getDocument()),
                ContentStatus.valueOf(status.toString()));
        return getState(getCurrentUser(), content);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public StateAbstractDTO setStatusAsAdmin(final String userHash, final StateToken token,
            final ContentStatusDTO status) {
        final Content content = contentManager.setStatus(ContentUtils.parseId(token.getDocument()),
                ContentStatus.valueOf(status.toString()));
        return getState(getCurrentUser(), content);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
    @Transactional(type = TransactionType.READ_WRITE)
    public TagCloudResultDTO setTags(final String userHash, final StateToken token, final String tags)
            throws DefaultException {
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final User user = getCurrentUser();
        final Group group = groupManager.findByShortName(token.getGroup());
        contentManager.setTags(user, contentId, tags);
        return getSummaryTags(group);
    }

    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    @Transactional(type = TransactionType.READ_WRITE)
    public CommentDTO voteComment(final String userHash, final StateToken token, final Long commentId,
            final boolean votePositive) throws DefaultException {
        final User voter = getCurrentUser();
        final Long contentId = ContentUtils.parseId(token.getDocument());
        final Comment comment = commentManager.vote(voter, contentId, commentId, votePositive);
        return mapper.map(comment, CommentDTO.class);
    }

    private StateContentDTO createContent(final StateToken parentToken, final String title, final String typeId,
            final String body) {
        final User user = getCurrentUser();
        final Container container = accessService.accessToContainer(ContentUtils.parseId(parentToken.getFolder()),
                user, AccessRol.Editor);
        final Content addedContent = creationService.createContent(title, body, user, container, typeId);
        return getState(user, addedContent);
    }

    private Container createFolder(final String groupShortName, final Long parentFolderId, final String title,
            final String typeId) throws DefaultException {
        final User user = getCurrentUser();
        final Group group = groupManager.findByShortName(groupShortName);
        final Container container = creationService.createFolder(group, parentFolderId, title, user.getLanguage(),
                typeId);
        return container;
    }

    @Authenticated(mandatory = false)
    @Authorizated(accessRolRequired = AccessRol.Viewer)
    private StateAbstractDTO getContentOrDefContent(final String userHash, final StateToken stateToken,
            final User user, final Content content) {
        final Long id = content.getId();
        if (id != null) {
            // Content
            return mapState(stateService.create(user, content), user);
        } else {
            // Container
            final Container container = content.getContainer();
            return mapState(stateService.create(user, container), user);
        }
    }

    private User getCurrentUser() {
        return getUserSession().getUser();
    }

    private StateContainerDTO getState(final User user, final Container container) {
        final StateContainer state = stateService.create(user, container);
        return mapState(state, user);
    }

    private StateContentDTO getState(final User user, final Content content) {
        final StateContent state = stateService.create(user, content);
        return mapState(state, user);
    }

    private TagCloudResultDTO getSummaryTags(final Group group) {
        final TagCloudResult result = tagManager.getTagCloudResultByGroup(group);
        return new TagCloudResultDTO(mapper.mapList(result.getTagCountList(), TagCountDTO.class), result.getMaxValue(),
                result.getMinValue());
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

    private boolean isUserLoggedIn() {
        return getUserSession().isUserLoggedIn();
    }

    private void mapContentRightsInstate(final User user, final AccessLists groupAccessList,
            final ContentSimpleDTO siblingDTO) {
        final Content sibling = contentManager.find(siblingDTO.getId());
        final AccessLists lists = sibling.hasAccessList() ? sibling.getAccessLists() : groupAccessList;
        siblingDTO.setRights(mapper.map(rightsService.get(user, lists), AccessRightsDTO.class));
    }

    private StateContainerDTO mapState(final StateContainer state, final User user) {
        final StateContainerDTO stateDTO = mapper.map(state, StateContainerDTO.class);
        final AccessLists groupAccessList = state.getGroup().getSocialNetwork().getAccessLists();
        for (final ContentSimpleDTO siblingDTO : stateDTO.getRootContainer().getContents()) {
            mapContentRightsInstate(user, groupAccessList, siblingDTO);
        }
        for (final ContentSimpleDTO siblingDTO : stateDTO.getContainer().getContents()) {
            mapContentRightsInstate(user, groupAccessList, siblingDTO);
        }
        return stateDTO;
    }

    private StateContentDTO mapState(final StateContent state, final User user) {
        final StateContentDTO stateDTO = mapper.map(state, StateContentDTO.class);
        final AccessLists groupAccessList = state.getGroup().getSocialNetwork().getAccessLists();
        for (final ContentSimpleDTO siblingDTO : stateDTO.getRootContainer().getContents()) {
            mapContentRightsInstate(user, groupAccessList, siblingDTO);
        }
        for (final ContentSimpleDTO siblingDTO : stateDTO.getContainer().getContents()) {
            mapContentRightsInstate(user, groupAccessList, siblingDTO);
        }
        return stateDTO;
    }

    private Content renameContent(final String documentId, final String newName) throws ContentNotFoundException,
            DefaultException {
        final Long contentId = ContentUtils.parseId(documentId);
        final User user = getCurrentUser();
        return contentManager.renameContent(user, contentId, newName);
    }

    private Container renameFolder(final String groupShortName, final Long folderId, final String newName)
            throws DefaultException {
        final Group group = groupManager.findByShortName(groupShortName);
        final User user = getCurrentUser();
        final Container container = accessService.accessToContainer(folderId, user, AccessRol.Editor);
        return containerManager.renameFolder(group, container, newName);
    }

}
