package com.onetwopoll.gwt.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.inject.Singleton;

/**
 * The EventBus is a singleton class that facilitates communication amongst all loosely coupled
 * widgets in the application.
 * 
 * All functionality is already provided by the HandlerManager it extends.
 * 
 * WARNING: for some reason this has to implement EventBus, although no interface is needed. Apparently classes bound with bind(ClassName.class) cannot be a singleton
 * 
 */
@Singleton
public class EventBus extends HandlerManager  {

	public EventBus() {
		super(null);
		// TODO log event bus message
		GWT.log("EventBus constructor", null);
	}

}
