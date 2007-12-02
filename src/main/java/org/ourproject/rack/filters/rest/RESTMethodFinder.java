package org.ourproject.rack.filters.rest;



public interface RESTMethodFinder {
	RESTMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
