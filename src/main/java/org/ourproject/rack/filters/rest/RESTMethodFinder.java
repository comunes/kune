package org.ourproject.rack.filters.rest;



public interface RESTMethodFinder {
	JsonMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
