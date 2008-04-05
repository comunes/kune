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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.blogs.client.BlogClientTool;
import org.ourproject.kune.blogs.client.BlogsClientModule;
import org.ourproject.kune.chat.client.ChatClientModule;
import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.docs.client.DocsClientModule;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.PlatformClientModule;
import org.ourproject.kune.platf.client.dispatch.ActionEvent;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.extend.HelloWorldPlugin;
import org.ourproject.kune.platf.client.extend.PluginManager;
import org.ourproject.kune.platf.client.extend.UIExtensionPointManager;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionImpl;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.WorkspaceClientModule;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;

public class ApplicationBuilder {

    public ApplicationBuilder() {
    }

    public void build(final String userHash, final I18nLanguageDTO initialLang) {
        KunePlatform platform = new KunePlatform();
        ChatClientTool chatClientTool = new ChatClientTool();
        platform.addTool(new DocumentClientTool());
        platform.addTool(chatClientTool);
        platform.addTool(new BlogClientTool());

        HashMap<String, ClientTool> tools = indexTools(platform.getTools());

        final Session session = new SessionImpl(userHash, initialLang);
        new KuneErrorHandler(session);

        UIExtensionPointManager extensionPointManager = new UIExtensionPointManager();
        final DefaultApplication application = new DefaultApplication(tools, session, extensionPointManager);
        Workspace workspace = application.getWorkspace();
        Site.showProgressLoading();
        Site.mask();

        ContentProvider provider = new ContentProviderImpl(ContentService.App.getInstance());

        HistoryWrapper historyWrapper = new HistoryWrapperImpl();
        final StateManager stateManager = new StateManagerDefault(provider, application, session, historyWrapper);
        History.addHistoryListener(stateManager);

        platform.install(new PlatformClientModule(session, stateManager));
        platform.install(new ChatClientModule(session, stateManager, chatClientTool));
        platform.install(new WorkspaceClientModule(session, stateManager, workspace));
        platform.install(new DocsClientModule(session, stateManager, workspace));
        platform.install(new BlogsClientModule());

        final DefaultDispatcher dispatcher = DefaultDispatcher.getInstance();

        // Services services = new Services(application, stateManager,
        // dispatcher, session, extensionPointManager,
        // Kune.I18N);

        application.init(dispatcher, stateManager);
        subscribeActions(dispatcher, platform.getActions());

        PluginManager pluginManager = new PluginManager(dispatcher, extensionPointManager, Kune.I18N);
        pluginManager.install(new HelloWorldPlugin());

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

    private void subscribeActions(final DefaultDispatcher dispatcher, final ArrayList<ActionEvent<?>> actions) {
        ActionEvent<?> actionEvent;

        for (Iterator<ActionEvent<?>> it = actions.iterator(); it.hasNext();) {
            actionEvent = it.next();
            dispatcher.subscribe(actionEvent.event, actionEvent.action);
        }
    }

    private HashMap<String, ClientTool> indexTools(final List<ClientTool> toolList) {
        HashMap<String, ClientTool> tools = new HashMap<String, ClientTool>();
        int total = toolList.size();
        for (int index = 0; index < total; index++) {
            ClientTool clientTool = toolList.get(index);
            tools.put(clientTool.getName(), clientTool);
        }
        return tools;
    }

}
