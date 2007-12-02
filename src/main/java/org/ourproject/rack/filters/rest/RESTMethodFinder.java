package org.ourproject.rack.filters.rest;

import org.ourproject.rack.filters.rest.Parameters;


public interface RESTMethodFinder {
	JsonMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
