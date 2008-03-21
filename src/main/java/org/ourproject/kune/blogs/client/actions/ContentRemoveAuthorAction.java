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

package org.ourproject.kune.blogs.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class ContentRemoveAuthorAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onContentRemoveAuthor(services, (String) value, (String) extra);
    }

    private void onContentRemoveAuthor(final Services services, final String documentId, final String authorShortName) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.removeAuthor(services.session.getUserHash(), services.session.getCurrentState().getGroup()
                .getShortName(), documentId, authorShortName, new AsyncCallbackSimple<Object>() {
            public void onSuccess(final Object result) {
                Site.hideProgress();
                services.stateManager.reload();
            }
        });
    }
}
