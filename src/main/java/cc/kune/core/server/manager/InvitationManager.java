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

package cc.kune.core.server.manager;

import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Invitation;
import cc.kune.domain.User;

public interface InvitationManager extends Manager<Invitation, Long> {

  /**
   * Gets the invitation via the hash
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
