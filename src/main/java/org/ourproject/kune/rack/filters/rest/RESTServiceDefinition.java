/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
