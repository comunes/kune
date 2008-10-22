/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

    void acceptJoinGroup(String hash, StateToken groupToken, String groupToAcceptShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addAdminMember(String hash, StateToken groupToken, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addCollabMember(String hash, StateToken groupToken, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addViewerMember(String hash, StateToken groupToken, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void deleteMember(String hash, StateToken groupToken, String groupToDeleteShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void denyJoinGroup(String hash, StateToken groupToken, String groupToDenyShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void getSocialNetwork(String hash, StateToken groupToken, AsyncCallback<SocialNetworkResultDTO> callback);

    void requestJoinGroup(String hash, StateToken groupToken, AsyncCallback<?> callback);

    void setAdminAsCollab(String hash, StateToken groupToken, String groupToSetCollabShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void setCollabAsAdmin(String hash, StateToken groupToken, String groupToSetAdminShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void unJoinGroup(String hash, StateToken groupToken, AsyncCallback<SocialNetworkResultDTO> callback);
}
