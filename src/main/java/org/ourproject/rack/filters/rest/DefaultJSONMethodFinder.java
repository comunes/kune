package org.ourproject.rack.filters.rest;

import java.lang.reflect.Method;

import org.ourproject.rack.filters.json.Parameters;

public class DefaultJSONMethodFinder implements JSONMethodFinder {

	public JsonMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType) {
		Method[] allMethods = serviceType.getMethods();
		for (Method method : allMethods) {
			JSONMethod methodAnnotation = method.getAnnotation(JSONMethod.class);
			if (methodAnnotation != null && methodAnnotation.name().equals(methodName)) {
				checkParams(methodAnnotation, parameters);
				return new JsonMethod(method, methodAnnotation.params(), parameters);
			}
		}
		return null;
	}

	private boolean checkParams(JSONMethod methodAnnotation, Parameters parameters) {
		for (String name : methodAnnotation.params()) {
			if (parameters.get(name) == null) {
				return false;
			}
		}
		return true;
	}
}
