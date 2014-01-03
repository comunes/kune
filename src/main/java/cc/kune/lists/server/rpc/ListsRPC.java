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
package cc.kune.lists.server.rpc;

import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.rpc.RPC;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.domain.Group;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.server.ListServerService;

import com.google.inject.Inject;

public class ListsRPC implements ListsService, RPC {

  private final GroupFinder groupFinder;
  private final ListServerService listService;

  @Inject
  public ListsRPC(final ListServerService listService, final GroupFinder groupFinder) {
    this.listService = listService;
    this.groupFinder = groupFinder;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.container)
  @KuneTransactional
  public StateContainerDTO createList(final String userHash, final StateToken parentToken,
      final String listName, final String description, final boolean isPublic) {
    return listService.createList(userHash, parentToken, listName, description, isPublic);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.container)
  @KuneTransactional
  public StateContentDTO newPost(final String userHash, final StateToken parentToken,
      final String postTitle) {
    return listService.newPost(userHash, parentToken, postTitle);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.container)
  @KuneTransactional
  public StateContainerDTO setPublic(final String hash, final StateToken token, final Boolean isPublic) {
    return listService.setPublic(token, isPublic);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.container)
  @KuneTransactional
  public StateContainerDTO subscribeAnUserToList(final String hash, final StateToken token,
      final String newSubscriber, final Boolean subscribe) {
    final Group newSubscriberGroup = groupFinder.findByShortName(newSubscriber);
    // Without checking perms because {@link AuthorizatedMethodInterceptor} do
    // this
    return listService.subscribeToListWithoutPermCheck(token, newSubscriberGroup, subscribe);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.container)
  @KuneTransactional
  public StateContainerDTO subscribeMyselfToList(final String userHash, final StateToken token,
      final Boolean subscribe) {
    return listService.subscribeCurrentUserToList(token, subscribe);
  }

}
