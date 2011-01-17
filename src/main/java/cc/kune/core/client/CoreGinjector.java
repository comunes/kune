package cc.kune.core.client;

import cc.kune.common.client.actions.gxtui.GxtGuiProvider;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

@GinModules({ CoreGinModule.class })
public interface CoreGinjector extends Ginjector {

    /*
     * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
     * see the GWTPlatform doc
     */

    AsyncProvider<CookiesManager> getCookiesManager();

    AsyncProvider<CorePresenter> getCorePresenter();

    ErrorHandler getErrorHandler();

    EventBus getEventBus();

    GlobalShortcutRegister getGlobalShortcutRegister();

    GuiProvider getGuiProvider();

    GxtGuiProvider getGxtGuiProvider();

    I18nTranslationService getI18n();

    PlaceManager getPlaceManager();

    ProxyFailureHandler getProxyFailureHandler();

    AsyncProvider<SitebarActionsPresenter> getSitebarActionsPresenter();

    AsyncProvider<SiteLogoPresenter> getSiteLogoPresenter();

    AsyncProvider<SpaceSelectorPresenter> getSpacesTabPresenter();

    AsyncProvider<SpinerPresenter> getSpinerPresenter();

    StateManager getStateManager();

    AsyncProvider<UserNotifierPresenter> getUserNotifierPresenter();
}