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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.SocialNetService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkRPC.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkRPC implements SocialNetService, RPC {

  /** The group manager. */
  private final GroupManager groupManager;

  /** The sn manager. */
  private final SocialNetworkManager snManager;

  /** The user finder. */
  private final UserFinder userFinder;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new social network rpc.
   * 
   * @param userSessionManager
   *          the user session manager
   * @param groupManager
   *          the group manager
   * @param socialNetworkManager
   *          the social network manager
   * @param userFinder
   *          the user finder
   */
  @Inject
  public SocialNetworkRPC(final UserSessionManager userSessionManager, final GroupManager groupManager,
      final SocialNetworkManager socialNetworkManager, final UserFinder userFinder) {
    this.userSessionManager = userSessionManager;
    this.groupManager = groupManager;
    this.snManager = socialNetworkManager;
    this.userFinder = userFinder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#acceptJoinGroup(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO acceptJoinGroup(final String hash, final StateToken groupToken,
      final String groupToAcceptShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToAccept = groupManager.findByShortName(groupToAcceptShortName);
    snManager.acceptJoinGroup(userLogged, groupToAccept, group);

    // notifyService.notifyGroupAdmins(group, group, hash,
    // groupToAcceptShortName)
    // "%admin approved the membership of the %user in the group %group".
    // notifyService.notifyGroupAdmins(group, group, "Collaborator accepted",
    // "There is a pending collaborator in this group. Please accept or deny him/her");
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#addAdminMember(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO addAdminMember(final String hash, final StateToken groupToken,
      final String groupToAddShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
    snManager.addGroupToAdmins(userLogged, groupToAdd, group);
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#addAsBuddie(java.lang.
   * String, java.lang.String)
   */
  @Override
  @Authenticated
  @KuneTransactional
  public void addAsBuddie(final String hash, final String userName) throws DefaultException {
    snManager.addAsBuddie(userSessionManager.getUser(), userFinder.findByShortName(userName));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#addCollabMember(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO addCollabMember(final String hash, final StateToken groupToken,
      final String groupToAddShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
    snManager.addGroupToCollabs(userLogged, groupToAdd, group);
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#addViewerMember(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO addViewerMember(final String hash, final StateToken groupToken,
      final String groupToAddShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
    snManager.addGroupToViewers(userLogged, groupToAdd, group);
    return generateResponse(userLogged, group);
  }

  /**
   * Check is not personal group.
   * 
   * @param group
   *          the group
   */
  private void checkIsNotPersonalGroup(final Group group) {
    if (group.isPersonal()) {
      throw new DefaultException();
    }
    ;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#deleteMember(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO deleteMember(final String hash, final StateToken groupToken,
      final String groupToDeleleShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToDelete = groupManager.findByShortName(groupToDeleleShortName);
    snManager.deleteMember(userLogged, groupToDelete, group);
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#denyJoinGroup(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO denyJoinGroup(final String hash, final StateToken groupToken,
      final String groupToDenyShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToDenyJoin = groupManager.findByShortName(groupToDenyShortName);
    snManager.denyJoinGroup(userLogged, groupToDenyJoin, group);
    return generateResponse(userLogged, group);
  }

  /**
   * Generate response.
   * 
   * @param userLogged
   *          the user logged
   * @param group
   *          the group
   * @return the social network data dto
   */
  private SocialNetworkDataDTO generateResponse(final User userLogged, final Group group) {
    return snManager.generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#getSocialNetwork(java.
   * lang.String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  @Authenticated(mandatory = false)
  // At least you can access as Viewer to the Group
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
  @KuneTransactional
  public SocialNetworkDataDTO getSocialNetwork(final String hash, final StateToken groupToken)
      throws DefaultException {
    final User user = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    return generateResponse(user, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#requestJoinGroup(java.
   * lang.String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  @Authenticated
  @KuneTransactional
  public SocialNetworkRequestResult requestJoinGroup(final String hash, final StateToken groupToken)
      throws DefaultException {
    final User user = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);

    return snManager.requestToJoin(user, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#setAdminAsCollab(java.
   * lang.String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO setAdminAsCollab(final String hash, final StateToken groupToken,
      final String groupToSetCollabShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToSetCollab = groupManager.findByShortName(groupToSetCollabShortName);
    snManager.setAdminAsCollab(userLogged, groupToSetCollab, group);
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#setCollabAsAdmin(java.
   * lang.String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public SocialNetworkDataDTO setCollabAsAdmin(final String hash, final StateToken groupToken,
      final String groupToSetAdminShortName) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    final Group groupToSetAdmin = groupManager.findByShortName(groupToSetAdminShortName);
    snManager.setCollabAsAdmin(userLogged, groupToSetAdmin, group);
    return generateResponse(userLogged, group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.SocialNetService#unJoinGroup(java.lang.
   * String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  @Authenticated
  @KuneTransactional
  public void unJoinGroup(final String hash, final StateToken groupToken) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    snManager.unJoinGroup(userLogged.getUserGroup(), group);
  }
}
