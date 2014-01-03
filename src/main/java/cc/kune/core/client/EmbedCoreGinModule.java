/*
 *
x * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client;

import cc.kune.client.KunePlaceManager;
import cc.kune.common.client.actions.ui.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.client.msgs.UserMessagesPanel;
import cc.kune.common.client.msgs.UserMessagesPresenter;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.UserNotifierPopup;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.shared.i18n.HasRTL;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.i18n.I18nTranslationServiceMocked;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.cookies.CookiesManagerImpl;
import cc.kune.core.client.embed.EmbedSignInAction;
import cc.kune.core.client.embed.EmbedSignOutAction;
import cc.kune.core.client.embed.EmbedSitebar;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.GroupFieldFactory;
import cc.kune.core.client.notify.spiner.SpinerPanel;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.AbstractSignInAction;
import cc.kune.core.client.sitebar.AbstractSignOutAction;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateManagerMock;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.ContentCacheDefault;
import cc.kune.core.client.state.impl.DummySessionChecker;
import cc.kune.core.client.state.impl.HistoryWrapperDefault;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.state.impl.SessionDefault;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.gspace.client.viewers.EmbedPanel;
import cc.kune.gspace.client.viewers.EmbedPresenter;
import cc.kune.gspace.client.viewers.EmbedRootPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * The Class CoreGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbedCoreGinModule extends ExtendedGinModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    bind(EventBus.class).to(EventBusWithLogging.class).in(Singleton.class);
    requestStaticInjection(EventBusInstance.class);

    // gwtplatform
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(RootPresenter.class).to(EmbedRootPresenter.class).asEagerSingleton();
    bind(PlaceManager.class).to(KunePlaceManager.class).in(Singleton.class);

    s(I18nTranslationServiceMocked.class);
    bind(HasRTL.class).to(I18nTranslationServiceMocked.class);
    bind(I18nTranslationService.class).to(I18nTranslationServiceMocked.class).in(Singleton.class);
    s(I18n.class);
    requestStaticInjection(I18n.class);

    s(UserFieldFactory.class);
    s(GroupFieldFactory.class);

    // Presenters
    bindPresenter(SpinerPresenter.class, SpinerPresenter.SpinerView.class, SpinerPanel.class,
        SpinerPresenter.SpinerProxy.class);
    eagle(UserNotifierPopup.class);
    requestStaticInjection(NotifyUser.class);

    bindPresenter(EmbedPresenter.class, EmbedPresenter.EmbedView.class, EmbedPanel.class,
        EmbedPresenter.EmbedProxy.class);
    s(EmbedPresenter.class);
    s(EmbedSitebar.class);

    bind(AbstractSignInAction.class).to(EmbedSignInAction.class).in(Singleton.class);
    bind(AbstractSignOutAction.class).to(EmbedSignOutAction.class).in(Singleton.class);
    s(UserMessagesPresenter.class);
    s(UserMessagesPanel.class);

    // UI
    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);

    bind(MaskWidgetView.class).to(MaskWidget.class).in(Singleton.class);

    // Core App
    bind(Session.class).to(SessionDefault.class).in(Singleton.class);
    bind(SessionChecker.class).to(DummySessionChecker.class).in(Singleton.class);
    requestStaticInjection(StateTokenUtils.class);
    requestStaticInjection(SessionInstance.class);
    // s(SessionExpirationManager.class);
    s(ErrorHandler.class);
    requestStaticInjection(AsyncCallbackSimple.class);
    s(StateManagerMock.class);
    bind(StateManager.class).to(StateManagerMock.class).in(Singleton.class);
    bind(ContentCache.class).to(ContentCacheDefault.class).in(Singleton.class);
    bind(HistoryWrapper.class).to(HistoryWrapperDefault.class).in(Singleton.class);
    bind(CookiesManager.class).to(CookiesManagerImpl.class).in(Singleton.class);
    s(BeforeSignOut.class);
    eagle(SiteTokenListeners.class);
    s(SiteTokens.class);
    s(ReservedWordsRegistryDTO.class);
    eagle(TokenMatcher.class);
    s(ActionRegistryByType.class);

    s(ClientFileDownloadUtils.class);
    s(ErrorsDialog.class);
  }
}
