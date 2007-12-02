package org.ourproject.rack.filters.rest;


import com.google.inject.AbstractModule;

public class RESTServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RESTMethodFinder.class).to(DefaultRESTMethodFinder.class);
		bind(RESTSerializer.class).to(JSONLibRESTSerializer.class);
	}
}
