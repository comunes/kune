package cc.kune.client;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatGinModule;
import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.CoreGinModule;
import cc.kune.core.client.auth.RegisterPresenter;
import cc.kune.core.client.auth.SignInPresenter;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.logs.EventBusWithLogging;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.pspace.client.PSpaceGinModule;
import cc.kune.pspace.client.PSpacePresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

@GinModules({ CoreGinModule.class, ChatGinModule.class, PSpaceGinModule.class })
public interface KuneGinjector extends Ginjector {

    /*
     * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
     * see the GWTPlatform doc
     */

    ChatClient getChatClient();

    AsyncProvider<CookiesManager> getCookiesManager();

    Provider<CorePresenter> getCorePresenter();

    ErrorHandler getErrorHandler();

    EventBus getEventBus();

    EventBusWithLogging getEventLogger();

    GlobalShortcutRegister getGlobalShortcutRegister();

    GuiProvider getGuiProvider();

    GwtGuiProvider getGwtGuiProvider();

    GxtGuiProvider getGxtGuiProvider();

    I18nTranslationService getI18n();

    ProxyFailureHandler getProxyFailureHandler();

    AsyncProvider<PSpacePresenter> getPSpacePresenter();

    AsyncProvider<RegisterPresenter> getRegisterPresenter();

    AsyncProvider<SignInPresenter> getSignInPresenter();

    AsyncProvider<SitebarActionsPresenter> getSitebarActionsPresenter();

    AsyncProvider<SiteLogoPresenter> getSiteLogoPresenter();

    SiteTokenListeners getSiteTokenListeners();

    AsyncProvider<SpaceSelectorPresenter> getSpacesTabPresenter();

    AsyncProvider<SpinerPresenter> getSpinerPresenter();

    StateManager getStateManager();

    AsyncProvider<UserNotifierPresenter> getUserNotifierPresenter();
}