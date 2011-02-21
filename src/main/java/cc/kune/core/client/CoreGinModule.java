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

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.DefaultGlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.RegisterView;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.auth.SignInView;
import cc.kune.core.client.auth.UserPassAutocompleteManager;
import cc.kune.core.client.auth.UserPassAutocompleteManagerImpl;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.cookies.CookiesManagerImpl;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.groups.newgroup.NewGroupPanel;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter;
import cc.kune.core.client.groups.newgroup.NewGroupView;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStarter;
import cc.kune.core.client.init.AppStarterDefault;
import cc.kune.core.client.init.PrefetchUtilities;
import cc.kune.core.client.logs.EventBusWithLogging;
import cc.kune.core.client.notify.confirm.UserConfirmPanel;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.msgs.UserNotifierViewImpl;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.notify.spiner.SpinerViewImpl;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarActionsPanel;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.SitebarNewGroupLink;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoViewImpl;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorViewImpl;
import cc.kune.core.client.sn.GroupMembersPanel;
import cc.kune.core.client.sn.GroupMembersPresenter;
import cc.kune.core.client.sn.actions.registry.GroupMembersActionsRegistry;
import cc.kune.core.client.sn.actions.registry.SNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNPendingsMenuItemsRegistry;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.ContentProvider;
import cc.kune.core.client.state.ContentProviderDefault;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.HistoryWrapperDefault;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionDefault;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateManagerDefault;
import cc.kune.core.client.ui.footer.license.EntityLicensePanel;
import cc.kune.core.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreViewImpl;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;
import cc.kune.gspace.client.WsArmorImpl;
import cc.kune.msgs.client.UserMessagesPanel;
import cc.kune.msgs.client.UserMessagesPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class CoreGinModule extends AbstractPresenterModule {

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
        bind(I18nUITranslationService.class).in(Singleton.class);
        bind(I18nTranslationService.class).to(I18nUITranslationService.class).in(Singleton.class);

        // Presenters
        bindPresenter(CorePresenter.class, CorePresenter.CoreView.class, CoreViewImpl.class,
                CorePresenter.CoreProxy.class);
        bindPresenter(SpinerPresenter.class, SpinerPresenter.SpinerView.class, SpinerViewImpl.class,
                SpinerPresenter.SpinerProxy.class);
        bindPresenter(UserNotifierPresenter.class, UserNotifierPresenter.UserNotifierView.class,
                UserNotifierViewImpl.class, UserNotifierProxy.class);

        bindPresenter(SpaceSelectorPresenter.class, SpaceSelectorPresenter.SpaceSelectorView.class,
                SpaceSelectorViewImpl.class, SpaceSelectorPresenter.SpaceSelectorProxy.class);
        bindPresenter(SiteLogoPresenter.class, SiteLogoPresenter.SiteLogoView.class, SiteLogoViewImpl.class,
                SiteLogoPresenter.SiteLogoProxy.class);
        bindPresenter(SitebarActionsPresenter.class, SitebarActionsPresenter.SitebarActionsView.class,
                SitebarActionsPanel.class, SitebarActionsPresenter.SitebarActionsProxy.class);
        bindPresenter(NewGroupPresenter.class, NewGroupView.class, NewGroupPanel.class,
                NewGroupPresenter.NewGroupProxy.class);
        bindPresenter(GroupMembersPresenter.class, GroupMembersPresenter.GroupMembersView.class,
                GroupMembersPanel.class, GroupMembersPresenter.GroupMembersProxy.class);
        bindPresenter(EntityLicensePresenter.class, EntityLicensePresenter.EntityLicenseView.class,
                EntityLicensePanel.class, EntityLicensePresenter.EntityLicenseProxy.class);

        bind(UserPassAutocompleteManager.class).to(UserPassAutocompleteManagerImpl.class).in(Singleton.class);
        bindPresenter(SignInPresenter.class, SignInView.class, SignInPanel.class, SignInPresenter.SignInProxy.class);
        bindPresenter(RegisterPresenter.class, RegisterView.class, RegisterPanel.class,
                RegisterPresenter.RegisterProxy.class);
        bind(SignIn.class).to(SignInPresenter.class).in(Singleton.class);
        bind(Register.class).to(RegisterPresenter.class).in(Singleton.class);
        bind(NewGroup.class).to(NewGroupPresenter.class).in(Singleton.class);

        bind(UserMessagesPresenter.class).in(Singleton.class);
        bind(UserMessagesPanel.class).in(Singleton.class);
        bindPresenter(UserConfirmPresenter.class, UserConfirmPresenter.UserConfirmView.class, UserConfirmPanel.class,
                UserConfirmPresenter.UserConfirmProxy.class);

        // bind(MessagePanelView.class).to(MessagePanel.class);

        // UI
        bind(WsArmorImpl.class).in(Singleton.class);
        bind(WsArmor.class).to(WsArmorImpl.class).in(Singleton.class);
        bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
        bind(GxtGuiProvider.class).in(Singleton.class);
        bind(GwtGuiProvider.class).in(Singleton.class);
        bind(GlobalShortcutRegister.class).to(DefaultGlobalShortcutRegister.class).in(Singleton.class);
        bind(MaskWidgetView.class).to(MaskWidget.class).in(Singleton.class);

        // Core App
        bind(Session.class).to(SessionDefault.class).in(Singleton.class);
        bind(ErrorHandler.class).in(Singleton.class);
        bind(StateManagerDefault.class).in(Singleton.class);
        bind(StateManager.class).to(StateManagerDefault.class).in(Singleton.class);
        bind(AccessRightsClientManager.class).in(Singleton.class);
        bind(ContentProvider.class).to(ContentProviderDefault.class).in(Singleton.class);
        bind(HistoryWrapper.class).to(HistoryWrapperDefault.class).in(Singleton.class);
        bind(PrefetchUtilities.class).in(Singleton.class);
        bind(AppStarter.class).to(AppStarterDefault.class).in(Singleton.class);
        bind(CookiesManager.class).to(CookiesManagerImpl.class).in(Singleton.class);
        bind(BeforeSignOut.class).in(Singleton.class);
        bind(SiteTokenListeners.class).asEagerSingleton();
        bind(CoreParts.class).asEagerSingleton();

        // SN
        bind(SNAdminsMenuItemsRegistry.class).in(Singleton.class);
        bind(SNCollabsMenuItemsRegistry.class).in(Singleton.class);
        bind(SNPendingsMenuItemsRegistry.class).in(Singleton.class);
        bind(GroupMembersActionsRegistry.class).in(Singleton.class);

        bind(SiteUserOptionsPresenter.class).in(Singleton.class);
        bind(SiteUserOptions.class).to(SiteUserOptionsPresenter.class).in(Singleton.class);
        bind(SitebarNewGroupLink.class).in(Singleton.class);
        bind(SitebarSignInLink.class).in(Singleton.class);
        bind(SitebarSignOutLink.class).in(Singleton.class);

    }
}
