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

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.mapper.Mapper;
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
import com.google.inject.persist.Transactional;

public class GroupRPC implements RPC, GroupService {
  private final ContentManager contentManager;
  private final ContentRPC contentRPC;
  private final GroupManager groupManager;
  private final Mapper mapper;
  private final ReservedWordsRegistry reserverdWords;
  private final UserSessionManager userSessionManager;

  @Inject
  public GroupRPC(final UserSessionManager userSessionManager, final GroupManager groupManager,
      final ContentManager contentManager, final Mapper mapper,
      final ReservedWordsRegistry reserverdWords, final ContentRPC contentRPC) {
    this.userSessionManager = userSessionManager;
    this.groupManager = groupManager;
    this.contentManager = contentManager;
    this.mapper = mapper;
    this.reserverdWords = reserverdWords;
    this.contentRPC = contentRPC;
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @Transactional
  public void changeDefLicense(final String userHash, final StateToken groupToken,
      final LicenseDTO license) {
    final User user = getUserLogged();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    groupManager.changeDefLicense(user, group, license.getShortName());
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @Transactional
  public void changeGroupWsTheme(final String userHash, final StateToken groupToken, final String theme)
      throws DefaultException {
    final User user = getUserLogged();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    groupManager.changeWsTheme(user, group, theme);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @Transactional
  public GroupDTO clearGroupBackImage(final String userHash, final StateToken token) {
    final Group group = groupManager.findByShortName(token.getGroup());
    groupManager.clearGroupBackImage(group);
    return mapper.map(group, GroupDTO.class);
  }

  @Override
  @Authenticated
  @Transactional(rollbackOn = DefaultException.class)
  public StateAbstractDTO createNewGroup(final String userHash, final GroupDTO groupDTO,
      final String publicDesc, final String tags, final String[] enabledTools) throws DefaultException {
    final User user = getUserLogged();
    reserverdWords.check(groupDTO.getShortName(), groupDTO.getLongName());
    final Group group = mapper.map(groupDTO, Group.class);
    final Group newGroup = groupManager.createGroup(group, user, publicDesc);
    // This is necessary?
    contentManager.save(newGroup.getDefaultContent());
    // contentManager.setTags(user, defContentId, tags);
    return contentRPC.getContent(userHash, newGroup.getDefaultContent().getStateToken());
  };

  @Override
  @Authenticated(mandatory = false)
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Viewer)
  public GroupDTO getGroup(final String userHash, final StateToken groupToken) {
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    return mapper.map(group, GroupDTO.class);
  }

  private User getUserLogged() {
    final User user = userSessionManager.getUser();
    return user;
  }

  @Override
  @Authenticated(mandatory = true)
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @Transactional
  public void setGroupNewMembersJoiningPolicy(final String userHash, final StateToken token,
      final AdmissionType admissionPolicy) {
    final Group group = groupManager.findByShortName(token.getGroup());
    group.setAdmissionType(AdmissionType.valueOf(admissionPolicy.toString()));
  }

  @Override
  @Authenticated(mandatory = true)
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @Transactional
  public void setSocialNetworkVisibility(final String userHash, final StateToken token,
      final SocialNetworkVisibility visibility) {
    final Group group = groupManager.findByShortName(token.getGroup());
    group.getSocialNetwork().setVisibility(visibility);
  }

  @Override
  @Authenticated
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  @Transactional
  public void setToolEnabled(final String userHash, final StateToken groupToken, final String toolName,
      final boolean enabled) {
    groupManager.setToolEnabled(getUserLogged(), groupToken.getGroup(), toolName, enabled);
  }

  @Override
  @Authenticated
  @Transactional
  @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
  public StateAbstractDTO updateGroup(final String userHash, final StateToken token,
      final GroupDTO groupDTO) throws DefaultException {
    final Group group = groupManager.findByShortName(token.getGroup());
    final Long id = group.getId();
    if (!id.equals(groupDTO.getId())) {
      throw new AccessViolationException();
    }
    final Group updatedGroup = groupManager.update(id, groupDTO);
    return contentRPC.getContent(userHash, token.setGroup(updatedGroup.getShortName()));
  }
}
