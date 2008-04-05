package org.ourproject.kune.rack.filters.rest;



public interface RESTMethodFinder {
	RESTMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
