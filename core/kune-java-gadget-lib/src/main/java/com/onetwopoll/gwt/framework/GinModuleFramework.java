package com.onetwopoll.gwt.framework;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * This class binds specific implementations to interfaces which will be
 * automatically injected into methods annotated with @Inject.
 *
 * We can use other modules for unit tests.
 *
 */
public class GinModuleFramework extends AbstractGinModule {

  @Override
  protected void configure() {
    /* Services */
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
  }

}
