/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.InitDataDTO;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

public class AppStarterDefault implements AppStarter {
  private final EventBus eventBus;
  private final PrefetchUtilities prefetchUtilities;
  private final CoreResources res;
  private final Session session;
  private final SiteServiceAsync siteService;

  @Inject
  public AppStarterDefault(final Session session, final SiteServiceAsync siteService,
      final EventBus eventBus, final PrefetchUtilities prefetchUtilities, final CoreResources res) {
    this.session = session;
    this.siteService = siteService;
    this.eventBus = eventBus;
    this.prefetchUtilities = prefetchUtilities;
    this.res = res;
    Window.addWindowClosingHandler(new ClosingHandler() {
      @Override
      public void onWindowClosing(final ClosingEvent event) {
        eventBus.fireEvent(new AppStopEvent());
      }
    });
  }

  private void checkNavigatorCompatibility(final NavigatorSupport navSupport) {
    // http://www.useragentstring.com/pages/useragentstring.php
    final String userAgent = Navigator.getUserAgent().toLowerCase();
    Log.info("User agent: " + userAgent);
    if (userAgent.contains("chrome") || userAgent.contains("gecko")) {
      navSupport.onSupported();
    } else {
      navSupport.onNotSupported();
    }
  }

  private void getInitData() {
    siteService.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
      private void continueStart(final InitDataDTO initData) {
        eventBus.fireEvent(new AppStartEvent(initData));
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            // hideInitialPanels();
          }
        });
      }

      private void hideInitialPanels() {
        RootPanel.get("kuneloading").setVisible(false);
      }

      @Override
      public void onFailure(final Throwable error) {
        eventBus.fireEvent(new ProgressHideEvent());
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
            "Error fetching initial data from Kune server"));
        Log.debug(error.getMessage());
        hideInitialPanels();
      }

      @Override
      public void onSuccess(final InitDataDTO initData) {
        session.setInitData(initData);
        session.setCurrentUserInfo(initData.getUserInfo());
        hideInitialPanels();
        checkNavigatorCompatibility(new NavigatorSupport() {
          @Override
          public void onNotSupported() {
            NotifyUser.askConfirmation(
                res.important32(),
                "Your browser is currently unsupported",
                "Please, use a free/libre modern navigator like <a class='k-link' href='https://www.mozilla.com/en-US/firefox/fx/'>Mozilla Firefox</a> instead. Continue anyway?",
                new SimpleResponseCallback() {
                  @Override
                  public void onCancel() {
                  }

                  @Override
                  public void onSuccess() {
                    continueStart(initData);
                  }
                });
          }

          @Override
          public void onSupported() {
            continueStart(initData);
          }
        });
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
}
