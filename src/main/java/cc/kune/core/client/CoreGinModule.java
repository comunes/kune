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
package cc.kune.core.client;

import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.log.EventBusWithLogging;
import cc.kune.common.client.shortcuts.DefaultGlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.shared.i18n.HasRTL;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.auth.AnonUsersManager;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.RegisterPresenter.RegisterView;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.auth.SignInPresenter.SignInView;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.auth.UserPassAutocompleteManager;
import cc.kune.core.client.auth.UserPassAutocompleteManagerImpl;
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
import cc.kune.core.client.notify.confirm.UserConfirmPanel;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.msgs.UserNotifierViewImpl;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.notify.spiner.SpinerViewImpl;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarActions;
import cc.kune.core.client.sitebar.SitebarActionsPanel;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.SitebarNewGroupLink;
import cc.kune.core.client.sitebar.SitebarSignInLink;
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
import cc.kune.core.client.state.ContentCacheDefault;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.HistoryWrapperDefault;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionDefault;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateManagerDefault;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.core.client.sub.SubtitlesWidget;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreViewImpl;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.client.ws.entheader.EntityHeaderPanel;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.msgs.client.UserMessagesPanel;
import cc.kune.msgs.client.UserMessagesPresenter;

import com.calclab.emite.core.client.services.Services;
import com.calclab.emite.core.client.services.gwt.GWTServices;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class CoreGinModule extends ExtendedGinModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    bind(EventBus.class).to(EventBusWithLogging.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(RootPresenter.class).asEagerSingleton();
    bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);
    s(I18nUITranslationService.class);
    bind(HasRTL.class).to(I18nUITranslationService.class);
    bind(I18nTranslationService.class).to(I18nUITranslationService.class).in(Singleton.class);
    bind(GlobalShortcutRegister.class).to(DefaultGlobalShortcutRegister.class).in(Singleton.class);
    s(AnonUsersManager.class);

    // DnD
    s(KuneDragController.class);
    s(NotImplementedDropManager.class);

    s(UserFieldFactory.class);
    s(GroupFieldFactory.class);

    // Presenters
    bindPresenter(CorePresenter.class, CorePresenter.CoreView.class, CoreViewImpl.class,
        CorePresenter.CoreProxy.class);
    bindPresenter(SpinerPresenter.class, SpinerPresenter.SpinerView.class, SpinerViewImpl.class,
        SpinerPresenter.SpinerProxy.class);
    bindPresenter(UserNotifierPresenter.class, UserNotifierPresenter.UserNotifierView.class,
        UserNotifierViewImpl.class, UserNotifierProxy.class);
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

    bind(UserPassAutocompleteManager.class).to(UserPassAutocompleteManagerImpl.class).in(Singleton.class);
    bind(SignIn.class).to(SignInPresenter.class).in(Singleton.class);
    bind(Register.class).to(RegisterPresenter.class).in(Singleton.class);
    bind(NewGroup.class).to(NewGroupPresenter.class).in(Singleton.class);
    bind(EntityHeader.class).to(EntityHeaderPresenter.class).in(Singleton.class);

    s(UserMessagesPresenter.class);
    s(UserMessagesPanel.class);
    // bind(MessagePanelView.class).to(MessagePanel.class);

    // UI
    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);

    // FIXME: revise this!
    s(GxtGuiProvider.class);
    // s(GwtGuiProvider.class);

    bind(MaskWidgetView.class).to(MaskWidget.class).in(Singleton.class);

    // Core App
    bind(Session.class).to(SessionDefault.class).in(Singleton.class);
    s(ErrorHandler.class);
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
    s(GroupSNAdminsMenuItemsRegistry.class);
    s(GroupSNCollabsMenuItemsRegistry.class);
    s(GroupSNPendingsMenuItemsRegistry.class);
    s(UserSNMenuItemsRegistry.class);
    s(GroupSNConfActions.class);
    s(UserSNConfActions.class);
    s(AddBuddieSearchPanel.class);
    s(AddMemberSearchPanel.class);

    s(SiteUserOptionsPresenter.class);
    s(SiteUserOptions.class, SiteUserOptionsPresenter.class);
    s(SitebarNewGroupLink.class);
    s(SitebarSignInLink.class);
    s(SitebarSignOutLink.class);

    s(ErrorsDialog.class);
    s(XMLActionsParser.class);
    s(Services.class, GWTServices.class);
  }

}
