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
package cc.kune.core.client.init;

import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.InitDataDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;

public class AppStarterDefault implements AppStarter {
    private final Session session;
    private final SiteServiceAsync siteService;
    private final EventBus eventBus;
    private final PrefetchUtilities prefetchUtilities;

    @Inject
    public AppStarterDefault(final Session session, final SiteServiceAsync siteService, final EventBus eventBus,
            PrefetchUtilities prefetchUtilities) {
        this.session = session;
        this.siteService = siteService;
        this.eventBus = eventBus;
        this.prefetchUtilities = prefetchUtilities;
        Window.addWindowClosingHandler(new ClosingHandler() {
            @Override
            public void onWindowClosing(final ClosingEvent event) {
                eventBus.fireEvent(new AppStopEvent());
            }
        });
    }

    @Override
    public void start() {
        prefetchUtilities.preFetchImpImages();
        getInitData();
        final Timer prefetchTimer = new Timer() {
            @Override
            public void run() {
                prefetchUtilities.doTasksDeferred();
            }
        };
        prefetchTimer.schedule(20000);
    }

    private void getInitData() {
        siteService.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
            @Override
            public void onFailure(final Throwable error) {
                eventBus.fireEvent(new ProgressHideEvent());
                eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
                        "Error fetching initial data from Kune server"));
                Log.debug(error.getMessage());
                hideInitialPanels();
            }

            private void hideInitialPanels() {
                RootPanel.get("kuneinitialcurtain").setVisible(false);
                RootPanel.get("kuneloading").setVisible(false);
            }

            @Override
            public void onSuccess(final InitDataDTO initData) {
                session.setInitData(initData);
                session.setCurrentUserInfo(initData.getUserInfo());
                eventBus.fireEvent(new AppStartEvent(initData));
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    @Override
                    public void execute() {
                        hideInitialPanels();
                        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, "Started"));
                        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
                                "Started closeable. Started closeable. Started closeable. Started closeable", true));
                    }
                });
            }
        });
    }
}
