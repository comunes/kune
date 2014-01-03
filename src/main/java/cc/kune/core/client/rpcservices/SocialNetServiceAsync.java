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
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface SocialNetServiceAsync.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SocialNetServiceAsync {

  /**
   * Accept join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAcceptShortName
   *          the group to accept short name
   * @param callback
   *          the callback
   */
  void acceptJoinGroup(String hash, StateToken groupToken, String groupToAcceptShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Adds the admin member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @param callback
   *          the callback
   */
  void addAdminMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Adds the as buddie.
   * 
   * @param hash
   *          the hash
   * @param userName
   *          the user name
   * @param callback
   *          the callback
   */
  void addAsBuddie(String hash, String userName, AsyncCallback<Void> callback);

  /**
   * Adds the collab member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @param callback
   *          the callback
   */
  void addCollabMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Adds the viewer member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @param callback
   *          the callback
   */
  void addViewerMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Delete member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToDeleteShortName
   *          the group to delete short name
   * @param callback
   *          the callback
   */
  void deleteMember(String hash, StateToken groupToken, String groupToDeleteShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Deny join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToDenyShortName
   *          the group to deny short name
   * @param callback
   *          the callback
   */
  void denyJoinGroup(String hash, StateToken groupToken, String groupToDenyShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Gets the social network.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param callback
   *          the callback
   * @return the social network
   */
  void getSocialNetwork(String hash, StateToken groupToken, AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Request join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param callback
   *          the callback
   */
  void requestJoinGroup(String hash, StateToken groupToken,
      AsyncCallback<SocialNetworkRequestResult> callback);

  /**
   * Sets the admin as collab.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToSetCollabShortName
   *          the group to set collab short name
   * @param callback
   *          the callback
   */
  void setAdminAsCollab(String hash, StateToken groupToken, String groupToSetCollabShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Sets the collab as admin.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToSetAdminShortName
   *          the group to set admin short name
   * @param callback
   *          the callback
   */
  void setCollabAsAdmin(String hash, StateToken groupToken, String groupToSetAdminShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  /**
   * Un join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param callback
   *          the callback
   */
  void unJoinGroup(String hash, StateToken groupToken, AsyncCallback<Void> callback);
}
