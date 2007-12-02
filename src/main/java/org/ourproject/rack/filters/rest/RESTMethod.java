package org.ourproject.rack.filters.rest;

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
