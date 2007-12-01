package org.ourproject.rack.filters.json;


public interface JSONMethodFinder {
	JsonMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType);
}
