/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialNetworkServiceAsync {

    void requestJoinGroup(String hash, String groupShortName, AsyncCallback<Object> callback);

    void AcceptJoinGroup(String hash, String groupShortName, String groupToAcceptShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void deleteMember(String hash, String groupShortName, String groupToDeleteShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void denyJoinGroup(String hash, String groupShortName, String groupToDenyShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void unJoinGroup(String hash, String groupShortName, AsyncCallback<SocialNetworkResultDTO> callback);

    void setCollabAsAdmin(String hash, String groupShortName, String groupToSetAdminShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void setAdminAsCollab(String hash, String groupShortName, String groupToSetCollabShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addAdminMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addCollabMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void addViewerMember(String hash, String groupShortName, String groupToAddShortName,
            AsyncCallback<SocialNetworkResultDTO> callback);

    void getGroupMembers(String hash, String groupShortName, AsyncCallback<SocialNetworkResultDTO> callback);

    void getParticipation(String hash, String groupShortName, AsyncCallback<ParticipationDataDTO> callback);
}
