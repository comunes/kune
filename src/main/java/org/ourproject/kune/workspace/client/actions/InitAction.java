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

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.utils.PrefetchUtilities;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class InitAction implements Action {
    public void execute(final Object value, final Object extra, final Services services) {
        PrefetchUtilities.preFetchImpImages();
        getInitData(services);
        int windowWidth = Window.getClientWidth();
        final Workspace workspace = services.app.getWorkspace();
        workspace.adjustSize(windowWidth, Window.getClientHeight());
        SiteBarFactory.getSiteMessage().adjustWidth(windowWidth);
        Timer prefetchTimer = new Timer() {
            public void run() {
                PrefetchUtilities.doTasksDeferred(workspace);
            }
        };
        prefetchTimer.schedule(20000);
    }

    private void getInitData(final Services services) {
        SiteServiceAsync server = SiteService.App.getInstance();
        server.getInitData(services.session.getUserHash(), new AsyncCallback() {
            public void onFailure(final Throwable error) {
                Site.error("Error fetching initial data");
                FireLog.debug(error.getMessage());
            }

            public void onSuccess(final Object response) {
                Dispatcher dispatcher = services.dispatcher;
                InitDataDTO initData = (InitDataDTO) response;
                services.session.setLicenses(initData.getLicenses());
                services.session.setWsThemes(initData.getWsThemes());
                services.session.setDefaultWsTheme(initData.getDefaultWsTheme());
                services.session.setLanguages(initData.getLanguages());
                services.session.setCountries(initData.getCountries());
                services.session.setTimezones(initData.getTimezones());
                UserInfoDTO currentUser = initData.getUserInfo();
                dispatcher.fire(WorkspaceEvents.INIT_DATA_RECEIVED, response, null);
                if (currentUser == null) {
                    dispatcher.fire(WorkspaceEvents.USER_LOGGED_OUT, null, null);
                } else {
                    dispatcher.fire(WorkspaceEvents.USER_LOGGED_IN, currentUser, null);
                }
                RootPanel.get("kuneinitialcurtain").setVisible(false);
                Site.unMask();
            }
        });
    }
}
