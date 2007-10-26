/*
 *
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

package org.ourproject.kune.sitebar.client.rpc;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO: cambiar nombre a UserService
public interface SiteBarService extends RemoteService {

    // FIXME: Vicente, userHash
    UserInfoDTO login(String nickOrEmail, String passwd) throws SerializableException;

    // FIXME: Vicente, userHash
    UserInfoDTO createUser(String shortName, String longName, String email, String passwd, LicenseDTO license)
            throws SerializableException;

    // FIXME: Vicente, userHash
    void logout() throws SerializableException;

    UserInfoDTO reloadUserInfo(String userHash) throws SerializableException;

    public class App {
        private static SiteBarServiceAsync ourInstance = null;

        public static synchronized SiteBarServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (SiteBarServiceAsync) GWT.create(SiteBarService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "SiteBarService");
            }
            return ourInstance;
        }

        public static void setMock(final SiteBarServiceAsync mock) {
            ourInstance = mock;
        }
    }
}
