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

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.persistence.NoResultException;

import cc.kune.chat.server.ChatManager;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.CannotDeleteDefaultContentException;
import cc.kune.core.client.errors.ContainerNotEmptyException;
import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.ContentNotPermittedException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.NoDefaultContentException;
import cc.kune.core.client.errors.ToolNotFoundException;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.server.LogThis;
import cc.kune.core.server.UserSessionManager;
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
import cc.kune.core.server.manager.KuneWaveManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.state.StateContainer;
import cc.kune.core.server.state.StateContent;
import cc.kune.core.server.state.StateEventContainer;
import cc.kune.core.server.state.StateService;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateEventContainerDTO;
import cc.kune.core.shared.dto.StateNoContentDTO;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.trash.server.TrashServerUtils;

import com.google.inject.Inject;

@LogThis
public class ContentRPC implements ContentService, RPC {
  private final AccessService accessService;
  private final ChatManager chatManager;
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final CreationService creationService;
  private final FinderService finderService;
  private final GroupManager groupManager;
  private final Mapper mapper;
  private final AccessRightsService rightsService;
  private final StateService stateService;
  private final TagUserContentManager tagManager;
  private final UserSessionManager userSession;
  private final KuneWaveManager waveManager;

  @Inject
  public ContentRPC(final FinderService finderService, final UserSessionManager userSession,
      final AccessService accessService, final AccessRightsService rightsService,
      final StateService stateService, final CreationService creationService,
      final GroupManager groupManager, final ContentManager contentManager,
      final ContainerManager containerManager, final TagUserContentManager tagManager,
      final Mapper mapper, final ChatManager chatManager, final KuneWaveManager waveManager) {
    this.finderService = finderService;
    this.userSession = userSession;
    this.accessService = accessService;
    this.rightsService = rightsService;
    this.stateService = stateService;
    this.creationService = creationService;
    this.groupManager = groupManager;
    this.contentManager = contentManager;
    this.containerManager = containerManager;
    this.tagManager = tagManager;
    this.mapper = mapper;
    this.chatManager = chatManager;
    this.waveManager = waveManager;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public void addAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.addAuthor(user, contentId, authorShortName);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateContentDTO addContent(final String userHash, final StateToken parentToken,
      final String title, final String typeId) throws DefaultException {
    return createContent(parentToken, title, typeId);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateContainerDTO addFolder(final String userHash, final StateToken parentToken,
      final String title, final String contentTypeId) throws DefaultException {
    final User user = getCurrentUser();
    final Container container = createFolder(parentToken.getGroup(),
        ContentUtils.parseId(parentToken.getFolder()), title, contentTypeId);
    return getState(user, container);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.content, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public void addGadgetToContent(final String userHash, final StateToken currentStateToken,
      final String gadgetName) throws DefaultException {
    final User user = getCurrentUser();
    final Content content = accessService.accessToContent(
        ContentUtils.parseId(currentStateToken.getDocument()), user, AccessRol.Editor);
    contentManager.addGadgetToContent(user, content, gadgetName);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateContentDTO addNewContentWithGadget(final String userHash, final StateToken parentToken,
      final String gadgetname, final String typeId, final String title, final String body)
      throws DefaultException {
    return addNewContentWithGadgetAndState(userHash, parentToken, gadgetname, typeId, title, body,
        Collections.<String, String> emptyMap());
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateContentDTO addNewContentWithGadgetAndState(final String userHash,
      final StateToken parentToken, final String gadgetname, final String typeId, final String title,
      final String body, final Map<String, String> gadgetProperties) throws DefaultException {
    final User user = getCurrentUser();
    final Container container = accessService.accessToContainer(
        ContentUtils.parseId(parentToken.getFolder()), user, AccessRol.Editor);
    final Content addedContent = contentManager.createGadget(user, container, gadgetname, typeId, title,
        body, gadgetProperties);
    return getState(user, addedContent);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public Boolean addParticipant(final String userHash, final StateToken token, final String participant)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    return contentManager.addParticipant(user, contentId, participant);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = true)
  @KuneTransactional
  public Boolean addParticipants(final String userHash, final StateToken token, final String groupName,
      final SocialNetworkSubGroup subGroup) throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final Group group = groupManager.findByShortName(groupName);
    final User user = getCurrentUser();
    return contentManager.addParticipants(user, contentId, group, subGroup);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public StateContainerDTO addRoom(final String userHash, final StateToken parentToken,
      final String roomName) throws DefaultException {
    final User user = getCurrentUser();
    return getState(user, chatManager.addRoom(userHash, user, parentToken, roomName, ""));
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = true)
  @KuneTransactional
  public StateContentDTO copyContent(final String userHash, final StateToken parentToken,
      final StateToken token) throws DefaultException {
    final User user = getCurrentUser();
    final Content contentToCopy = finderService.getContent(ContentUtils.parseId(token.getDocument()));
    final Container container = finderService.getContainer(ContentUtils.parseId(parentToken.getFolder()));
    if (rightsService.get(user, contentToCopy.getAccessLists()).isVisible()) {
      return mapper.map(creationService.copyContent(user, container, contentToCopy),
          StateContentDTO.class);
    } else {
      throw new AccessViolationException();
    }
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
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
  @KuneTransactional
  public StateContainerDTO delContent(final String userHash, final StateToken token)
      throws DefaultException {
    final boolean isContent = token.hasAll();
    Container previousParent;
    if (isContent) {
      final Content content = setStatusInTheDustbin(token);
      previousParent = content.getContainer();
    } else {
      previousParent = finderService.getContainer(token.getFolder());
    }
    final Group group = previousParent.getOwner();
    if (!containerManager.hasTrashFolder(group)) {
      groupManager.initTrash(group);
    }
    final Container trash = containerManager.getTrashFolder(group);
    return moveContent(userHash, token, trash.getStateToken());
  }

  @Override
  @Authenticated(mandatory = false)
  @KuneTransactional
  public StateAbstractDTO getContent(final String userHash, final StateToken token)
      throws DefaultException {
    Group defaultGroup;
    final User user = getCurrentUser();
    if (isUserLoggedIn()) {
      defaultGroup = groupManager.getGroupOfUserWithId(user.getId());
      if (groupManager.findEnabledTools(defaultGroup.getId()).size() <= 1) {
        // 1, because the trash
        // Groups with no homepage
        defaultGroup = groupManager.getSiteDefaultGroup();
      }
    } else {
      defaultGroup = groupManager.getSiteDefaultGroup();
    }
    try {
      final Content content = finderService.getContentOrDefContent(token, defaultGroup);
      // See AccessRol.Viewer in @Authorizated annotation of
      // getContentorDefContentent

      return getContentOrDefContent(userHash, content.getStateToken(), user, content);
    } catch (final NoResultException e) {
      throw new ContentNotFoundException();
    } catch (final ToolNotFoundException e) {
      throw new ContentNotFoundException();
    } catch (final NoDefaultContentException e) {
      return mapper.map(
          stateService.createNoHome(user, token.hasNothing() ? user.getShortName() : token.getGroup()),
          StateNoContentDTO.class);
    }
  }

  @Override
  @Authenticated
  @KuneTransactional
  public StateAbstractDTO getContentByWaveRef(final String userHash, final String waveRef) {
    final User user = getCurrentUser();
    try {
      // FIXME get this from a wave constant
      String root = "/~/conv+root";
      final Content content = finderService.getContainerByWaveRef(waveRef.endsWith("/~/conv+root") ? waveRef
          : waveRef + root);
      accessService.accessToContent(content, user, AccessRol.Viewer);
      return mapState(stateService.create(user, content), user);
    } catch (javax.persistence.NoResultException e) {
      return new StateNoContentDTO();
    }

  }

  private StateAbstractDTO getContentOrDefContent(final String userHash, final StateToken stateToken,
      final User user, final Content content) {
    final Long id = content.getId();

    if (id != null) {
      // Content
      accessService.accessToContent(content, user, AccessRol.Viewer);
      return mapState(stateService.create(user, content), user);
    } else {
      // Container
      // Dirty hack in finderService to pass only a content object
      final Container container = content.getContainer();
      accessService.accessToContainer(container, user, AccessRol.Viewer);
      return mapState(stateService.create(user, container), user);
    }
  }

  private User getCurrentUser() {
    return userSession.getUser();
  }

  public StateContainerDTO getState(final Container container) {
    return getState(getCurrentUser(), container);
  }

  public StateContentDTO getState(final Content content) {
    return getState(getCurrentUser(), content);
  }

  public StateContainerDTO getState(final User user, final Container container) {
    final StateContainer state = stateService.create(user, container);
    return mapState(state, user);
  }

  public StateContentDTO getState(final User user, final Content content) {
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
  @KuneTransactional
  public TagCloudResult getSummaryTags(final String userHash, final StateToken groupToken) {
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    return getSummaryTags(group);
  }

  private boolean isUserLoggedIn() {
    return userSession.isUserLoggedIn();
  }

  private void mapContentRightsInstate(final User user, final AccessLists groupAccessList,
      final ContentSimpleDTO siblingDTO) {
    final Content sibling = contentManager.find(siblingDTO.getId());
    final AccessLists lists = sibling.hasAccessList() ? sibling.getAccessLists() : groupAccessList;
    siblingDTO.setRights(mapper.map(rightsService.get(user, lists), AccessRights.class));
  }

  private StateContainerDTO mapState(final StateContainer state, final User user) {
    final StateContainerDTO stateDTO = state instanceof StateEventContainer ? mapper.map(state,
        StateEventContainerDTO.class) : mapper.map(state, StateContainerDTO.class);

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
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateContainerDTO moveContent(final String userHash, final StateToken movedToken,
      final StateToken newContainerToken) throws DefaultException {
    final User user = getCurrentUser();
    final boolean toTrash = TrashServerUtils.isTrash(newContainerToken);

    // FIXME: this not should be here ... should be in the managers

    // Search the container id (because sometimes we get only #group.tool
    // tokens)
    final Long newContainerId = newContainerToken.hasGroupToolAndFolder() ? ContentUtils.parseId(newContainerToken.getFolder())
        : finderService.findByRootOnGroup(newContainerToken.getGroup(), newContainerToken.getTool()).getContainer().getId();
    try {
      if (movedToken.isComplete()) {
        final Long contentId = ContentUtils.parseId(movedToken.getDocument());
        final Content content = accessService.accessToContent(contentId, user, AccessRol.Editor);

        if (content.equals(content.getContainer().getOwner().getDefaultContent())) {
          throw new CannotDeleteDefaultContentException();
        }

        final Container newContainer = accessService.accessToContainer(newContainerId, user,
            AccessRol.Editor);

        final Container oldContainer = content.getContainer();
        if (toTrash) {
          // Remove operation
          setStatusInTheDustbin(movedToken);
        }
        contentManager.moveContent(content, newContainer);
        return getState(user, oldContainer);
      } else if (movedToken.hasGroupToolAndFolder()) {
        // Folder to folder move
        final Container container = accessService.accessToContainer(
            ContentUtils.parseId(movedToken.getFolder()), user, AccessRol.Editor);
        final Container oldParent = container.getParent();
        if (toTrash && !container.isLeaf()) {
          throw new ContainerNotEmptyException();
        }
        final Container newContainer = accessService.accessToContainer(newContainerId, user,
            AccessRol.Editor);
        containerManager.moveContainer(container, newContainer);
        return getState(user, finderService.getContainer(oldParent.getId()));
      } else {
        throw new ContentNotPermittedException();
      }
    } catch (final NoResultException e) {
      throw new AccessViolationException();
    }
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public StateContainerDTO purgeAll(final String userHash, final StateToken token) {
    final User user = getCurrentUser();
    final Long containerId = ContentUtils.parseId(token.getFolder());
    final Container container = finderService.getContainer(containerId);
    containerManager.purgeAll(container);
    contentManager.purgeAll(container);
    return mapState(stateService.create(user, container), user);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public StateContainerDTO purgeContent(final String userHash, final StateToken token) {
    final User user = getCurrentUser();
    if (token.isComplete()) {
      final Long contentId = ContentUtils.parseId(token.getDocument());
      final Container container = contentManager.purgeContent(finderService.getContent(contentId));
      return mapState(stateService.create(user, container), user);
    } else {
      final Long containerId = ContentUtils.parseId(token.getFolder());
      final Container container = containerManager.purgeContainer(finderService.getContainer(containerId));
      return mapState(stateService.create(user, container), user);
    }
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer)
  @KuneTransactional
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
  @KuneTransactional
  public void removeAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.removeAuthor(user, contentId, authorShortName);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public StateAbstractDTO renameContainer(final String userHash, final StateToken token,
      final String newName) throws DefaultException {
    renameFolder(token.getGroup(), ContentUtils.parseId(token.getFolder()), newName);
    return getContent(userHash, token);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
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

  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  // Not used with wave
  public void save(final String userHash, final StateToken token, final String textContent)
      throws DefaultException {
    return;
    // final Long contentId = ContentUtils.parseId(token.getDocument());
    // final User user = getCurrentUser();
    // final Content content = accessService.accessToContent(contentId, user,
    // AccessRol.Editor);
    // contentManager.save(user, content, textContent);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public String sendFeedback(final String userHash, final String title, final String body)
      throws DefaultException {
    final User user = getCurrentUser();
    return waveManager.sendFeedback(user, title, body);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public ContentSimpleDTO setAsDefaultContent(final String userHash, final StateToken token) {
    final Content content = contentManager.find(ContentUtils.parseId(token.getDocument()));
    groupManager.setDefaultContent(token.getGroup(), content);
    return mapper.map(content, ContentSimpleDTO.class);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.container, accessRolRequired = AccessRol.Editor)
  @KuneTransactional
  public void setGadgetProperties(final String userHash, final StateToken currentStateToken,
      final String gadgetName, final Map<String, String> properties) {
    final User user = getCurrentUser();
    final Content content = accessService.accessToContent(
        ContentUtils.parseId(currentStateToken.getDocument()), user, AccessRol.Editor);
    contentManager.setGadgetProperties(user, content, gadgetName, properties);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public I18nLanguageDTO setLanguage(final String userHash, final StateToken token,
      final String languageCode) throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    return mapper.map(contentManager.setLanguage(user, contentId, languageCode), I18nLanguageDTO.class);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public void setPublishedOn(final String userHash, final StateToken token, final Date publishedOn)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    contentManager.setPublishedOn(user, contentId, publishedOn);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor)
  @KuneTransactional
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
  @KuneTransactional
  public StateAbstractDTO setStatusAsAdmin(final String userHash, final StateToken token,
      final ContentStatus status) {
    final Content content = contentManager.setStatus(ContentUtils.parseId(token.getDocument()),
        ContentStatus.valueOf(status.toString()));
    return getState(getCurrentUser(), content);
  }

  private Content setStatusInTheDustbin(final StateToken token) {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final Content content = finderService.getContent(contentId);
    contentManager.setStatus(contentId, ContentStatus.inTheDustbin);
    return content;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public TagCloudResult setTags(final String userHash, final StateToken token, final String tags)
      throws DefaultException {
    final Long contentId = ContentUtils.parseId(token.getDocument());
    final User user = getCurrentUser();
    final Group group = groupManager.findByShortName(token.getGroup());
    contentManager.setTags(user, contentId, tags);
    return getSummaryTags(group);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public String writeTo(final String userHash, final StateToken token, final boolean onlyToAdmins)
      throws DefaultException {
    final User user = getCurrentUser();
    return waveManager.writeTo(user, token.getGroup(), onlyToAdmins);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public String writeTo(final String userHash, final StateToken token, final boolean onlyToAdmins,
      final String title, final String message) throws DefaultException {
    final User user = getCurrentUser();
    return waveManager.writeTo(user, token.getGroup(), onlyToAdmins, title, message);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.content, accessRolRequired = AccessRol.Editor, mustCheckMembership = false)
  @KuneTransactional
  public String writeToParticipants(final String userHash, final StateToken token)
      throws DefaultException {
    final User user = getCurrentUser();
    final Content content = finderService.getContent(ContentUtils.parseId(token.getDocument()));
    return waveManager.writeToParticipants(content.getAuthors().get(0).getShortName(),
        user.getShortName(), content.getWaveId());
  }

}
