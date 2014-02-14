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
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegisterDefault;
import cc.kune.common.client.shortcuts.GlobalShortcuts;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.shared.i18n.HasRTL;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.auth.AnonUsersManager;
import cc.kune.core.client.auth.LoginRememberManager;
import cc.kune.core.client.auth.LoginRememberManagerImpl;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.RegisterPresenter.RegisterView;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.auth.SignInPresenter.SignInView;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.cookies.CookiesManagerImpl;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.dnd.NotImplementedDropManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.GroupFieldFactory;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.groups.newgroup.NewGroupPanel;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter;
import cc.kune.core.client.groups.newgroup.NewGroupView;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStarter;
import cc.kune.core.client.init.AppStarterDefault;
import cc.kune.core.client.init.PrefetchUtilities;
import cc.kune.core.client.invitation.GroupInviteUserSearchPanel;
import cc.kune.core.client.invitation.ListInviteUserSearchPanel;
import cc.kune.core.client.notify.confirm.UserConfirmPanel;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter;
import cc.kune.core.client.notify.spiner.SpinerPanel;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.AbstractSignInAction;
import cc.kune.core.client.sitebar.AbstractSignOutAction;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.MyGroupsMenu;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.sitebar.SitebarActionsPanel;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.SitebarNewGroupLink;
import cc.kune.core.client.sitebar.SitebarSignInAction;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutAction;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.sitebar.logo.SiteLogo;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPanel;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sn.GroupSNPanel;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPanel;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.sn.actions.AddBuddieSearchPanel;
import cc.kune.core.client.sn.actions.AddMemberSearchPanel;
import cc.kune.core.client.sn.actions.registry.GroupSNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.GroupSNPendingsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNMenuItemsRegistry;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.LinkInterceptor;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionExpirationManager;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.ContentCacheDefault;
import cc.kune.core.client.state.impl.HistoryWrapperDefault;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.state.impl.SessionCheckerImpl;
import cc.kune.core.client.state.impl.SessionDefault;
import cc.kune.core.client.state.impl.StateManagerDefault;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.core.client.sub.SubtitlesWidget;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreViewImpl;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.client.ws.entheader.EntityHeaderPanel;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

