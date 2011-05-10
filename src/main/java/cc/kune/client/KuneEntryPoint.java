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
package cc.kune.client;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The Class KuneEntryPoint.
 */
public class KuneEntryPoint implements EntryPoint {

  public final KuneGinjector ginjector = GWT.create(KuneGinjector.class);

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @Override
  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable e) {
        Log.error("Error in 'onModuleLoad()' method", e);
        e.printStackTrace();
      }
    });
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        onModuleLoadCont();
      }
    });
  }

  /**
   * On module load cont.
   */
  public void onModuleLoadCont() {
    DelayedBindRegistry.bind(ginjector);
    ginjector.getSpinerPresenter();
    ginjector.getGlobalShortcutRegister().enable();
    AsyncCallbackSimple.init(ginjector.getErrorHandler());
    ginjector.getEventLogger();
    NotifyUser.init(ginjector.getEventBus(), ginjector.getI18n());
    ginjector.getCorePresenter().get().forceReveal();
    ginjector.getOnAppStartFactory();
    ginjector.getStateManager();
    ginjector.getGwtGuiProvider();
    ginjector.getUserNotifierPresenter();
    ginjector.getSpinerPresenter();
    ginjector.getGroupMembersPresenter();
    ginjector.getDocsParts();
    ginjector.getBlogsParts();
    ginjector.getMeetingsParts();
    ginjector.getBartersParts();
    ginjector.getWikiParts();
    ginjector.getSiteLogoPresenter();
    ginjector.getSpacesTabPresenter();
    ginjector.getChatClient();
    ginjector.getCoreParts();
    ginjector.getGSpaceParts();
    ginjector.getPSpaceParts();
    ginjector.getContentViewerSelector().init();
  }
}
