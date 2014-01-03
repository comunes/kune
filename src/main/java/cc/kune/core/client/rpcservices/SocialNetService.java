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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface SocialNetService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("SocialNetService")
public interface SocialNetService extends RemoteService {

  /**
   * Accept join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAcceptShortName
   *          the group to accept short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO acceptJoinGroup(String hash, StateToken groupToken, String groupToAcceptShortName)
      throws DefaultException;

  /**
   * Adds the admin member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO addAdminMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  /**
   * Adds the as buddie.
   * 
   * @param hash
   *          the hash
   * @param userName
   *          the user name
   * @throws DefaultException
   *           the default exception
   */
  void addAsBuddie(String hash, String userName) throws DefaultException;

  /**
   * Adds the collab member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO addCollabMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  /**
   * Adds the viewer member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToAddShortName
   *          the group to add short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO addViewerMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  /**
   * Delete member.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToDeleteShortName
   *          the group to delete short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO deleteMember(String hash, StateToken groupToken, String groupToDeleteShortName)
      throws DefaultException;

  /**
   * Deny join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToDenyShortName
   *          the group to deny short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO denyJoinGroup(String hash, StateToken groupToken, String groupToDenyShortName)
      throws DefaultException;

  /**
   * Gets the social network.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @return the social network
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO getSocialNetwork(String hash, StateToken groupToken) throws DefaultException;

  /**
   * Request join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @return the social network request result
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkRequestResult requestJoinGroup(String hash, StateToken groupToken)
      throws DefaultException;

  /**
   * Sets the admin as collab.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToSetCollabShortName
   *          the group to set collab short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO setAdminAsCollab(String hash, StateToken groupToken,
      String groupToSetCollabShortName) throws DefaultException;

  /**
   * Sets the collab as admin.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @param groupToSetAdminShortName
   *          the group to set admin short name
   * @return the social network data dto
   * @throws DefaultException
   *           the default exception
   */
  SocialNetworkDataDTO setCollabAsAdmin(String hash, StateToken groupToken,
      String groupToSetAdminShortName) throws DefaultException;

  /**
   * Un join group.
   * 
   * @param hash
   *          the hash
   * @param groupToken
   *          the group token
   * @throws DefaultException
   *           the default exception
   */
  void unJoinGroup(String hash, StateToken groupToken) throws DefaultException;
}
