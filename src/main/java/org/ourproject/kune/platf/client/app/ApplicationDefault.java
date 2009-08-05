/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.utils.PrefetchUtilities;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationDefault implements Application {
    private final Session session;
    private final Event0 onAppStarting;
    private final Event<ClosingEvent> onAppClosing;
    private final SiteServiceAsync siteService;

    public ApplicationDefault(final Session session, final SiteServiceAsync siteService) {
        this.session = session;
        this.siteService = siteService;
        this.onAppStarting = new Event0("onAppStarting");
        this.onAppClosing = new Event<ClosingEvent>("onAppClossing");
        Window.addWindowClosingHandler(new ClosingHandler() {
            public void onWindowClosing(final ClosingEvent event) {
                stop(event);
            }
        });
    }

    public void onClosing(final Listener<ClosingEvent> listener) {
        onAppClosing.add(listener);
    }

    public void onStarting(final Listener0 listener) {
        onAppStarting.add(listener);
    }

    public void start() {
        onAppStarting.fire();
        PrefetchUtilities.preFetchImpImages();
        getInitData();
        final Timer prefetchTimer = new Timer() {
            @Override
            public void run() {
                PrefetchUtilities.doTasksDeferred();
            }
        };
        prefetchTimer.schedule(20000);
    }

    private void getInitData() {
        siteService.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
            public void onFailure(final Throwable error) {
                RootPanel.get("kuneinitialcurtain").setVisible(false);
                RootPanel.get("kuneloading").setVisible(false);
                NotifyUser.error("Error fetching initial data");
                Log.debug(error.getMessage());
            }

            public void onSuccess(final InitDataDTO initData) {
                session.setInitData(initData);
                session.setCurrentUserInfo(initData.getUserInfo());
                DeferredCommand.addCommand(new Command() {
                    public void execute() {
                        RootPanel.get("kuneinitialcurtain").setVisible(false);
                        RootPanel.get("kuneloading").setVisible(false);
                    }
                });
            }
        });
    }

    private void stop(final ClosingEvent event) {
        onAppClosing.fire(event);
    }
}
