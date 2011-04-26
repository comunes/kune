package cc.kune.docs.client;

import cc.kune.docs.client.actions.DocsClientActions;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DocsGinModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(DocsClientTool.class).in(Singleton.class);
        bind(DocsClientActions.class).in(Singleton.class);
    }

}
