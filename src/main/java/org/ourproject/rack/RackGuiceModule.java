package org.ourproject.rack;

import org.ourproject.rack.filters.rest.DefaultRESTMethodFinder;
import org.ourproject.rack.filters.rest.XStreamRESTSerializer;
import org.ourproject.rack.filters.rest.RESTMethodFinder;
import org.ourproject.rack.filters.rest.RESTSerializer;

import com.google.inject.AbstractModule;

public class RackGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RESTMethodFinder.class).to(DefaultRESTMethodFinder.class);
		bind(RESTSerializer.class).to(XStreamRESTSerializer.class);
	}
}
