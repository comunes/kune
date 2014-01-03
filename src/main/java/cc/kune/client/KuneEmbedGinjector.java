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

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.EmbedCoreGinModule;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.embed.EmbedSitebar;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.gspace.client.viewers.EmbedPresenter;
import cc.kune.wave.client.kspecific.WaveEmbedGinModule;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * The Interface KuneEmbedGinjector.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@GinModules({ EmbedCoreGinModule.class, WaveEmbedGinModule.class })
public interface KuneEmbedGinjector extends Ginjector {
  // FIXME: Seems that hablar is needed in some point (Wave actions, probably)

  /*
   * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
   * see the GWTPlatform doc
   */

  /**
   * Gets the cookies manager.
   * 
   * @return the cookies manager
   */
  AsyncProvider<CookiesManager> getCookiesManager();

  Provider<EmbedPresenter> getEmbedPresenter();

  EmbedSitebar getEmbedSitebar();

  /**
   * Gets the error handler.
   * 
   * @return the error handler
   */
  ErrorHandler getErrorHandler();

  /**
   * Gets the errors dialog.
   * 
   * @return the errors dialog
   */
  ErrorsDialog getErrorsDialog();

  /**
   * Gets the event bus.
   * 
   * @return the event bus
   */
  EventBus getEventBus();

  /**
   * Gets the event logger.
   * 
   * @return the event logger
   */
  EventBusWithLogging getEventBusWithLogger();

  /**
   * Gets the gui provider.
   * 
   * @return the gui provider
   */
  GuiProvider getGuiProvider();

  /**
   * Gets the gwt gui provider.
   * 
   * @return the gwt gui provider
   */
  GwtGuiProvider getGwtGuiProvider();

  /**
   * Gets the i18n.
   * 
   * @return the i18n
   */
  I18nTranslationService getI18n();

  /**
   * Gets the place manager.
   * 
   * @return the place manager
   */
  PlaceManager getPlaceManager();

  /**
   * Gets the site token listeners.
   * 
   * @return the site token listeners
   */
  SiteTokenListeners getSiteTokenListeners();

  /**
   * Gets the spiner presenter.
   * 
   * @return the spiner presenter
   */
  AsyncProvider<SpinerPresenter> getSpinerPresenter();

  /**
   * Gets the token matcher.
   * 
   * @return the token matcher
   */
  TokenMatcher getTokenMatcher();

}
