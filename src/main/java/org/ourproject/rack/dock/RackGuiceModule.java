package org.ourproject.rack.dock;

import org.ourproject.rack.filters.rest.DefaultRESTMethodFinder;
import org.ourproject.rack.filters.rest.RESTMethodFinder;

import com.google.inject.AbstractModule;

public class RackGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RESTMethodFinder.class).to(DefaultRESTMethodFinder.class);
	}
	

}
