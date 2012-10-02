package com.example.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * Sample of GinModule (but with GWTPlatform)
 * 
 */
public class HelloWordGinModule extends AbstractPresenterModule {

  /*
   * You have to add here all the GWTPresenters (as Provider or AsyncProvider)
   * in the injector. See the GWTPlatform doc
   */
  public interface HelloWordGinjector extends Ginjector {
    AsyncProvider<HelloWorldPresenter> getHelloWorldPresenter();
  }

  @Override
  protected void configure() {
    bindPresenter(HelloWorldPresenter.class, HelloWorldPresenter.HelloWorldView.class,
        HelloWorldPanel.class, HelloWorldPresenter.HelloWorldProxy.class);
    bind(HelloWorldActions.class).in(Singleton.class);
  }

}
