package cc.kune.core.client;

import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.sitebar.logo.SiteLogoPresenter;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

@GinModules({ CoreGinModule.class })
public interface CoreGinjector extends Ginjector {

    ErrorHandler getErrorHandler();

    AsyncProvider<CorePresenter> getCorePresenter();

    AsyncProvider<CookiesManager> getCookiesManager();

    EventBus getEventBus();

    PlaceManager getPlaceManager();

    ProxyFailureHandler getProxyFailureHandler();

    AsyncProvider<SpinerPresenter> getSpinerPresenter();

    AsyncProvider<SiteLogoPresenter> getSiteLogoPresenter();

    AsyncProvider<UserNotifierPresenter> getUserNotifierPresenter();

    I18nTranslationService getI18n();

    StateManager getStateManager();

}