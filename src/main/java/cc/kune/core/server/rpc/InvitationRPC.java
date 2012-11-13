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

import cc.kune.core.client.rpcservices.InvitationService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class InvitationRPC implements RPC, InvitationService {

  private final InvitationManager invitationManager;
  private final UserSessionManager userSessionManager;

  @Inject
  public InvitationRPC(final InvitationManager invitationManager,
      final UserSessionManager userSessionManager) {
    this.invitationManager = invitationManager;
    this.userSessionManager = userSessionManager;
  }

  private User getUser() {
    return userSessionManager.getUser();
  }

  @Override
  @Authenticated
  public void invite(final String userHash, final InvitationType type, final StateToken token,
      final String[] emails) {
    invitationManager.invite(getUser(), type, NotificationType.email, token, emails);
  }

}
