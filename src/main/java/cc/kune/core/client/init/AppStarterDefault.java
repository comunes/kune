/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import org.waveprotocol.wave.client.common.util.UserAgent;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.UserNotifyEvent;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStopEvent;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.core.shared.CoreConstants;
import cc.kune.core.shared.dto.InitDataDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AppStarterDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AppStarterDefault implements AppStarter {

  /** The event bus. */
  private final EventBus eventBus;

  /** The prefetch utilities. */
  private final PrefetchUtilities prefetchUtilities;

  /** The res. */
  private final CommonResources res;

  /** The session. */
  private final Session session;

  /** The site service. */
  private final SiteServiceAsync siteService;

  /**
   * Instantiates a new app starter default.
   * 
   * @param session
   *          the session
   * @param siteService
   *          the site service
   * @param eventBus
   *          the event bus
   * @param prefetchUtilities
   *          the prefetch utilities
   * @param res
   *          the res
   */
  @Inject
  public AppStarterDefault(final Session session, final SiteServiceAsync siteService,
      final EventBus eventBus, final PrefetchUtilities prefetchUtilities, final CommonResources res) {
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

  /**
   * Check navigator compatibility.
   * 
   * @param navSupport
   *          the nav support
   */
  private void checkNavigatorCompatibility(final NavigatorSupport navSupport) {
    if (!SiteParameters.checkUA()) {
      // htmlunit server parsing
      navSupport.onSupported();
    } else {
      // http://www.useragentstring.com/pages/useragentstring.php
      final String userAgent = Navigator.getUserAgent().toLowerCase();
      Log.info("User agent: " + userAgent);
      if (UserAgent.isFirefox() || UserAgent.isWebkit() || UserAgent.isAndroid() || UserAgent.isChrome()
          || UserAgent.isSafari() || UserAgent.isMobileWebkit()) {
        navSupport.onSupported();
      } else {
        navSupport.onNotSupported();
      }
    }
  }

  /**
   * Gets the inits the data.
   * 
   * @return the inits the data
   */
  private void getInitData() {
    siteService.getInitData(session.getUserHash(), new AsyncCallback<InitDataDTO>() {
      private void continueStart(final InitDataDTO initData) {
        eventBus.fireEvent(new AppStartEvent(initData));
      }

      private void hideInitialPanels() {
        final RootPanel loadingElement = RootPanel.get("k-home-loading");
        if (loadingElement != null) {
          loadingElement.setVisible(false);
        }
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
        session.setCurrentUserInfo(initData.getUserInfo(), null);
        hideInitialPanels();
        checkNavigatorCompatibility(new NavigatorSupport() {
          @Override
          public void onNotSupported() {
            NotifyUser.askConfirmation(
                res.important32(),
                "Your browser is currently unsupported",
                "Please, use a free/libre modern navigator like "
                    + TextUtils.generateHtmlLink(CoreConstants.MOZILLA_FF_LINK, "Mozilla Firefox")
                    + " instead. Continue anyway?", new SimpleResponseCallback() {
                  @Override
                  public void onCancel() {
                    WindowUtils.changeHref(CoreConstants.MOZILLA_FF_LINK);
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.init.AppStarter#start()
   */
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
