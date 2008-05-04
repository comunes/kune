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
import org.ourproject.kune.platf.client.errors.DefaultException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SocialNetworkService extends RemoteService {

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

    SocialNetworkResultDTO AcceptJoinGroup(String hash, String groupShortName, String groupToAcceptShortName)
	    throws DefaultException;

    SocialNetworkResultDTO addAdminMember(String hash, String groupShortName, String groupToAddShortName)
	    throws DefaultException;

    SocialNetworkResultDTO addCollabMember(String hash, String groupShortName, String groupToAddShortName)
	    throws DefaultException;

    SocialNetworkResultDTO addViewerMember(String hash, String groupShortName, String groupToAddShortName)
	    throws DefaultException;

    SocialNetworkResultDTO deleteMember(String hash, String groupShortName, String groupToDeleteShortName)
	    throws DefaultException;

    SocialNetworkResultDTO denyJoinGroup(String hash, String groupShortName, String groupToDenyShortName)
	    throws DefaultException;

    SocialNetworkDTO getGroupMembers(String hash, String groupShortName) throws DefaultException;

    ParticipationDataDTO getParticipation(String hash, String groupShortName) throws DefaultException;

    String requestJoinGroup(String hash, String groupShortName) throws DefaultException;

    SocialNetworkResultDTO setAdminAsCollab(String hash, String groupShortName, String groupToSetCollabShortName)
	    throws DefaultException;

    SocialNetworkResultDTO setCollabAsAdmin(String hash, String groupShortName, String groupToSetAdminShortName)
	    throws DefaultException;

    SocialNetworkResultDTO unJoinGroup(String hash, String groupShortName) throws DefaultException;
}
