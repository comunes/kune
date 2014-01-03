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

import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroupServiceAsync.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface GroupServiceAsync {

  /**
   * Change def license.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param license
   *          the license
   * @param asyncCallback
   *          the async callback
   */
  void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license,
      AsyncCallback<Void> asyncCallback);

  /**
   * Change group ws theme.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param theme
   *          the theme
   * @param callback
   *          the callback
   */
  void changeGroupWsTheme(String userHash, StateToken groupToken, String theme,
      AsyncCallback<Void> callback);

  /**
   * Clear group back image.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param asyncCallback
   *          the async callback
   */
  void clearGroupBackImage(String userHash, StateToken token, AsyncCallback<GroupDTO> asyncCallback);

  /**
   * Creates the new group.
   * 
   * @param userHash
   *          the user hash
   * @param group
   *          the group
   * @param publicDesc
   *          the public desc
   * @param tags
   *          the tags
   * @param enabledTools
   *          the enabled tools
   * @param callback
   *          the callback
   */
  void createNewGroup(String userHash, GroupDTO group, String publicDesc, String tags,
      String[] enabledTools, AsyncCallback<StateAbstractDTO> callback);

  /**
   * Gets the group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param asyncCallback
   *          the async callback
   * @return the group
   */
  void getGroup(String userHash, StateToken token, AsyncCallback<GroupDTO> asyncCallback);

  /**
   * Sets the group new members joining policy.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param admissionPolicy
   *          the admission policy
   * @param asyncCallback
   *          the async callback
   */
  void setGroupNewMembersJoiningPolicy(String userHash, StateToken groupToken,
      AdmissionType admissionPolicy, AsyncCallback<Void> asyncCallback);

  /**
   * Sets the social network visibility.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param visibility
   *          the visibility
   * @param asyncCallback
   *          the async callback
   */
  void setSocialNetworkVisibility(String userHash, StateToken token, SocialNetworkVisibility visibility,
      AsyncCallback<Void> asyncCallback);

  /**
   * Sets the tool enabled.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param toolName
   *          the tool name
   * @param enabled
   *          the enabled
   * @param asyncCallback
   *          the async callback
   */
  void setToolEnabled(String userHash, StateToken groupToken, String toolName, boolean enabled,
      AsyncCallback<Void> asyncCallback);

  /**
   * Update group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param groupDTO
   *          the group dto
   * @param callback
   *          the callback
   */
  void updateGroup(String userHash, StateToken token, GroupDTO groupDTO,
      AsyncCallback<StateAbstractDTO> callback);

}
