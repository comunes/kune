package cc.kune.core.client;

import org.ourproject.kune.ws.armor.client.Body;
import org.ourproject.kune.ws.armor.client.IBody;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.ContentProvider;
import cc.kune.core.client.state.ContentProviderDefault;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.HistoryWrapperDefault;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionDefault;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * The Class Core GinModule.
 */
public class CoreGinModule extends AbstractGinModule {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        bind(IBody.class).to(Body.class).in(Singleton.class);
        bind(Session.class).to(SessionDefault.class).in(Singleton.class);
        bind(I18nTranslationService.class).to(I18nUITranslationService.class).in(Singleton.class);
        // bind(ErrorHandler.class).in(Singleton.class);
        bind(ContentProvider.class).to(ContentProviderDefault.class).in(Singleton.class);
        bind(HistoryWrapper.class).to(HistoryWrapperDefault.class).in(Singleton.class);
        // bind(StateManager.class).to(StateManagerDefault.class).in(Singleton.class);
    }

}
