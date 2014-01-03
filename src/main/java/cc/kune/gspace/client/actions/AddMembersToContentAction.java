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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AddMembersToContentAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AddMembersToContentAction extends RolAction {

  /** The content service. */
  private final ContentServiceHelper contentService;

  /** The session. */
  private final Session session;

  /** The sub group. */
  private SocialNetworkSubGroup subGroup;

  /**
   * Instantiates a new adds the members to content action.
   * 
   * @param session
   *          the session
   * @param contentService
   *          the content service
   */
  @Inject
  public AddMembersToContentAction(final Session session, final ContentServiceHelper contentService) {
    super(AccessRolDTO.Editor, true);
    this.session = session;
    this.contentService = contentService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    final StateToken token = session.getCurrentStateToken().hasAll() ? session.getCurrentStateToken()
        : ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
    contentService.addParticipants(token, subGroup, SimpleCallback.DO_NOTHING);
  }

  /**
   * Sets the sub group.
   * 
   * @param subGroup
   *          the new sub group
   */
  public void setSubGroup(final SocialNetworkSubGroup subGroup) {
    this.subGroup = subGroup;
  }

}
