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
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;

public class ApplicationBuilder {
    private final KunePlatform platform;

    public ApplicationBuilder(final KunePlatform platform) {
        this.platform = platform;
    }

    public void build(final String userHash) {
        HashMap tools = indexTools(platform.getTools());
        final Session session = new Session(userHash);
        new KuneErrorHandler(session);
        final DefaultApplication application = new DefaultApplication(tools, session);
        Site.showProgressLoading();
        ContentProvider provider = new ContentProviderImpl(ContentService.App.getInstance());
        final StateManager stateManager = new StateManagerDefault(provider, application, session);
        History.addHistoryListener(stateManager);

        final DefaultDispatcher dispatcher = DefaultDispatcher.getInstance();
        application.init(dispatcher, stateManager);
        subscribeActions(dispatcher, platform.getActions());

        Services services = new Services(application, stateManager, dispatcher, session);
        dispatcher.setServices(services);
        Window.addWindowCloseListener(new WindowCloseListener() {
            public void onWindowClosed() {
                application.stop();
            }

            public String onWindowClosing() {
                return null;
            }
        });
        application.start();
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
