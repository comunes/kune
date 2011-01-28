package cc.kune.core.client;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.DefaultGlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.RegisterView;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.auth.SignInView;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.cookies.CookiesManagerImpl;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStarter;
import cc.kune.core.client.init.AppStarterDefault;
import cc.kune.core.client.init.PrefetchUtilities;
import cc.kune.core.client.logs.EventsLogger;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.msgs.UserNotifierViewImpl;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.notify.spiner.SpinerViewImpl;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.SitebarActionsViewImpl;
import cc.kune.core.client.sitebar.SitebarNewGroupLink;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink.BeforeSignOut;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoViewImpl;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorViewImpl;
import cc.kune.core.client.state.ContentProvider;
import cc.kune.core.client.state.ContentProviderDefault;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.HistoryWrapperDefault;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionDefault;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateManagerDefault;
import cc.kune.core.client.ui.QTipsHelper;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreViewImpl;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.msgs.client.UserMessagesPanel;
import cc.kune.msgs.client.UserMessagesPresenter;
import cc.kune.wspace.client.WsArmor;
import cc.kune.wspace.client.WsArmorImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
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
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        // bind(PlaceManager.class).to(CorePlaceManager.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();
        bind(QTipsHelper.class).asEagerSingleton();
        bind(EventsLogger.class).asEagerSingleton();
        bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);
        bind(I18nUITranslationService.class).in(Singleton.class);
        bind(I18nTranslationService.class).to(I18nUITranslationService.class).in(Singleton.class);

        bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
        bind(GxtGuiProvider.class).in(Singleton.class);
        bind(GwtGuiProvider.class).in(Singleton.class);
        bind(GlobalShortcutRegister.class).to(DefaultGlobalShortcutRegister.class).in(Singleton.class);

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
                SitebarActionsViewImpl.class, SitebarActionsPresenter.SitebarActionsProxy.class);

        bindPresenter(SignInPresenter.class, SignInView.class, SignInPanel.class, SignInPresenter.SignInProxy.class);
        bindPresenter(RegisterPresenter.class, RegisterView.class, RegisterPanel.class,
                RegisterPresenter.RegisterProxy.class);
        bind(SignIn.class).to(SignInPresenter.class).in(Singleton.class);
        bind(Register.class).to(RegisterPresenter.class).in(Singleton.class);

        bind(UserMessagesPresenter.class).in(Singleton.class);
        bind(UserMessagesPanel.class).in(Singleton.class);

        bind(WsArmorImpl.class).in(Singleton.class);
        bind(WsArmor.class).to(WsArmorImpl.class).in(Singleton.class);
        bind(CookiesManager.class).to(CookiesManagerImpl.class).in(Singleton.class);
        bind(Session.class).to(SessionDefault.class).in(Singleton.class);

        bind(ErrorHandler.class).in(Singleton.class);
        bind(StateManagerDefault.class).in(Singleton.class);
        bind(StateManager.class).to(StateManagerDefault.class).in(Singleton.class);
        bind(ContentProvider.class).to(ContentProviderDefault.class).in(Singleton.class);
        bind(HistoryWrapper.class).to(HistoryWrapperDefault.class).in(Singleton.class);
        bind(PrefetchUtilities.class).in(Singleton.class);
        bind(AppStarter.class).to(AppStarterDefault.class).in(Singleton.class);

        bind(SitebarNewGroupLink.class).in(Singleton.class);
        bind(SitebarSignInLink.class).in(Singleton.class);
        bind(SitebarSignOutLink.class).in(Singleton.class);
        bind(BeforeSignOut.class).in(Singleton.class);
        bind(SiteTokenListeners.class).asEagerSingleton();
    }
}
