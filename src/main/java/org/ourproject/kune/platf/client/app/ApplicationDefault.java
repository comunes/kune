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

package org.ourproject.kune.platf.client.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.utils.PrefetchUtilities;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Slot;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ApplicationDefault implements Application {
    private final Workspace workspace;
    private Map<String, ClientTool> tools;
    private final Session session;
    private final Signal<Object> onApplicationStart;
    private final Signal<Object> onApplicationStop;
    private final WorkspaceSkeleton ws;
    private StateManager stateManager;

    public ApplicationDefault(final Session session, final ExtensibleWidgetsManager extensionPointManager,
	    final I18nTranslationService i18n, final KuneErrorHandler errorHandler, final WorkspaceSkeleton ws) {
	this.session = session;
	this.ws = ws;
	workspace = WorkspaceFactory.createWorkspace(session, extensionPointManager, i18n, errorHandler);
	tools = new HashMap<String, ClientTool>();
	this.onApplicationStart = new Signal<Object>("onApplicationStart");
	this.onApplicationStop = new Signal<Object>("onApplicationStop");
    }

    public ClientTool getTool(final String toolName) {
	return tools.get(toolName);
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    public void init(final DefaultDispatcher dispatcher, final StateManager stateManager,
	    final HashMap<String, ClientTool> tools) {
	this.stateManager = stateManager;
	this.tools = tools;
	workspace.attachTools(tools.values().iterator());
    }

    public void onApplicationStart(final Slot<Object> slot) {
	onApplicationStart.add(slot);
    }

    public void onApplicationStop(final Slot<Object> slot) {
	onApplicationStop.add(slot);
    }

    public void setGroupState(final String groupShortName) {
	final Iterator<ClientTool> iterator = tools.values().iterator();
	while (iterator.hasNext()) {
	    final ClientTool tool = iterator.next();
	    tool.setGroupState(groupShortName);
	}
    }

    public void start() {
	onApplicationStart.fire(null);
	PrefetchUtilities.preFetchImpImages();
	getInitData();
	final Timer prefetchTimer = new Timer() {
	    public void run() {
		PrefetchUtilities.doTasksDeferred(workspace);
	    }
	};
	prefetchTimer.schedule(20000);
    }

    public void stop() {
	onApplicationStop.fire(null);
    }

    private void getInitData() {
	final SiteServiceAsync server = SiteService.App.getInstance();
	server.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
	    public void onFailure(final Throwable error) {
		Site.error("Error fetching initial data");
		Log.debug(error.getMessage());
	    }

	    public void onSuccess(final InitDataDTO initData) {
		checkChatDomain(initData.getChatDomain());
		session.setInitData(initData);
		session.setCurrentUserInfo(initData.getUserInfo());
		stateManager.reload();
	    }

	    private void checkChatDomain(final String chatDomain) {
		final String httpDomain = WindowUtils.getLocation().getHostName();
		if (!chatDomain.equals(httpDomain)) {
		    Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
			    + "). This will produce problems with the chat functionality. "
			    + "Check kune.properties on the server.");
		}
	    }
	});
    }
}
