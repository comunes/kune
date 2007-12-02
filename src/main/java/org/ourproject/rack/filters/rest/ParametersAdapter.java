/**
 * 
 */
package org.ourproject.rack.filters.rest;

import javax.servlet.ServletRequest;

public class ParametersAdapter implements Parameters {
	private final ServletRequest request;

	public ParametersAdapter(ServletRequest request) {
		this.request = request;
	}

	public String get(String name) {
		return request.getParameter(name);
	}
	
}