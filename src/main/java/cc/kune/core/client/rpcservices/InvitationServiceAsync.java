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

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.InvitationDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface InvitationServiceAsync.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface InvitationServiceAsync {

  /**
   * Confirmation invitation to group.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @param callback
   *          the callback
   */
  void confirmationInvitationToGroup(String userHash, String invitationHash, AsyncCallback<Void> callback);

  /**
   * Confirmation invitation to site.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @param callback
   *          the callback
   */
  void confirmationInvitationToSite(String userHash, String invitationHash, AsyncCallback<Void> callback);

  /**
   * Confirm invitation to list.
   * 
   * @param userHash
   *          the user hash
   * @param invitationHash
   *          the invitation hash
   * @param callback
   *          the callback
   */
  void confirmInvitationToList(String userHash, String invitationHash,
      AsyncCallback<StateContainerDTO> callback);

  /**
   * Gets the invitation.
   * 
   * @param invitationHash
   *          the invitation hash
   * @param callback
   *          the callback
   * @return the invitation
   */
  void getInvitation(String invitationHash, AsyncCallback<InvitationDTO> callback);

  /**
   * Invite to group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   * @param callback
   *          the callback
   */
  void inviteToGroup(String userHash, StateToken token, String[] emails, AsyncCallback<Void> callback);

  /**
   * Invite to list.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   * @param callback
   *          the callback
   */
  void inviteToList(String userHash, StateToken token, String[] emails, AsyncCallback<Void> callback);

  /**
   * Invite to site.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param emails
   *          the emails
   * @param callback
   *          the callback
   */
  void inviteToSite(String userHash, StateToken token, String[] emails, AsyncCallback<Void> callback);

}
