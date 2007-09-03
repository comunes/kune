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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.ActionEvent;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.state.StateControllerDefault;
import org.ourproject.kune.platf.client.tool.ClientTool;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationBuilder {
    private final String userHash;
    private final KunePlatform platform;

    public ApplicationBuilder(final String userHash, final KunePlatform platform) {
	this.userHash = userHash;
	this.platform = platform;
    }

    public Application build() {
	HashMap tools = indexTools(platform.getTools());
	DefaultApplication application = new DefaultApplication(tools);
	RootPanel.get("kuneinitialstatusbar").setVisible(false);

	final Session session = new Session(userHash);
	ContentProvider provider = new ContentProviderImpl(ContentService.App.getInstance());
	final StateController stateManager = new StateControllerDefault(provider, application, session);
	History.addHistoryListener(stateManager);

	final DefaultDispatcher dispatcher = DefaultDispatcher.getInstance();
	application.init(dispatcher, stateManager);
	subscribeActions(dispatcher, platform.getActions());

	Services services = new Services(userHash, application, session, stateManager, dispatcher);
	dispatcher.setServices(services);
	return application;
    }

    private void subscribeActions(final DefaultDispatcher dispatcher, final ArrayList actions) {
	ActionEvent actionEvent;

	for (Iterator it = actions.iterator(); it.hasNext();) {
	    actionEvent = (ActionEvent) it.next();
	    dispatcher.subscribe(actionEvent.event, actionEvent.action);
	}
    }

    private HashMap indexTools(final List toolList) {
	HashMap tools = new HashMap();
	int total = toolList.size();
	for (int index = 0; index < total; index++) {
	    ClientTool clientTool = (ClientTool) toolList.get(index);
	    tools.put(clientTool.getName(), clientTool);
	}
	return tools;
    }

}
