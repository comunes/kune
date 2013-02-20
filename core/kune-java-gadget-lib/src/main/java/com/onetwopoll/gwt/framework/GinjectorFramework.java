package com.onetwopoll.gwt.framework;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * This injector provides injection functions for the classes required in the root of the project
 * such as the EventBus or the MainPresenter.
 *
 * The Gin Module used to resolve the types is given in the @GinModules annotation
 *
 */
@GinModules(GinModuleFramework.class)
public interface GinjectorFramework extends Ginjector {

  EventBus getEventBus();

}
