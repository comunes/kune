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

import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

    String requestJoinGroup(String hash, String groupShortName) throws SerializableException;

    SocialNetworkResultDTO AcceptJoinGroup(String hash, String groupShortName, String groupToAcceptShortName)
            throws SerializableException;

    SocialNetworkResultDTO deleteMember(String hash, String groupShortName, String groupToDeleteShortName)
            throws SerializableException;

    SocialNetworkResultDTO denyJoinGroup(String hash, String groupShortName, String groupToDenyShortName)
            throws SerializableException;

    SocialNetworkResultDTO unJoinGroup(String hash, String groupShortName) throws SerializableException;

    SocialNetworkResultDTO setCollabAsAdmin(String hash, String groupShortName, String groupToSetAdminShortName)
            throws SerializableException;

    SocialNetworkResultDTO setAdminAsCollab(String hash, String groupShortName, String groupToSetCollabShortName)
            throws SerializableException;

    SocialNetworkResultDTO addAdminMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkResultDTO addCollabMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkResultDTO addViewerMember(String hash, String groupShortName, String groupToAddShortName)
            throws SerializableException;

    SocialNetworkDTO getGroupMembers(String hash, String groupShortName) throws SerializableException;

    ParticipationDataDTO getParticipation(String hash, String groupShortName) throws SerializableException;

    public static class App {
        private static SocialNetworkServiceAsync instance;

        public static SocialNetworkServiceAsync getInstance() {
            if (instance == null) {
                instance = (SocialNetworkServiceAsync) GWT.create(SocialNetworkService.class);
                ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "SocialNetworkService");
            }
            return instance;
        }

    }
}
