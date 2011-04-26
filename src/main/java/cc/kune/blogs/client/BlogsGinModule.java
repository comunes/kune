package cc.kune.blogs.client;

import cc.kune.blogs.client.actions.BlogsClientActions;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BlogsGinModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(BlogsClientTool.class).in(Singleton.class);
        bind(BlogsClientActions.class).in(Singleton.class);
    }

}
