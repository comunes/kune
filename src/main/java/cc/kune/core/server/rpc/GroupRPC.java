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

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.ToolIsDefaultException;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.ReservedWordsRegistry;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupRPC.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupRPC implements RPC, GroupService {

  /** The content manager. */
  private final ContentManager contentManager;

  /** The content rpc. */
  private final ContentRPC contentRPC;

  /** The group manager. */
  private final GroupManager groupManager;

  /** The mapper. */
  private final KuneMapper mapper;

  /** The reserverd words. */
  private final ReservedWordsRegistry reserverdWords;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new group rpc.
   * 
   * @param userSessionManager
   *          the user session manager
   * @param groupManager
   *          the group manager
   * @param contentManager
   *          the content manager
   * @param mapper
   *          the mapper
   * @param reserverdWords
   *          the reserverd words
   * @param contentRPC
   *          the content rpc
   */
  @Inject
  public GroupRPC(final UserSessionManager userSessionManager, final GroupManager groupManager,
      final ContentManager contentManager, final KuneMapper mapper,
      final ReservedWordsRegistry reserverdWords, final ContentRPC contentRPC) {
    this.userSessionManager = userSessionManager;
    this.groupManager = groupManager;
    this.contentManager = contentManager;
    this.mapper = mapper;
    this.reserverdWords = reserverdWords;
    this.contentRPC = contentRPC;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#changeDefLicense(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.dto.LicenseDTO)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public void changeDefLicense(final String userHash, final StateToken groupToken,
      final LicenseDTO license) {
    final User user = getUserLogged();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    groupManager.changeDefLicense(user, group, license.getShortName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#changeGroupWsTheme(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public void changeGroupWsTheme(final String userHash, final StateToken groupToken, final String theme)
      throws DefaultException {
    final User user = getUserLogged();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    groupManager.changeWsTheme(user, group, theme);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#clearGroupBackImage(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public GroupDTO clearGroupBackImage(final String userHash, final StateToken token) {
    final Group group = groupManager.findByShortName(token.getGroup());
    groupManager.clearGroupBackImage(group);
    return mapper.map(group, GroupDTO.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#createNewGroup(java.lang.String
   * , cc.kune.core.shared.dto.GroupDTO, java.lang.String, java.lang.String,
   * java.lang.String[])
   */
  @Override
  @Authenticated
  @KuneTransactional(rollbackOn = DefaultException.class)
  public StateAbstractDTO createNewGroup(final String userHash, final GroupDTO groupDTO,
      final String publicDesc, final String tags, final String[] enabledTools) throws DefaultException {
    final User user = getUserLogged();
    reserverdWords.check(groupDTO.getShortName(), groupDTO.getLongName());
    final Group group = mapper.map(groupDTO, Group.class);
    final Group newGroup = groupManager.createGroup(group, user, publicDesc);
    // This is necessary?
    contentManager.save(newGroup.getDefaultContent());
    // contentManager.setTags(user, defContentId, tags);
    return contentRPC.getContent(userHash,
        newGroup.getDefaultContent().getStateToken().copy().clearDocument());
  };

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#getGroup(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  @Authenticated(mandatory = false)
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
  public GroupDTO getGroup(final String userHash, final StateToken groupToken) {
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    return mapper.map(group, GroupDTO.class);
  }

  /**
   * Gets the user logged.
   * 
   * @return the user logged
   */
  private User getUserLogged() {
    final User user = userSessionManager.getUser();
    return user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#setGroupNewMembersJoiningPolicy
   * (java.lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.AdmissionType)
   */
  @Override
  @Authenticated(mandatory = true)
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  public void setGroupNewMembersJoiningPolicy(final String userHash, final StateToken token,
      final AdmissionType admissionPolicy) {
    final Group group = groupManager.findByShortName(token.getGroup());
    group.setAdmissionType(AdmissionType.valueOf(admissionPolicy.toString()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#setSocialNetworkVisibility
   * (java.lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.SocialNetworkVisibility)
   */
  @Override
  @Authenticated(mandatory = true)
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @KuneTransactional
  public void setSocialNetworkVisibility(final String userHash, final StateToken token,
      final SocialNetworkVisibility visibility) {
    final Group group = groupManager.findByShortName(token.getGroup());
    group.getSocialNetwork().setVisibility(visibility);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#setToolEnabled(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken, java.lang.String, boolean)
   */
  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @KuneTransactional
  public void setToolEnabled(final String userHash, final StateToken groupToken, final String toolName,
      final boolean enabled) throws ToolIsDefaultException {
    groupManager.setToolEnabled(getUserLogged(), groupToken.getGroup(), toolName, enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.GroupService#updateGroup(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.dto.GroupDTO)
   */
  @Override
  @Authenticated
  @KuneTransactional
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  public StateAbstractDTO updateGroup(final String userHash, final StateToken token,
      final GroupDTO groupDTO) throws DefaultException {
    final Group group = groupManager.findByShortName(token.getGroup());
    final Long id = group.getId();
    if (!id.equals(groupDTO.getId()) || group.isPersonal()) {
      throw new AccessViolationException();
    }
    final Group updatedGroup = groupManager.update(id, groupDTO);
    return contentRPC.getContent(userHash, token.setGroup(updatedGroup.getShortName()));
  }
}
