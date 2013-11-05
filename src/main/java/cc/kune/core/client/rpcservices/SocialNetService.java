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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SocialNetService")
public interface SocialNetService extends RemoteService {

  SocialNetworkDataDTO acceptJoinGroup(String hash, StateToken groupToken, String groupToAcceptShortName)
      throws DefaultException;

  SocialNetworkDataDTO addAdminMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  void addAsBuddie(String hash, String userName) throws DefaultException;

  SocialNetworkDataDTO addCollabMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  SocialNetworkDataDTO addViewerMember(String hash, StateToken groupToken, String groupToAddShortName)
      throws DefaultException;

  SocialNetworkDataDTO deleteMember(String hash, StateToken groupToken, String groupToDeleteShortName)
      throws DefaultException;

  SocialNetworkDataDTO denyJoinGroup(String hash, StateToken groupToken, String groupToDenyShortName)
      throws DefaultException;

  SocialNetworkDataDTO getSocialNetwork(String hash, StateToken groupToken) throws DefaultException;

  SocialNetworkRequestResult requestJoinGroup(String hash, StateToken groupToken)
      throws DefaultException;

  SocialNetworkDataDTO setAdminAsCollab(String hash, StateToken groupToken,
      String groupToSetCollabShortName) throws DefaultException;

  SocialNetworkDataDTO setCollabAsAdmin(String hash, StateToken groupToken,
      String groupToSetAdminShortName) throws DefaultException;

  void unJoinGroup(String hash, StateToken groupToken) throws DefaultException;
}
