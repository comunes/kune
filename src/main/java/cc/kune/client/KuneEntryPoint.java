/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.client;

import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.MetaUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.wave.client.WebClient.ErrorHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The Class KuneEntryPoint.
 */
public class KuneEntryPoint implements EntryPoint {

  private static final String HOME_IDS_DEF_SUFFIX = "-def";
  private static final String HOME_IDS_PREFIX = "k-home-";
  public final KuneGinjector ginjector = GWT.create(KuneGinjector.class);

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @Override
  public void onModuleLoad() {
    setHomeLocale();
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable e) {
        Log.error("Error in 'onModuleLoad()' method", e);
        ErrorHandler.getStackTraceAsync(e, new Accessor<SafeHtml>() {
          @Override
          public void use(final SafeHtml stack) {
            final String message = stack.asString().replace("<br>", "\n");
            NotifyUser.logError(message);
            NotifyUser.showProgress("Error");
            new Timer() {
              @Override
              public void run() {
                NotifyUser.hideProgress();
              }
            }.schedule(5000);
          }
        });
      }
    });

    GWT.runAsync(new RunAsyncCallback() {
      @Override
      public void onFailure(final Throwable reason) {
        GWT.log("Error starting kune");
      }

      @Override
      public void onSuccess() {
        // FIXME: emite is loading (via EmiteBrowserEntryPoint) here! (maybe we
        // don't need chat)
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            onModuleLoadCont();
          }

          /**
           * On module load continue
           */
          private void onModuleLoadCont() {
            DelayedBindRegistry.bind(ginjector);
            ginjector.getSpinerPresenter();

            ginjector.getSessionExpirationManager();
            ginjector.getEventLogger();
            ginjector.getErrorsDialog();
            ginjector.getCorePresenter().get().forceReveal();
            ginjector.getOnAppStartFactory();
            ginjector.getStateManager();
            ginjector.getGwtGuiProvider();
            ginjector.getGroupMembersPresenter();

            /* Tools (order in GUI) */
            ginjector.getDocsParts();
            ginjector.getBlogsParts();
            ginjector.getWikiParts();
            ginjector.getEventsParts();
            ginjector.getTasksParts();
            ginjector.getListsParts();
            ginjector.getChatParts();
            ginjector.getBartersParts();
            ginjector.getTrashParts();

            ginjector.getSiteLogo();
            ginjector.getChatClient();
            ginjector.getCoreParts();
            ginjector.getGSpaceParts();
            ginjector.getPSpaceParts();
            ginjector.getHSpaceParts();

            ginjector.getXmlActionsParser();
            ginjector.getContentViewerSelector().init();

            ginjector.getGlobalShortcutRegister().enable();
          }
        });
      }
    });
  }

  /**
   * Home set locale. In ws.html there is some no visible elements with the
   * different locales and we only show the current locale
   */
  private void setHomeLocale() {
    final String currentLocale = WindowUtils.getParameter(SiteParameters.LOCALE);

    final String meta = MetaUtils.get("kune.home.ids");
    if (meta != null) {
      final String[] ids = meta.split(",[ ]*");

      for (final String id : ids) {
        final RootPanel someElement = RootPanel.get(HOME_IDS_PREFIX + id + "-" + currentLocale);
        final RootPanel defElement = RootPanel.get(HOME_IDS_PREFIX + id + HOME_IDS_DEF_SUFFIX);
        if (someElement != null) {
          someElement.setVisible(true);
        } else if (defElement != null) {
          defElement.setVisible(true);
        }
      }
    }
  }

}
