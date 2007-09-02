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

package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

// TODO: cambiar nombre a UserService
public interface KuneService extends RemoteService {

    // FIXME: falta el userHash!
    StateToken createNewGroup(GroupDTO group) throws SerializableException;

    InitDataDTO getInitData(String userHash);

    public class App {
	private static KuneServiceAsync ourInstance = null;

	public static synchronized KuneServiceAsync getInstance() {
	    if (ourInstance == null) {
		ourInstance = (KuneServiceAsync) GWT.create(KuneService.class);
		((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneService");
	    }
	    return ourInstance;
	}

	public static void setMock(final KuneServiceAsync mocked) {
	    ourInstance = mocked;
	}
    }

}
