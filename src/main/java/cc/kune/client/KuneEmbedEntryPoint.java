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
package cc.kune.client;

import cc.kune.core.client.embed.EmbedConfiguration;
import cc.kune.core.client.embed.EmbedJsActions;
import cc.kune.gspace.client.viewers.EmbedPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The KuneEmbedEntryPoint is used to start kune complete client
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneEmbedEntryPoint extends AbstractKuneEntryPoint {

  private static native Element window() /*-{
		return $wnd;
  }-*/;

  /** The ginjector. */
  private KuneEmbedGinjector ginjector;

  /**
   * On module load continue.
   */
  @Override
  protected void onContinueModuleLoad() {
    ginjector.getEventBusWithLogger();
    ginjector.getGwtGuiProvider();
    final EmbedPresenter embedPresenter = ginjector.getEmbedPresenter().get();
    embedPresenter.forceReveal();
    ginjector.getSpinerPresenter();
    com.google.gwt.user.client.History.addValueChangeHandler(embedPresenter);

    // Inspired in:
    // http://code.google.com/p/gwt-exporter/wiki/GettingStarted#Quick_start_guide
    onLoad();
  }

  private native void onLoad() /*-{
		if ($wnd.kuneEmbedInit)
			$wnd.kuneEmbedInit();
  }-*/;

  /**
   * On start module load.
   */
  @Override
  protected void onStartModuleLoad() {
    EmbedConfiguration.export();
    EmbedJsActions.export();
    ginjector = GWT.create(KuneEmbedGinjector.class);
    DelayedBindRegistry.bind(ginjector);
  }

}
