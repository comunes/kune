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

package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public abstract class WorkspaceAction implements Action {
    protected Application app;
    protected Workspace workspace;
    protected Dispatcher dispatcher;
    protected StateController stateManager;
    protected String user;
    protected Session session;

    public void init(final Application app, final Session session, final StateController stateManager) {
	this.app = app;
	this.session = session;
	this.stateManager = stateManager;
	this.workspace = app.getWorkspace();
	this.dispatcher = app.getDispatcher();
	this.user = stateManager.getUser();
    }

    protected int toInt(final Object value) {
	return Integer.parseInt((String) value);
    }

    protected StateDTO getState() {
	return session.getCurrentState();
    }
}
