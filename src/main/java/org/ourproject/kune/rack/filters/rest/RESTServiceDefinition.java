package org.ourproject.kune.rack.filters.rest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RESTServiceDefinition {
	private final Class<?> serviceType;
	private final Method[] methods;

	public RESTServiceDefinition(Class<?> serviceType) {
		this.serviceType = serviceType;
		List<Method> sorted = sortByParamsLength(getAnnotatedMethods());
		this.methods = sorted.toArray(new Method[sorted.size()]);
	}

	private List<Method> sortByParamsLength(List<Method> annotatedMethods) {
		Collections.sort(annotatedMethods, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				REST a1 = o1.getAnnotation(REST.class);
				REST a2 = o2.getAnnotation(REST.class);
				Integer length1 = a1.params().length;
				Integer length2 = a2.params().length;
				return -1 * length1.compareTo(length2);
			}
		});
		return annotatedMethods;
	}

	private List<Method> getAnnotatedMethods() {
		Method[] allMethods = serviceType.getMethods();
		List<Method> annotatedMethods = new ArrayList<Method>();
		for (Method m : allMethods) {
			if (m.getAnnotation(REST.class) != null) {
				annotatedMethods.add(m);
			}
		}
		return annotatedMethods;
	}

	public Method[] getMethods() {
		return methods;
	}

}
