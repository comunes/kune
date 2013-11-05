/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public interface GroupServiceAsync {

  void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license,
      AsyncCallback<Void> asyncCallback);

  void changeGroupWsTheme(String userHash, StateToken groupToken, String theme,
      AsyncCallback<Void> callback);

  void clearGroupBackImage(String userHash, StateToken token, AsyncCallback<GroupDTO> asyncCallback);

  void createNewGroup(String userHash, GroupDTO group, String publicDesc, String tags,
      String[] enabledTools, AsyncCallback<StateAbstractDTO> callback);

  void getGroup(String userHash, StateToken token, AsyncCallback<GroupDTO> asyncCallback);

  void setGroupNewMembersJoiningPolicy(String userHash, StateToken groupToken,
      AdmissionType admissionPolicy, AsyncCallback<Void> asyncCallback);

  void setSocialNetworkVisibility(String userHash, StateToken token, SocialNetworkVisibility visibility,
      AsyncCallback<Void> asyncCallback);

  void setToolEnabled(String userHash, StateToken groupToken, String toolName, boolean enabled,
      AsyncCallback<Void> asyncCallback);

  void updateGroup(String userHash, StateToken token, GroupDTO groupDTO,
      AsyncCallback<StateAbstractDTO> callback);

}
