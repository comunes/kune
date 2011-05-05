/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.rpc;

import java.util.Date;

import javax.persistence.NoResultException;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.NoDefaultContentException;
import cc.kune.core.client.errors.ToolNotFoundException;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.access.FinderService;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.state.StateContainer;
import cc.kune.core.server.state.StateContent;
import cc.kune.core.server.state.StateService;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateNoContentDTO;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ContentRPC implements ContentService, RPC {
  private final AccessService accessService;
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final CreationService creationService;
  private final FinderService finderService;
  private final GroupManager groupManager;
  private final Mapper mapper;
  private final AccessRightsService rightsService;
  private final StateService stateService;
  private final TagUserContentManager tagManager;
  private final Provider<UserSession> userSessionProvider;
  private final XmppManager xmppManager;

  @Inject
  public ContentRPC(final FinderService finderService, final Provider<UserSession> userSessionProvider,
      final AccessService accessService, final AccessRightsService rightsService,
      final StateService stateService, final CreationService creationService,
      final GroupManager groupManager, final XmppManager xmppManager,
      final ContentManager contentManager, final ContainerManager containerManager,
      final TagUserContentManager tagManager, final Mapper mapper) {
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
    this.mapper = mapper;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public void addAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.addAuthor(user, contentId, authorShortName);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public StateContentDTO addContent(final String userHash, final StateToken parentToken,
      final String title, final String typeId) throws DefaultException {
    return createContent(parentToken, title, typeId);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public StateContainerDTO addFolder(final String userHash, final StateToken parentToken,
      final String title, final String contentTypeId) throws DefaultException {
    final User user = getCurrentUser();
    final Container container = createFolder(parentToken.getGroup(),
        ContentUtils.parseId(parentToken.getFolder()), title, contentTypeId);
    return getState(user, container);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public void addParticipant(final String userHash, final StateToken token, final String participant)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.addParticipant(user, contentId, participant);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
  @Transactional
  public StateContainerDTO addRoom(final String userHash, final StateToken parentToken,
      final String roomName) throws DefaultException {
    // final String groupShortName = parentToken.getGroup();
    // final User user = getCurrentUser();
    // final String userShortName = user.getShortName();
    // final ChatConnection connection = xmppManager.login(userShortName,
    // user.getPassword(), userHash);
    // xmppManager.createRoom(connection, roomName, userShortName +
    // userHash);
    // xmppManager.disconnect(connection);
    // try {
    // final Container container = createFolder(groupShortName,
    // ContentUtils.parseId(parentToken.getFolder()),
    // roomName, ChatServerTool.TYPE_ROOM);
    // return getState(user, container);
    // } catch (final ContentNotFoundException e) {
    // xmppManager.destroyRoom(connection, roomName);
    // throw new ContentNotFoundException();
    // } catch (final AccessViolationException e) {
    // xmppManager.destroyRoom(connection, roomName);
    // throw new AccessViolationException();
    // } catch (final GroupNotFoundException e) {
    // xmppManager.destroyRoom(connection, roomName);
    // throw new GroupNotFoundException();
    // }
    return null;
  }

  private StateContentDTO createContent(final StateToken parentToken, final String title,
      final String typeId) {
    final User user = getCurrentUser();
    final Container container = accessService.accessToContainer(
        ContentUtils.parseId(parentToken.getFolder()), user, AccessRol.Editor);
    final String body = "";
    final Content addedContent = creationService.createContent(title, body, user, container, typeId);
    return getState(user, addedContent);
  }

  private Container createFolder(final String groupShortName, final Long parentFolderId,
      final String title, final String typeId) throws DefaultException {
    final User user = getCurrentUser();
    final Group group = groupManager.findByShortName(groupShortName);
    final Container container = creationService.createFolder(group, parentFolderId, title,
        user.getLanguage(), typeId);
    return container;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator)
  @Transactional
  public StateContentDTO delContent(final String userHash, final StateToken token)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    return getState(getCurrentUser(), contentManager.setStatus(contentId, ContentStatus.inTheDustbin));
  }

  @Override
  @Authenticated(mandatory = false)
  @Transactional
  public StateAbstractDTO getContent(final String userHash, final StateToken token)
      throws DefaultException {
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

  private TagCloudResult getSummaryTags(final Group group) {
    final TagCloudResult result = tagManager.getTagCloudResultByGroup(group);
    return result;
  }

  @Override
  @Authenticated(mandatory = false)
  @Authorizated(accessRolRequired = AccessRol.Viewer)
  @Transactional
  public TagCloudResult getSummaryTags(final String userHash, final StateToken groupToken) {
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    return getSummaryTags(group);
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
    siblingDTO.setRights(mapper.map(rightsService.get(user, lists), AccessRights.class));
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

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer)
  @Transactional
  public RateResult rateContent(final String userHash, final StateToken token, final Double value)
      throws DefaultException {
    final User rater = getCurrentUser();
    final Long contentId = ContentUtils.parseId(token.getDocument());

    if (isUserLoggedIn()) {
      return contentManager.rateContent(rater, contentId, value);
    } else {
      throw new AccessViolationException();
    }
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public void removeAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.removeAuthor(user, contentId, authorShortName);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public StateAbstractDTO renameContainer(final String userHash, final StateToken token,
      final String newName) throws DefaultException {
    renameFolder(token.getGroup(), ContentUtils.parseId(token.getFolder()), newName);
    return getContent(userHash, token);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public StateAbstractDTO renameContent(final String userHash, final StateToken token,
      final String newName) throws DefaultException {
    final User user = getCurrentUser();
    try {
      accessService.accessToContent(ContentUtils.parseId(token.getDocument()), user, AccessRol.Editor);
    } catch (final NoResultException e) {
      throw new AccessViolationException();
    }
    renameContent(token.getDocument(), newName);
    return getContent(userHash, token);
  }

  private Content renameContent(final String documentId, final String newName)
      throws ContentNotFoundException, DefaultException {
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

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public void save(final String userHash, final StateToken token, final String textContent)
      throws DefaultException {

    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    final Content content = accessService.accessToContent(contentId, user, AccessRol.Editor);
    creationService.saveContent(user, content, textContent);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator)
  @Transactional
  public ContentSimpleDTO setAsDefaultContent(final String userHash, final StateToken token) {
    final Content content = contentManager.find(ContentUtils.parseId(token.getDocument()));
    groupManager.setDefaultContent(token.getGroup(), content);
    return mapper.map(content, ContentSimpleDTO.class);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public I18nLanguageDTO setLanguage(final String userHash, final StateToken token,
      final String languageCode) throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    return mapper.map(contentManager.setLanguage(user, contentId, languageCode), I18nLanguageDTO.class);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public void setPublishedOn(final String userHash, final StateToken token, final Date publishedOn)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.setPublishedOn(user, contentId, publishedOn);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor)
  @Transactional
  public StateAbstractDTO setStatus(final String userHash, final StateToken token,
      final ContentStatus status) {
    if (status.equals(ContentStatus.publishedOnline) || status.equals(ContentStatus.rejected)
        || status.equals(ContentStatus.inTheDustbin)) {
      throw new AccessViolationException();
    }
    final Content content = contentManager.setStatus(ContentUtils.parseId(token.getDocument()),
        ContentStatus.valueOf(status.toString()));
    return getState(getCurrentUser(), content);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator)
  @Transactional
  public StateAbstractDTO setStatusAsAdmin(final String userHash, final StateToken token,
      final ContentStatus status) {
    final Content content = contentManager.setStatus(ContentUtils.parseId(token.getDocument()),
        ContentStatus.valueOf(status.toString()));
    return getState(getCurrentUser(), content);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @Transactional
  public TagCloudResult setTags(final String userHash, final StateToken token, final String tags)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    final Group group = groupManager.findByShortName(token.getGroup());
    contentManager.setTags(user, contentId, tags);
    return getSummaryTags(group);
  }

}
