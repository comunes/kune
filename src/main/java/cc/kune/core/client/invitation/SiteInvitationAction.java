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

// TODO: Auto-generated Javadoc
/**
 * The Class SiteInvitationAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SiteInvitationAction extends AbstractInvitateAction {

  /** The Constant CANCEL_ID. */
  public static final String CANCEL_ID = "k-site-inv-cancel-id";

  /** The Constant DIALOG_ID. */
  public static final String DIALOG_ID = "k-site-inv-diag-id";

  /** The Constant INVITE_ID. */
  public static final String INVITE_ID = "k-site-inv-invite-id";

  /** The Constant TEXTBOX_ID. */
  public static final String TEXTBOX_ID = "k-site-inv-textbox-id";

  /**
   * Instantiates a new site invitation action.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param rightsManager
   *          the rights manager
   * @param invitationService
   *          the invitation service
   */
  @Inject
  public SiteInvitationAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager,
      final Provider<InvitationServiceAsync> invitationService) {
    super(
        stateManager,
        session,
        rightsManager,
        invitationService,
        InvitationType.TO_SITE,
        AccessRolDTO.Viewer,
        true,
        true,
        true,
        I18n.t("Send invitations to others via email"),
        I18n.t("You can invite others to join this site. Please provide here a comma-separated list of emails where the invitations will be sent:"),
        DIALOG_ID, TEXTBOX_ID, INVITE_ID, CANCEL_ID);
  }
}
