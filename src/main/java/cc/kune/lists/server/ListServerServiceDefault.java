/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

package cc.kune.lists.server;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.rpc.ContentRPC;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ListServerServiceDefault implements ListServerService {
  private final ContainerManager contentManager;
  private final ContentRPC contentRPC;
  private final UserSessionManager userSessionManager;

  @Inject
  public ListServerServiceDefault(final ContentRPC contentRPC,
      final UserSessionManager userSessionManager, final ContainerManager contentManager) {
    this.contentRPC = contentRPC;
    this.userSessionManager = userSessionManager;
    this.contentManager = contentManager;

  }

  private String composeListName(final StateToken parentToken, final String listname) {
    // FIXME In the future use tags here
    return parentToken.getGroup() + "-" + listname;
  }

  @Override
  public StateContainerDTO createList(final String userHash, final StateToken parentToken,
      final String listName, final String description, final boolean isPublic) {
    final StateContainerDTO newList = contentRPC.addFolder(userHash, parentToken,
        composeListName(parentToken, listName), ListsToolConstants.TYPE_LIST);
    setContainerAcl(newList.getStateToken(), isPublic);
    // Not public list, don't permit subscriptions neither view posts
    return subscribeCurrentUserToList(newList.getStateToken(), true);
  }

  private Container getContainer(final StateToken token) {
    final Container container = contentManager.find(ContentUtils.parseId(token.getFolder()));
    if (!container.getTypeId().equals(ListsToolConstants.TYPE_LIST)) {
      throw new RuntimeException("This container is not a list!");
    }
    return container;
  }

  private Group getUserGroup() {
    return userSessionManager.getUser().getUserGroup();
  }

  @Override
  public StateContentDTO newPost(final String userHash, final StateToken parentToken,
      final String postTitle) {
    final Container container = getContainer(parentToken);
    final StateContentDTO content = contentRPC.addContent(userHash, parentToken,
        "[" + container.getName() + "] " + postTitle, ListsToolConstants.TYPE_POST);
    // FIXME In the future use tabs here
    return content;
  }

  private Container setContainerAcl(final StateToken token, final boolean isPublic) {
    final Container container = getContainer(token);
    final AccessLists acl = new AccessLists();
    acl.getAdmins().setMode(GroupListMode.NORMAL);
    acl.getAdmins().add(getUserGroup());
    acl.getEditors().setMode(isPublic ? GroupListMode.EVERYONE : GroupListMode.NOBODY);
    setViewersAcl(isPublic, acl);
    contentManager.setAccessList(container, acl);
    return container;
  }

  @Override
  public StateContainerDTO setPublic(final StateToken token, final Boolean isPublic) {
    final Container container = setPublicAcl(token, isPublic);
    return contentRPC.getState(container);
  }

  private Container setPublicAcl(final StateToken token, final boolean isPublic) {
    final Container container = getContainer(token);
    final AccessLists acl = container.getAccessLists();
    setViewersAcl(isPublic, acl);
    contentManager.setAccessList(container, acl);
    return container;
  }

  private void setViewersAcl(final boolean isPublic, final AccessLists acl) {
    acl.getViewers().clear();
    acl.getViewers().setMode(isPublic ? GroupListMode.EVERYONE : GroupListMode.NOBODY);
  }

  @Override
  public StateContainerDTO subscribeCurrentUserToList(final StateToken token, final Boolean subscribe) {
    final Group userGroup = getUserGroup();
    return subscribeToList(token, subscribe, userGroup);
  }

  private StateContainerDTO subscribeToList(final StateToken token, final Boolean subscribe,
      final Group userGroup) {
    final Container container = getContainer(token);
    final AccessLists acl = container.getAccessLists();
    if (subscribe) {
      if (!acl.getAdmins().includes(userGroup)
          && acl.getViewers().getMode().equals(GroupListMode.NOBODY)) {
        // Not public list, don't permit subscriptions neither view posts
        throw new AccessViolationException();
      } else {
        acl.getEditors().add(userGroup);
      }
    } else {
      acl.getEditors().remove(userGroup);
    }
    contentManager.setAccessList(container, acl);
    return contentRPC.getState(container);
  }

  /**
   * This is used by invitations when confirmed
   */
  @Override
  public StateContainerDTO subscribeToListWithoutPermCheck(final StateToken token,
      final Group userGroup, final Boolean subscribe) {
    final Container container = getContainer(token);
    final AccessLists acl = container.getAccessLists();
    if (subscribe) {
      acl.getEditors().add(userGroup);
    } else {
      acl.getEditors().add(userGroup);
    }
    contentManager.setAccessList(container, acl);
    return contentRPC.getState(container);
  }
}
