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
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

    String requestJoinGroup(String hash, String groupShortName) throws SerializableException;

    void AcceptJoinGroup(String hash, String groupToAcceptShortName, String groupShortName)
            throws SerializableException;

    void deleteMember(String hash, String groupToDeleteShortName, String groupShortName) throws SerializableException;

    void denyJoinGroup(String hash, String groupToDenyShortName, String groupShortName) throws SerializableException;

    void unJoinGroup(String hash, String groupToUnJoinShortName, String groupShortName) throws SerializableException;

    void setCollabAsAdmin(String hash, String groupToSetAdminShortName, String groupShortName)
            throws SerializableException;

    void setAdminAsCollab(String hash, String groupToSetCollabShortName, String groupShortName)
            throws SerializableException;

    void addAdminMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

    void addCollabMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

    void addViewerMember(String hash, String groupToAddShortName, String groupShortName) throws SerializableException;

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
