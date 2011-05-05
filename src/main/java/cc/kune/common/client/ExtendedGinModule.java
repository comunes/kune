package cc.kune.common.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public abstract class ExtendedGinModule extends AbstractPresenterModule {

    protected void eagle(final Class<?> type) {
        bind(type).asEagerSingleton();
    }

    protected void s(final Class<?> type) {
        bind(type).in(Singleton.class);
    }

    protected <V, W> void s(final Class<V> type, final Class<? extends V> typeImpl) {
        bind(type).to(typeImpl).in(Singleton.class);
    }

}
