package org.ourproject.rack.dock;

import org.ourproject.rack.filters.rest.DefaultJSONMethodFinder;
import org.ourproject.rack.filters.rest.JSONMethodFinder;

import com.google.inject.AbstractModule;

public class RackGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(JSONMethodFinder.class).to(DefaultJSONMethodFinder.class);
	}
	

}
