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

import cc.kune.barters.client.BartersGinModule;
import cc.kune.barters.client.BartersGinjector;
import cc.kune.blogs.client.BlogsGinModule;
import cc.kune.blogs.client.BlogsGinjector;
import cc.kune.chat.client.ChatGinModule;
import cc.kune.chat.client.ChatGinjector;
import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.log.EventBusWithLogging;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.CoreGinModule;
import cc.kune.core.client.CoreParts;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;
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
import cc.kune.wave.client.WaveGinModule;
import cc.kune.wave.client.WaveParts;
import cc.kune.wiki.client.WikiGinModule;
import cc.kune.wiki.client.WikiGinjector;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

@GinModules({ KuneGinModule.class, CoreGinModule.class, WaveGinModule.class, PSpaceGinModule.class,
    HSpaceGinModule.class, GSpaceGinModule.class, DocsGinModule.class, BlogsGinModule.class,
    ChatGinModule.class, WikiGinModule.class, BartersGinModule.class, EventsGinModule.class,
    TasksGinModule.class, ListsGinModule.class })
public interface KuneGinjector extends Ginjector, GSpaceGinjector, DocsGinjector, BlogsGinjector,
    WikiGinjector, BartersGinjector, EventsGinjector, ChatGinjector, TasksGinjector, ListsGinjector {

  /*
   * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
   * see the GWTPlatform doc
   */

  AsyncProvider<UserSNPresenter> getBuddiesAndParticipationPresenter();

  AsyncProvider<CookiesManager> getCookiesManager();

  CoreParts getCoreParts();

  Provider<CorePresenter> getCorePresenter();

  AsyncProvider<EntityHeaderPresenter> getEntityHeaderPresenter();

  ErrorHandler getErrorHandler();

  EventBus getEventBus();

  EventBusWithLogging getEventLogger();

  GlobalShortcutRegister getGlobalShortcutRegister();

  AsyncProvider<GroupSNPresenter> getGroupMembersPresenter();

  GuiProvider getGuiProvider();

  GwtGuiProvider getGwtGuiProvider();

  // GxtGuiProvider getGxtGuiProvider();

  HSpaceParts getHSpaceParts();

  AsyncProvider<HSpacePresenter> getHSpacePresenter();

  I18nTranslationService getI18n();

  AsyncProvider<NewGroupPresenter> getNewGroupPresenter();

  OnAppStartFactory getOnAppStartFactory();

  ProxyFailureHandler getProxyFailureHandler();

  PSpaceParts getPSpaceParts();

  AsyncProvider<PSpacePresenter> getPSpacePresenter();

  AsyncProvider<RegisterPresenter> getRegisterPresenter();

  AsyncProvider<SignInPresenter> getSignInPresenter();

  AsyncProvider<SitebarActionsPresenter> getSitebarActionsPresenter();

  AsyncProvider<SiteLogoPresenter> getSiteLogoPresenter();

  SiteTokenListeners getSiteTokenListeners();

  AsyncProvider<SpaceSelectorPresenter> getSpacesTabPresenter();

  AsyncProvider<SpinerPresenter> getSpinerPresenter();

  StateManager getStateManager();

  AsyncProvider<SubtitlesManager> getSubtitlesPresenter();

  TokenMatcher getTokenMatcher();

  AsyncProvider<UserConfirmPresenter> getUserConfirmPresenter();

  AsyncProvider<UserNotifierPresenter> getUserNotifierPresenter();

  WaveParts getWaveParts();

  XMLActionsParser getXmlActionsParser();

}
