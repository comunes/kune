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

package cc.kune.core.client.invitation;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupInvitationAction extends AbstractInvitateAction {

  public static final String CANCEL_ID = "k-group-inv-cancel-id";
  public static final String DIALOG_ID = "k-group-inv-diag-id";
  public static final String INVITE_ID = "k-group-inv-invite-id";
  public static final String TEXTBOX_ID = "k-group-inv-textbox-id";

  @Inject
  public GroupInvitationAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager,
      final Provider<InvitationServiceAsync> invitationService) {
    super(
        stateManager,
        session,
        rightsManager,
        invitationService,
        InvitationType.TO_GROUP,
        /* InvitationManagerDefault will fail if we change this */
        AccessRolDTO.Administrator,
        true,
        true,
        true,
        I18n.t("Send invitations to others via email"),
        I18n.t("You can invite others to join this group. Please provide here a comma-separated list of emails where the invitations will be sent:"),
        DIALOG_ID, TEXTBOX_ID, INVITE_ID, CANCEL_ID);
  }
}
