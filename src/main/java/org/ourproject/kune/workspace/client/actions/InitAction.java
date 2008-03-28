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

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.utils.PrefetchUtilities;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class InitAction implements Action<Object> {
    private final Session session;
    private final Dispatcher dispatcher;
    private final Workspace workspace;

    public InitAction(final Session session, final Dispatcher dispatcher, final Workspace workspace) {
        this.session = session;
        this.dispatcher = dispatcher;
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        PrefetchUtilities.preFetchImpImages();
        getInitData();
        dispatcher.fire(WorkspaceEvents.RECALCULATE_WORKSPACE_SIZE, null);
        Timer prefetchTimer = new Timer() {
            public void run() {
                PrefetchUtilities.doTasksDeferred(workspace);
            }
        };
        prefetchTimer.schedule(20000);
    }

    private void getInitData() {
        SiteServiceAsync server = SiteService.App.getInstance();
        server.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
            public void onFailure(final Throwable error) {
                Site.error("Error fetching initial data");
                Log.debug(error.getMessage());
            }

            public void onSuccess(final InitDataDTO initData) {
                checkChatDomain(initData.getChatDomain());
                session.setLicenses(initData.getLicenses());
                session.setWsThemes(initData.getWsThemes());
                session.setDefaultWsTheme(initData.getDefaultWsTheme());
                session.setLanguages(initData.getLanguages());
                session.setCountries(initData.getCountries());
                session.setTimezones(initData.getTimezones());
                UserInfoDTO currentUser = initData.getUserInfo();
                dispatcher.fire(WorkspaceEvents.INIT_DATA_RECEIVED, initData);
                if (currentUser == null) {
                    dispatcher.fire(WorkspaceEvents.USER_LOGGED_OUT, null);
                } else {
                    dispatcher.fire(WorkspaceEvents.USER_LOGGED_IN, currentUser);
                }
                RootPanel.get("kuneinitialcurtain").setVisible(false);

                Site.unMask();
            }

            private void checkChatDomain(final String chatDomain) {
                String httpDomain = WindowUtils.getLocation().getHostName();
                if (!chatDomain.equals(httpDomain)) {
                    Log.error("Your http domain (" + httpDomain + ") is different from the chat domain (" + chatDomain
                            + "). This will produce problems with the chat functionality. "
                            + "Check kune.properties on the server.");
                }
            }
        });
    }
}
