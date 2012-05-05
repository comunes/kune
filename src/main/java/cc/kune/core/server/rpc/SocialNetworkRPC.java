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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.SocialNetService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

public class SocialNetworkRPC implements SocialNetService, RPC {

  private final GroupManager groupManager;
  private final Mapper mapper;
  private final NotificationService notifyService;
  private final SocialNetworkManager socialNetworkManager;
  private final UserFinder userFinder;
  private final UserSessionManager userSessionManager;

  @Inject
  public SocialNetworkRPC(final UserSessionManager userSessionManager, final GroupManager groupManager,
      final SocialNetworkManager socialNetworkManager, final Mapper mapper, final UserFinder userFinder,
      final NotificationService notifyService) {
    this.userSessionManager = userSessionManager;
    this.groupManager = groupManager;
    this.socialNetworkManager = socialNetworkManager;
    this.mapper = mapper;
    this.userFinder = userFinder;
    this.notifyService = notifyService;
  }

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
    socialNetworkManager.acceptJoinGroup(userLogged, groupToAccept, group);
    notifyService.notifyGroupMembers(groupToAccept, group, "Accepted as member",
        "You are now member of this group");
    // notifyService.notifyGroupAdmins(group, group, hash,
    // groupToAcceptShortName)
    // "%admin approved the membership of the %user in the group %group".
    // notifyService.notifyGroupAdmins(group, group, "Collaborator accepted",
    // "There is a pending collaborator in this group. Please accept or deny him/her");
    return generateResponse(userLogged, group);
  }

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
    socialNetworkManager.addGroupToAdmins(userLogged, groupToAdd, group);
    notifyService.notifyGroupMembers(groupToAdd, group, "Added as administrator",
        "You are now admin of this group");
    return generateResponse(userLogged, group);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public void addAsBuddie(final String hash, final String userName) throws DefaultException {
    socialNetworkManager.addAsBuddie(userSessionManager.getUser(), userFinder.findByShortName(userName));
  }

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
    socialNetworkManager.addGroupToCollabs(userLogged, groupToAdd, group);
    notifyService.notifyGroupMembers(groupToAdd, group, "Added as collaborator",
        "You are now a collaborator of this group");
    return generateResponse(userLogged, group);
  }

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
    socialNetworkManager.addGroupToViewers(userLogged, groupToAdd, group);
    return generateResponse(userLogged, group);
  }

  private void checkIsNotPersonalGroup(final Group group) {
    if (group.isPersonal()) {
      throw new DefaultException();
    }
    ;
  }

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
    socialNetworkManager.deleteMember(userLogged, groupToDelete, group);
    notifyService.notifyGroupMembers(groupToDelete, group, "Removed as collaborator",
        "You have been remove as collaborator of this group");
    return generateResponse(userLogged, group);
  }

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
    socialNetworkManager.denyJoinGroup(userLogged, groupToDenyJoin, group);
    notifyService.notifyGroupMembers(groupToDenyJoin, group, "Membership denied",
        "Your membership to this group has been rejected");
    return generateResponse(userLogged, group);
  }

  private SocialNetworkDataDTO generateResponse(final User userLogged, final Group group) {
    return mapper.map(socialNetworkManager.getSocialNetworkData(userLogged, group),
        SocialNetworkDataDTO.class);
  }

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

  @Override
  @Authenticated
  @KuneTransactional
  public SocialNetworkRequestResult requestJoinGroup(final String hash, final StateToken groupToken)
      throws DefaultException {
    final User user = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    if (!group.getAdmissionType().equals(AdmissionType.Open)) {
      notifyService.notifyGroupAdmins(group, group, "Pending collaborator",
          "There is a pending collaborator in this group. Please accept or deny him/her");
    }
    return socialNetworkManager.requestToJoin(user, group);
  }

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
    socialNetworkManager.setAdminAsCollab(userLogged, groupToSetCollab, group);
    notifyService.notifyGroupMembers(groupToSetCollab, group, "Membership changed",
        "Your membership to this group have changed. You are now a collaborator of this group");
    return generateResponse(userLogged, group);
  }

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
    socialNetworkManager.setCollabAsAdmin(userLogged, groupToSetAdmin, group);
    notifyService.notifyGroupMembers(groupToSetAdmin, group, "Membership changed",
        "Your membership to this group have changed. You are now an admin of this group");
    return generateResponse(userLogged, group);
  }

  @Override
  @Authenticated
  @KuneTransactional
  public void unJoinGroup(final String hash, final StateToken groupToken) throws DefaultException {
    final User userLogged = userSessionManager.getUser();
    final Group group = groupManager.findByShortName(groupToken.getGroup());
    checkIsNotPersonalGroup(group);
    socialNetworkManager.unJoinGroup(userLogged.getUserGroup(), group);
    notifyService.notifyGroupAdmins(group, userLogged.getUserGroup(), "Some member left this group",
        "Some member have left this group");
  }
}
