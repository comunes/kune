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

package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class RequestJoinGroupAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
	onRequestJoinGroup(services);
    }

    private void onRequestJoinGroup(final Services services) {
	Site.showProgressProcessing();
	final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
	String user = services.session.user;
	// FIXME Bug .... Check if user is logged!!!
	if (user == null) {
	    Site.important("You must be logged to request join a group");
	} else {
	    server.requestJoinGroup(user, services.session.getCurrentState().getGroup().getShortName(),
		    new AsyncCallback() {
			public void onFailure(final Throwable caught) {
			    Site.hideProgress();
			}

			public void onSuccess(final Object result) {
			    Site.hideProgress();
			    final String resultType = (String) result;
			    // i18n
			    if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
				Site.info("This is a open group, you are now member of this group");
			    }
			    if (resultType == SocialNetworkDTO.REQ_JOIN_DENIED) {
				Site.important("Sorry this is a closed group");
			    }
			    if (resultType == SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION) {
				Site.info("Requested. Waiting for admins decision");
			    }
			}
		    });
	}

    }
}
