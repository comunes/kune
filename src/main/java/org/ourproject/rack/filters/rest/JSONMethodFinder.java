package org.ourproject.rack.filters.rest;

import org.ourproject.rack.filters.json.Parameters;


public interface JSONMethodFinder {
	JsonMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
