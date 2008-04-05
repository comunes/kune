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
package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class OnlyCheckUserSessionAction implements Action<AsyncCallback<Object>> {
    private final Session session;

    public OnlyCheckUserSessionAction(final Session session) {
        this.session = session;
    }

    public void execute(final AsyncCallback<Object> value) {
        onOnlyCheckUserSession(value);
    }

    private void onOnlyCheckUserSession(final AsyncCallback<Object> callback) {
        UserServiceAsync server = UserService.App.getInstance();
        server.onlyCheckSession(session.getUserHash(), callback);
    }
}
