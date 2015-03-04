/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.embed.client;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.embed.client.actions.EmbedJsActions;
import cc.kune.embed.client.conf.EmbedConfiguration;
import cc.kune.embed.client.panels.EmbedPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

/**
 * The KuneEmbedEntryPoint is used to start kune complete client
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneEmbedEntryPoint implements EntryPoint {

  /** The ginjector. */
  private KuneEmbedGinjector ginjector;

  private native void onLoad() /*-{
		if ($wnd.kuneEmbedInit)
			$wnd.kuneEmbedInit();
  }-*/;

  @Override
  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable excep) {
        Log.error("Error in 'onPreBootstrap()' method", excep);
        NotifyUser.showProgress("Error");
        NotifyUser.logError(excep.getMessage());
      }
    });

    EmbedConfiguration.export();
    EmbedJsActions.export();

    ginjector = GWT.create(KuneEmbedGinjector.class);

    ginjector.getEventBusWithLogger();
    ginjector.getGwtGuiProvider();

    final EmbedPresenter embedPresenter = ginjector.getEmbedPresenter().get();
    embedPresenter.show();
    GWT.<CoreResources> create(CoreResources.class).coreCss().ensureInjected();
    com.google.gwt.user.client.History.addValueChangeHandler(embedPresenter);

    // Inspired in:
    // http://code.google.com/p/gwt-exporter/wiki/GettingStarted#Quick_start_guide
    onLoad();
  }
}
