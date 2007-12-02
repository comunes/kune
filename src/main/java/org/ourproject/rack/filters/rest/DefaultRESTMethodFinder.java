package org.ourproject.rack.filters.rest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultRESTMethodFinder implements RESTMethodFinder {
	private static final Log log = LogFactory.getLog(DefaultRESTMethodFinder.class);
	
	private HashMap<Class<?>, RESTServiceDefinition> definitionCache;

	public DefaultRESTMethodFinder() {
		this.definitionCache = new HashMap<Class<?>, RESTServiceDefinition>();
	}
	
	public RESTMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType) {
		RESTServiceDefinition serviceDefinition = getServiceDefinition(serviceType);
		Method[] serviceMethods = serviceDefinition.getMethods();
		log.debug("SERVICE METHODS: " + Arrays.toString(serviceMethods));
		for (Method method : serviceMethods) {
			log.debug("CHEKING: " + method.toString());
			if (method.getName().equals(methodName)) {
				REST methodAnnotation = method.getAnnotation(REST.class);
				if (checkParams(methodAnnotation, parameters)) {
					return new RESTMethod(method, methodAnnotation.params(), parameters, methodAnnotation.format());
				}
			}
		}
		return null;
	}

	private RESTServiceDefinition getServiceDefinition(Class<?> serviceType) {
		RESTServiceDefinition serviceDefinition = definitionCache.get(serviceType);
		if (serviceDefinition == null) {
			serviceDefinition = new RESTServiceDefinition(serviceType);
			definitionCache.put(serviceType, serviceDefinition);
		}
		return serviceDefinition;
	}

	private boolean checkParams(REST methodAnnotation, Parameters parameters) {
		for (String name : methodAnnotation.params()) {
			if (parameters.get(name) == null) {
				return false;
			}
		}
		return true;
	}
}
