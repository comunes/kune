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
package org.ourproject.kune.docs.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class ContentSetPublishedOnAction implements Action<Date> {

    private final Session session;

    public ContentSetPublishedOnAction(final Session session) {
	this.session = session;
    }

    public void execute(final Date value) {
	onContentsetPublishedOn(value);
    }

    private void onContentsetPublishedOn(final Date publishedOn) {
	Site.showProgressProcessing();
	final ContentServiceAsync server = ContentService.App.getInstance();
	final StateDTO currentState = session.getCurrentState();
	server.setPublishedOn(session.getUserHash(), currentState.getGroup().getShortName(), currentState
		.getDocumentId(), publishedOn, new AsyncCallbackSimple<Object>() {
	    public void onSuccess(final Object result) {
		Site.hideProgress();
		// FIXME
		// workspace.getContentTitleComponent().setContentDate(publishedOn);
	    }
	});
    }
}