// TODO: Auto-generated Javadoc
/**
 * The Class CoreGinModule.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CoreGinModule extends ExtendedGinModule {

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
    bind(RootPresenter.class).asEagerSingleton();
    bind(PlaceManager.class).to(KunePlaceManager.class).in(Singleton.class);

    // FIXME
    // http://code.google.com/p/gwt-platform/wiki/PortingV1?ts=1305893694&updated=PortingV1#No_more_FailureHandler
    // No more FailureHandler
    // The FailureHandler interface and its implementation,
    // DefaultFailureHandler have both been removed from GWTP. You will have to
    // remove the binding from your module. If you were using it, handle the new
    // and more versatile AsyncCallFailEvent instead.
    // bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);

    s(I18nUITranslationService.class);
    bind(HasRTL.class).to(I18nUITranslationService.class);
    bind(I18nTranslationService.class).to(I18nUITranslationService.class).in(Singleton.class);
    s(I18n.class);
    requestStaticInjection(I18n.class);

    bind(GlobalShortcutRegister.class).to(GlobalShortcutRegisterDefault.class).in(Singleton.class);
    bind(GlobalShortcuts.class).in(Singleton.class);

    s(AnonUsersManager.class);
    s(LinkInterceptor.class);

    // DnD
    s(KuneDragController.class);
    s(NotImplementedDropManager.class);

    s(UserFieldFactory.class);
    s(GroupFieldFactory.class);

    // Presenters
    bindPresenter(CorePresenter.class, CorePresenter.CoreView.class, CoreViewImpl.class,
        CorePresenter.CoreProxy.class);
    bindPresenter(SpinerPresenter.class, SpinerPresenter.SpinerView.class, SpinerPanel.class,
        SpinerPresenter.SpinerProxy.class);
    eagle(UserNotifierPopup.class);
    requestStaticInjection(NotifyUser.class);
    bindPresenter(SpaceSelectorPresenter.class, SpaceSelectorPresenter.SpaceSelectorView.class,
        SpaceSelectorPanel.class, SpaceSelectorPresenter.SpaceSelectorProxy.class);
    s(SiteLogo.class);
    bindPresenter(SitebarActionsPresenter.class, SitebarActionsPresenter.SitebarActionsView.class,
        SitebarActionsPanel.class, SitebarActionsPresenter.SitebarActionsProxy.class);
    bind(SitebarActions.class).to(SitebarActionsPresenter.class).in(Singleton.class);
    bindPresenter(NewGroupPresenter.class, NewGroupView.class, NewGroupPanel.class,
        NewGroupPresenter.NewGroupProxy.class);
    bindPresenter(GroupSNPresenter.class, GroupSNPresenter.GroupSNView.class, GroupSNPanel.class,
        GroupSNPresenter.GroupSNProxy.class);
    bindPresenter(UserSNPresenter.class, UserSNPresenter.UserSNView.class, UserSNPanel.class,
        UserSNPresenter.UserSNProxy.class);
    bindPresenter(EntityHeaderPresenter.class, EntityHeaderPresenter.EntityHeaderView.class,
        EntityHeaderPanel.class, EntityHeaderPresenter.EntityHeaderProxy.class);
    bindPresenter(SignInPresenter.class, SignInView.class, SignInPanel.class,
        SignInPresenter.SignInProxy.class);
    bindPresenter(RegisterPresenter.class, RegisterView.class, RegisterPanel.class,
        RegisterPresenter.RegisterProxy.class);
    bindPresenter(UserConfirmPresenter.class, UserConfirmPresenter.UserConfirmView.class,
        UserConfirmPanel.class, UserConfirmPresenter.UserConfirmProxy.class);
    bindPresenter(SubtitlesManager.class, SubtitlesManager.SubtitlesView.class, SubtitlesWidget.class,
        SubtitlesManager.SubtitlesProxy.class);

    bind(LoginRememberManager.class).to(LoginRememberManagerImpl.class).in(Singleton.class);
    bind(SignIn.class).to(SignInPresenter.class).in(Singleton.class);
    bind(AbstractSignInAction.class).to(SitebarSignInAction.class).in(Singleton.class);
    bind(AbstractSignOutAction.class).to(SitebarSignOutAction.class).in(Singleton.class);

    bind(Register.class).to(RegisterPresenter.class).in(Singleton.class);
    bind(NewGroup.class).to(NewGroupPresenter.class).in(Singleton.class);
    bind(EntityHeader.class).to(EntityHeaderPresenter.class).in(Singleton.class);

    s(UserMessagesPresenter.class);
    s(UserMessagesPanel.class);
    // bind(MessagePanelView.class).to(MessagePanel.class);

    // UI
    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);

    // s(GxtGuiProvider.class);
    // s(GwtGuiProvider.class);

    bind(MaskWidgetView.class).to(MaskWidget.class).in(Singleton.class);

    // Core App
    bind(Session.class).to(SessionDefault.class).in(Singleton.class);
    requestStaticInjection(SessionInstance.class);
    requestStaticInjection(StateTokenUtils.class);
    bind(SessionChecker.class).to(SessionCheckerImpl.class).in(Singleton.class);
    s(SessionExpirationManager.class);
    s(ErrorHandler.class);
    requestStaticInjection(AsyncCallbackSimple.class);
    s(StateManagerDefault.class);
    bind(StateManager.class).to(StateManagerDefault.class).in(Singleton.class);
    s(AccessRightsClientManager.class);
    bind(ContentCache.class).to(ContentCacheDefault.class).in(Singleton.class);
    bind(HistoryWrapper.class).to(HistoryWrapperDefault.class).in(Singleton.class);
    s(PrefetchUtilities.class);
    bind(AppStarter.class).to(AppStarterDefault.class).in(Singleton.class);
    bind(CookiesManager.class).to(CookiesManagerImpl.class).in(Singleton.class);
    s(BeforeSignOut.class);
    eagle(SiteTokenListeners.class);
    s(SiteTokens.class);
    s(ReservedWordsRegistryDTO.class);
    eagle(TokenMatcher.class);
    s(ActionRegistryByType.class);
    s(ContentCapabilitiesRegistry.class);
    s(NewMenusForTypeIdsRegistry.class);
    eagle(CoreParts.class);

    // SN
    s(ClientFileDownloadUtils.class);
    s(GroupSNAdminsMenuItemsRegistry.class);
    s(GroupSNCollabsMenuItemsRegistry.class);
    s(GroupSNPendingsMenuItemsRegistry.class);
    s(UserSNMenuItemsRegistry.class);
    s(GroupSNConfActions.class);
    s(UserSNConfActions.class);
    s(AddBuddieSearchPanel.class);
    s(AddMemberSearchPanel.class);
    s(GroupInviteUserSearchPanel.class);
    s(ListInviteUserSearchPanel.class);

    s(ContentServiceHelper.class);
    s(SocialNetServiceHelper.class);
    s(ListsServiceHelper.class);

    s(MyGroupsMenu.class);
    s(SiteUserOptionsPresenter.class);
    s(SiteUserOptions.class, SiteUserOptionsPresenter.class);
    s(SitebarNewGroupLink.class);
    s(SitebarSignInLink.class);
    s(SitebarSignOutLink.class);

    s(ErrorsDialog.class);
    s(XMLActionsParser.class);

  }
}
