package org.ourproject.rack.filters.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JsonMethod {
	private final Method method;
	private final Parameters parameters;
	private final String[] names;
	private Object response;

	public JsonMethod(Method method, String[] names, Parameters parameters) {
		this.method = method;
		this.names = names;
		this.parameters = parameters;
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
		} else {
			throw new RuntimeException("unable to convert parameter in JSON method to type: " + type);
		}
	}

	public Object getResponse() {
		return response;
	}

}
