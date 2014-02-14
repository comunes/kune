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
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.notifier.Addressee;
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
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class InvitationRPC.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InvitationRPC implements RPC, InvitationService {

  /** The container manager. */
  private final ContainerManager containerManager;

  /** The group finder. */
  private final GroupFinder groupFinder;

  /** The invitation manager. */
  private final InvitationManager invitationManager;

  /** The mapper. */
  private final KuneMapper mapper;

  /** The user finder. */
  private final UserFinder userFinder;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new invitation rpc.
   *
   * @param invitationManager
   *          the invitation manager
   * @param userSessionManager
   *          the user session manager
   * @param mapper
   *          the mapper
   * @param groupFinder
   *          the group finder
   * @param containerManager
   *          the container manager
   */
  @Inject
  public InvitationRPC(final InvitationManager invitationManager,
      final UserSessionManager userSessionManager, final KuneMapper mapper,
      final GroupFinder groupFinder, final ContainerManager containerManager, final UserFinder userFinder) {
    this.containerManager = containerManager;
    this.invitationManager = invitationManager;
    this.userSessionManager = userSessionManager;
    this.mapper = mapper;
    this.groupFinder = groupFinder;
    this.userFinder = userFinder;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#confirmationInvitationToGroup
   * (java.lang.String, java.lang.String)
   */
  @Override
  public void confirmationInvitationToGroup(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    invitationManager.confirmInvitationToGroup(getUser(), invitationHash);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#confirmationInvitationToSite
   * (java.lang.String, java.lang.String)
   */
  @Override
  @Authenticated
  @KuneTransactional
  public void confirmationInvitationToSite(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    invitationManager.confirmInvitationToSite(getUser(), invitationHash);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#confirmInvitationToList
   * (java.lang.String, java.lang.String)
   */
  @Override
  public StateContainerDTO confirmInvitationToList(final String userHash, final String invitationHash)
      throws IncorrectHashException {
    return invitationManager.confirmInvitationToList(getUser(), invitationHash);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#getInvitation(java.lang
   * .String)
   */
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

  /**
   * Gets the user.
   *
   * @return the user
   */
  private User getUser() {
    return userSessionManager.getUser();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#inviteToGroup(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String[])
   */
  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteToGroup(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_GROUP, NotificationType.email, token, emails);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#inviteToList(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String[])
   */
  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteToList(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_LISTS, NotificationType.email, token, emails);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#inviteToSite(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String[])
   */
  @Override
  @Authenticated
  @KuneTransactional
  public void inviteToSite(final String userHash, final StateToken token, final String[] emails) {
    invitationManager.invite(getUser(), InvitationType.TO_SITE, NotificationType.email, token, emails);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#inviteUserToGroup(java
   * .lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String)
   */
  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteUserToGroup(final String userHash, final StateToken token, final String shortName) {
    /** invited user */
    final User to = userFinder.findByShortName(shortName);
    final String invitedEmail = Addressee.build(to).getAddress();
    invitationManager.invite(getUser(), InvitationType.TO_GROUP, NotificationType.email, token,
        invitedEmail);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.rpcservices.InvitationService#inviteUserToList(java
   * .lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String)
   */
  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public void inviteUserToList(final String userHash, final StateToken token, final String shortName) {
    final User to = userFinder.findByShortName(shortName);
    final String invitedEmail = Addressee.build(to).getAddress();
    invitationManager.invite(getUser(), InvitationType.TO_LISTS, NotificationType.email, token,
        invitedEmail);
  }
}
