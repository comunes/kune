package cc.kune.client;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class KuneGinModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(OnAppStartFactory.class).asEagerSingleton();
    }

}
