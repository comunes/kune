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

package org.ourproject.kune.platf.client.app;

import java.util.Iterator;
import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.actions.LoginAction;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class DefaultApplication implements Application {
    private final Workspace workspace;
    private final Map tools;
    private Dispatcher dispatcher;
    private StateController stateManager;

    public DefaultApplication(final Map tools) {
	this.tools = tools;
	workspace = WorkspaceFactory.getWorkspace();
	workspace.attachTools(tools.values().iterator());

	SiteBarListener listener = new SiteBarListener() {
	    public void onUserLoggedIn(final UserDTO user) {
		dispatcher.fire(LoginAction.EVENT, user);
	    }

	    public void onUserLoggedOut() {
	    }
	};

	final Desktop desktop = new Desktop(workspace, listener);
	RootPanel.get().add(desktop);
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		int windowWidth = Window.getClientWidth();
		workspace.adjustSize(windowWidth, Window.getClientHeight());
		SiteBarFactory.getSiteMessage().adjustWidth(windowWidth);
	    }
	});

    }

    public Dispatcher getDispatcher() {
	return dispatcher;
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    public ClientTool getTool(final String toolName) {
	return (ClientTool) tools.get(toolName);
    }

    public void init(final DefaultDispatcher dispatcher, final StateController stateManager) {
	this.dispatcher = dispatcher;
	this.stateManager = stateManager;
    }

    public StateController getStateManager() {
	return stateManager;
    }

    public void setGroupState(final String groupShortName) {
	Iterator iterator = tools.values().iterator();
	while (iterator.hasNext()) {
	    ClientTool tool = (ClientTool) iterator.next();
	    tool.setGroupState(groupShortName);
	}
    }

}
