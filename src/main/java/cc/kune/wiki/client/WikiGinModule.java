package cc.kune.wiki.client;

import cc.kune.wiki.client.actions.WikiClientActions;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class WikiGinModule extends AbstractPresenterModule {

  @Override
  protected void configure() {
    bind(WikiClientTool.class).in(Singleton.class);
    bind(WikiClientActions.class).in(Singleton.class);
  }

}
