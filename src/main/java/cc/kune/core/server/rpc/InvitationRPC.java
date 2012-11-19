/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.client.rpcservices.InvitationService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InvitationDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;

public class InvitationRPC implements RPC, InvitationService {

  private final ContainerManager containerManager;
  private final GroupFinder groupFinder;
  private final InvitationManager invitationManager;
  private final Mapper mapper;
  private final UserSessionManager userSessionManager;

  @Inject
  public InvitationRPC(final InvitationManager invitationManager,
      final UserSessionManager userSessionManager, final Mapper mapper, final GroupFinder groupFinder,
      final ContainerManager containerManager) {
    this.containerManager = containerManager;
    this.invitationManager = invitationManager;
    this.userSessionManager = userSessionManager;
    this.mapper = mapper;
    this.groupFinder = groupFinder;
  }

  @Override
  public void confirmationInvitationToGroup(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    invitationManager.confirmInvitationToGroup(getUser(), invitationHash);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public void confirmationInvitationToSite(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    invitationManager.confirmInvitationToSite(getUser(), invitationHash);
  }

  @Override
  public StateContainerDTO confirmInvitationToList(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    return invitationManager.confirmInvitationToList(getUser(), invitationHash);
  }

  @Override
  @KuneTransactional
  public InvitationDTO getInvitation(final String invitationHash) throws IncorrectHashException {
    final InvitationDTO map = mapper.map(invitationManager.get(invitationHash), InvitationDTO.class);
    final StateToken token = new StateToken(map.getInvitedToToken());
    switch (map.getType()) {
    case TO_GROUP:
      final Group group = groupFinder.findByShortName(token.getGroup());
      map.setName(group.getShortName());
      map.setDescription(group.getLongName());
      break;
    case TO_LISTS:
      final Group groupOfList = groupFinder.findByShortName(token.getGroup());
      final Container cnt = containerManager.find(ContentUtils.parseId(token.getFolder()));
      map.setName(cnt.getName());
      map.setDescription(groupOfList.getLongName());
    default:
      break;
    }
    return map;
  }

  private User getUser() {
    return userSessionManager.getUser();
  }

  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteToGroup(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_GROUP, NotificationType.email, token, emails);
  }

  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteToList(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_LISTS, NotificationType.email, token, emails);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public void inviteToSite(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_SITE, NotificationType.email, token, emails);
  }
}
