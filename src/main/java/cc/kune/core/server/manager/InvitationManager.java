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

package cc.kune.core.server.manager;

import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.domain.Invitation;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface InvitationManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface InvitationManager extends Manager<Invitation, Long> {

  /**
   * Confirm group invitation.
   * 
   * @param user
   *          the user who received the invitation
   * @param invitationHash
   *          the invitation hash
   * @return the social network data
   */
  SocialNetworkDataDTO confirmInvitationToGroup(User user, String invitationHash);

  /**
   * Confirm list invitation.
   * 
   * @param user
   *          the user who received the invitation
   * @param invitationHash
   *          the invitation hash
   * @return the state container
   */
  StateContainerDTO confirmInvitationToList(User user, String invitationHash);

  /**
   * Confirm site invitation.
   * 
   * @param user
   *          the user who received the invitation
   * @param invitationHash
   *          the invitation hash
   */
  void confirmInvitationToSite(User user, String invitationHash);

  /**
   * Delete older invitations.
   */
  void deleteOlderInvitations();

  /**
   * Gets the invitation via the hash.
   * 
   * @param invitationHash
   *          the invitation hash
   * @return the invitation
   */
  Invitation get(String invitationHash);

  /**
   * Invite some emails to the site or a group, or a list.
   * 
   * @param from
   *          the user inviting
   * @param type
   *          the type of invitation (to group/list/etc)
   * @param notifType
   *          the notif type (email,chat,wave)
   * @param toToken
   *          the to token
   * @param emails
   *          the list of emails to invite to
   */
  void invite(User from, InvitationType type, NotificationType notifType, StateToken toToken,
      String... emails);
}
