package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.Action;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.state.*;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

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

/**
 * The class ListInviteUserAction. The action of inviting an existing user to a
 * kune list.
 *
 * @author Antonio Tenorio Fornés <antoniotenorio@ucm.es>
 *
 */
public class ListInviteUserAction extends AbstractInviteUserAction {

  /**
   * @param stateManager
   * @param session
   * @param rightsManager
   * @param res
   * @param invitationService
   */
  @Inject
  public ListInviteUserAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager, final ListInviteUserSearchPanel searchPanel,
      final IconicResources res, final Provider<InvitationServiceAsync> invitationService) {
    super(stateManager, session, rightsManager, AccessRolDTO.Administrator, true, false, true,
        searchPanel, res, InvitationType.TO_LISTS, invitationService);
    putValue(NAME, I18n.t("Invite some other user of [%s] to this list", I18n.getSiteCommonName()));
    putValue(Action.SMALL_ICON, res.add());
  }

}
