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

import cc.kune.barters.client.BartersGinModule;
import cc.kune.barters.client.BartersGinjector;
import cc.kune.blogs.client.BlogsGinModule;
import cc.kune.blogs.client.BlogsGinjector;
import cc.kune.chat.client.ChatGinModule;
import cc.kune.chat.client.ChatGinjector;
import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.CoreGinModule;
import cc.kune.core.client.CoreParts;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogo;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.state.SessionExpirationManager;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter;
import cc.kune.docs.client.DocsGinModule;
import cc.kune.docs.client.DocsGinjector;
import cc.kune.events.client.EventsGinModule;
import cc.kune.events.client.EventsGinjector;
import cc.kune.gspace.client.GSpaceGinModule;
import cc.kune.gspace.client.GSpaceGinjector;
import cc.kune.hspace.client.HSpaceGinModule;
import cc.kune.hspace.client.HSpaceParts;
import cc.kune.hspace.client.HSpacePresenter;
import cc.kune.lists.client.ListsGinModule;
import cc.kune.lists.client.ListsGinjector;
import cc.kune.pspace.client.PSpaceGinModule;
import cc.kune.pspace.client.PSpaceParts;
import cc.kune.pspace.client.PSpacePresenter;
import cc.kune.tasks.client.TasksGinModule;
import cc.kune.tasks.client.TasksGinjector;
import cc.kune.trash.client.TrashGinModule;
import cc.kune.trash.client.TrashGinjector;
import cc.kune.wave.client.kspecific.WaveGinModule;
import cc.kune.wave.client.kspecific.WaveParts;
import cc.kune.wiki.client.WikiGinModule;
import cc.kune.wiki.client.WikiGinjector;

import com.calclab.hablar.client.HablarGinjector;
import com.calclab.hablar.client.HablarModule;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

// TODO: Auto-generated Javadoc
/**
 * The Interface KuneGinjector.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@GinModules({ KuneGinModule.class, CoreGinModule.class, WaveGinModule.class, PSpaceGinModule.class,
    HSpaceGinModule.class, GSpaceGinModule.class, DocsGinModule.class, BlogsGinModule.class,
    ChatGinModule.class, WikiGinModule.class, BartersGinModule.class, EventsGinModule.class,
    TasksGinModule.class, ListsGinModule.class, TrashGinModule.class, HablarModule.class })
public interface KuneGinjector extends Ginjector, GSpaceGinjector, DocsGinjector, BlogsGinjector,
    WikiGinjector, BartersGinjector, EventsGinjector, ChatGinjector, TasksGinjector, ListsGinjector,
    TrashGinjector, HablarGinjector {

  /*
   * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
   * see the GWTPlatform doc
   */

  /**
   * Gets the buddies and participation presenter.
   * 
   * @return the buddies and participation presenter
   */
  AsyncProvider<UserSNPresenter> getBuddiesAndParticipationPresenter();

  /**
   * Gets the cookies manager.
   * 
   * @return the cookies manager
   */
  AsyncProvider<CookiesManager> getCookiesManager();

  /**
   * Gets the core parts.
   * 
   * @return the core parts
   */
  CoreParts getCoreParts();

  /**
   * Gets the core presenter.
   * 
   * @return the core presenter
   */
  Provider<CorePresenter> getCorePresenter();

  /**
   * Gets the entity header presenter.
   * 
   * @return the entity header presenter
   */
  AsyncProvider<EntityHeaderPresenter> getEntityHeaderPresenter();

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
   * Gets the global shortcut register.
   * 
   * @return the global shortcut register
   */
  GlobalShortcutRegister getGlobalShortcutRegister();

  /**
   * Gets the group members presenter.
   * 
   * @return the group members presenter
   */
  AsyncProvider<GroupSNPresenter> getGroupMembersPresenter();

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
   * Gets the h space parts.
   * 
   * @return the h space parts
   */
  HSpaceParts getHSpaceParts();

  /**
   * Gets the h space presenter.
   * 
   * @return the h space presenter
   */
  AsyncProvider<HSpacePresenter> getHSpacePresenter();

  // GxtGuiProvider getGxtGuiProvider();

  /**
   * Gets the i18n.
   * 
   * @return the i18n
   */
  I18nTranslationService getI18n();

  /**
   * Gets the kune state manager.
   * 
   * @return the kune state manager
   */
  StateManager getKuneStateManager();

  /**
   * Gets the new group presenter.
   * 
   * @return the new group presenter
   */
  AsyncProvider<NewGroupPresenter> getNewGroupPresenter();

  /**
   * Gets the on app start factory.
   * 
   * @return the on app start factory
   */
  OnAppStartFactory getOnAppStartFactory();

  /**
   * Gets the place manager.
   * 
   * @return the place manager
   */
  PlaceManager getPlaceManager();

  /**
   * Gets the p space parts.
   * 
   * @return the p space parts
   */
  PSpaceParts getPSpaceParts();

  /**
   * Gets the p space presenter.
   * 
   * @return the p space presenter
   */
  AsyncProvider<PSpacePresenter> getPSpacePresenter();

  /**
   * Gets the register presenter.
   * 
   * @return the register presenter
   */
  AsyncProvider<RegisterPresenter> getRegisterPresenter();

  SessionChecker getSessionChecker();

  /**
   * Gets the session expiration manager.
   * 
   * @return the session expiration manager
   */
  SessionExpirationManager getSessionExpirationManager();

  /**
   * Gets the sign in presenter.
   * 
   * @return the sign in presenter
   */
  AsyncProvider<SignInPresenter> getSignInPresenter();

  /**
   * Gets the sitebar actions presenter.
   * 
   * @return the sitebar actions presenter
   */
  AsyncProvider<SitebarActionsPresenter> getSitebarActionsPresenter();

  /**
   * Gets the site logo.
   * 
   * @return the site logo
   */
  SiteLogo getSiteLogo();

  /**
   * Gets the site token listeners.
   * 
   * @return the site token listeners
   */
  SiteTokenListeners getSiteTokenListeners();

  /**
   * Gets the spaces tab presenter.
   * 
   * @return the spaces tab presenter
   */
  AsyncProvider<SpaceSelectorPresenter> getSpacesTabPresenter();

  /**
   * Gets the spiner presenter.
   * 
   * @return the spiner presenter
   */
  AsyncProvider<SpinerPresenter> getSpinerPresenter();

  /**
   * Gets the subtitles presenter.
   * 
   * @return the subtitles presenter
   */
  AsyncProvider<SubtitlesManager> getSubtitlesPresenter();

  /**
   * Gets the token matcher.
   * 
   * @return the token matcher
   */
  TokenMatcher getTokenMatcher();

  /**
   * Gets the user confirm presenter.
   * 
   * @return the user confirm presenter
   */
  AsyncProvider<UserConfirmPresenter> getUserConfirmPresenter();

  /**
   * Gets the wave parts.
   * 
   * @return the wave parts
   */
  WaveParts getWaveParts();

  /**
   * Gets the xml actions parser.
   * 
   * @return the xml actions parser
   */
  XMLActionsParser getXmlActionsParser();

}
