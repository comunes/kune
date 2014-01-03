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
import cc.kune.core.client.errors.ToolIsDefaultException;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroupService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("GroupService")
public interface GroupService extends RemoteService {

  /**
   * Change def license.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param license
   *          the license
   */
  void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license);

  /**
   * Change group ws theme.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param theme
   *          the theme
   * @throws DefaultException
   *           the default exception
   */
  void changeGroupWsTheme(String userHash, StateToken groupToken, String theme) throws DefaultException;

  /**
   * Clear group back image.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @return the group dto
   */
  GroupDTO clearGroupBackImage(String userHash, StateToken token);

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
   * @return the state abstract dto
   * @throws DefaultException
   *           the default exception
   */
  StateAbstractDTO createNewGroup(String userHash, GroupDTO group, String publicDesc, String tags,
      String[] enabledTools) throws DefaultException;

  /**
   * Gets the group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @return the group
   */
  GroupDTO getGroup(String userHash, StateToken token);

  /**
   * Sets the group new members joining policy.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param admissionPolicy
   *          the admission policy
   */
  void setGroupNewMembersJoiningPolicy(String userHash, StateToken groupToken,
      AdmissionType admissionPolicy);

  /**
   * Sets the social network visibility.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param visibility
   *          the visibility
   */
  void setSocialNetworkVisibility(String userHash, StateToken groupToken,
      SocialNetworkVisibility visibility);

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
   * @throws ToolIsDefaultException
   *           the tool is default exception
   */
  void setToolEnabled(String userHash, StateToken groupToken, String toolName, boolean enabled)
      throws ToolIsDefaultException;

  /**
   * Update group.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   * @param groupDTO
   *          the group dto
   * @return the state abstract dto
   * @throws DefaultException
   *           the default exception
   */
  StateAbstractDTO updateGroup(String userHash, StateToken token, GroupDTO groupDTO)
      throws DefaultException;

}
