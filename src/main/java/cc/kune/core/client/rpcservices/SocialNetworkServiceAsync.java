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

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

  void acceptJoinGroup(String hash, StateToken groupToken, String groupToAcceptShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void addAdminMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void addAsBuddie(String hash, String userName, AsyncCallback<Void> callback);

  void addCollabMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void addViewerMember(String hash, StateToken groupToken, String groupToAddShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void deleteMember(String hash, StateToken groupToken, String groupToDeleteShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void denyJoinGroup(String hash, StateToken groupToken, String groupToDenyShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void getSocialNetwork(String hash, StateToken groupToken, AsyncCallback<SocialNetworkDataDTO> callback);

  void requestJoinGroup(String hash, StateToken groupToken,
      AsyncCallback<SocialNetworkRequestResult> callback);

  void setAdminAsCollab(String hash, StateToken groupToken, String groupToSetCollabShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void setCollabAsAdmin(String hash, StateToken groupToken, String groupToSetAdminShortName,
      AsyncCallback<SocialNetworkDataDTO> callback);

  void unJoinGroup(String hash, StateToken groupToken, AsyncCallback<Void> callback);
}
