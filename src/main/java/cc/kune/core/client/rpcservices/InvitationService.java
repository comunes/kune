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
package cc.kune.core.client.rpcservices;

import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InvitationDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface InvitationService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("InvitationService")
public interface InvitationService extends RemoteService {

  /**
   * Confirmation invitation to group.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @throws IncorrectHashException
   *           the incorrect hash exception
   */
  void confirmationInvitationToGroup(String userHash, String invitationHash)
      throws IncorrectHashException;

  /**
   * Confirmation invitation to site.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @throws IncorrectHashException
   *           the incorrect hash exception
   */
  void confirmationInvitationToSite(String userHash, String invitationHash)
      throws IncorrectHashException;

  /**
   * Confirm invitation to list.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @return the state container dto
   * @throws IncorrectHashException
   *           the incorrect hash exception
   */
  StateContainerDTO confirmInvitationToList(String userHash, String invitationHash)
      throws IncorrectHashException;

  /**
   * Gets the invitation.
   * 
   * @param invitationHash
   *          the invitation hash
   * @return the invitation
   * @throws IncorrectHashException
   *           the incorrect hash exception
   */
  InvitationDTO getInvitation(String invitationHash) throws IncorrectHashException;

  /**
   * Invite to group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   */
  void inviteToGroup(String userHash, StateToken token, String[] emails);

  /**
   * Invite to list.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   */
  void inviteToList(String userHash, StateToken token, String[] emails);

  /**
   * Invite to site.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   */
  void inviteToSite(String userHash, StateToken token, String[] emails);

}
