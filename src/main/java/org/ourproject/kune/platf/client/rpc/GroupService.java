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
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.AdmissionTypeDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkVisibilityDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.DefaultException;

import com.google.gwt.user.client.rpc.RemoteService;

public interface GroupService extends RemoteService {

    void changeDefLicense(final String userHash, final StateToken groupToken, final LicenseDTO license);

    void changeGroupWsTheme(String userHash, StateToken groupToken, String theme) throws DefaultException;

    GroupDTO clearGroupBackImage(String userHash, StateToken token);

    StateToken createNewGroup(String userHash, GroupDTO group, String publicDesc, String tags, String[] enabledTools)
            throws DefaultException;

    GroupDTO getGroup(String userHash, StateToken token);

    GroupDTO setGroupBackImage(String userHash, StateToken token);

    void setGroupNewMembersJoiningPolicy(String userHash, StateToken groupToken, AdmissionTypeDTO admissionPolicy);

    void setSocialNetworkVisibility(String userHash, StateToken groupToken, SocialNetworkVisibilityDTO visibility);

    void setToolEnabled(String userHash, StateToken groupToken, String toolName, boolean enabled);

}
