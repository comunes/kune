/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

@RemoteServiceRelativePath("GroupService")
public interface GroupService extends RemoteService {

  void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license);

  void changeGroupWsTheme(String userHash, StateToken groupToken, String theme) throws DefaultException;

  GroupDTO clearGroupBackImage(String userHash, StateToken token);

  StateAbstractDTO createNewGroup(String userHash, GroupDTO group, String publicDesc, String tags,
      String[] enabledTools) throws DefaultException;

  GroupDTO getGroup(String userHash, StateToken token);

  void setGroupNewMembersJoiningPolicy(String userHash, StateToken groupToken,
      AdmissionType admissionPolicy);

  void setSocialNetworkVisibility(String userHash, StateToken groupToken,
      SocialNetworkVisibility visibility);

  void setToolEnabled(String userHash, StateToken groupToken, String toolName, boolean enabled)
      throws ToolIsDefaultException;

  StateAbstractDTO updateGroup(String userHash, StateToken token, GroupDTO groupDTO)
      throws DefaultException;

}
