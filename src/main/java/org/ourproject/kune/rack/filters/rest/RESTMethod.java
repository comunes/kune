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

public class RESTMethod {
	public static final String FORMAT_JSON = "json";
	public static final String FORMAT_XML = "xml";
	
	private final Method method;
	private final Parameters parameters;
	private final String[] names;
	private Object response;
	private final String format;

	public RESTMethod(Method method, String[] names, Parameters parameters, String format) {
		this.method = method;
		this.names = names;
		this.parameters = parameters;
		this.format = format;
	}

	public boolean invoke(Object service) {
		Object[] values = convertParameters();
		try {
			response = method.invoke(service, values);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Object[] convertParameters() {
		int total = names.length;
		Object[] values = new Object[total];
		Class<?>[] types = method.getParameterTypes();
		
		for (int index = 0; index < total; index++) {
			values[index] = convert(types[index], parameters.get(names[index])); 
		}
		
		return values;
	}

	private Object convert(Class<?> type, String stringValue) {
		if (type.equals(String.class)) {
			return stringValue;
		} else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
			return Integer.parseInt(stringValue);
		} else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
			return Long.parseLong(stringValue);
		} else {
			throw new RuntimeException("unable to convert parameter in JSON method to type: " + type);
		}
	}
	
	

	public String getFormat() {
		return format;
	}

	public Object getResponse() {
		return response;
	}

}
