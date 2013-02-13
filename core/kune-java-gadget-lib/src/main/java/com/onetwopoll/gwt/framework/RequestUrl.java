package com.onetwopoll.gwt.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;

public class RequestUrl {

	protected String url;
	protected String queryString;
	
	public RequestUrl() {
		url = GWT.getHostPageBaseURL() + "rest";
		queryString = null;
	}

	public RequestUrl(String baseUrl) {
		url = baseUrl;
		queryString = null;
	}

	public RequestUrl add(String pathPiece) {
	
		if(pathPiece == null) return this;
		
		String[] pieces = pathPiece.split("/");
		for(int i=0; i<pieces.length; i++) {
			if(pieces[i].isEmpty()) continue;
			url += "/"+URL.encodeComponent(pieces[i]);
		}
		return this;		
	}

	public RequestUrl add(int pathPiece) {
		return add(Integer.toString(pathPiece));
	}
	
	public RequestUrl addParam(String key, String value) {
		if(key == null) return this;
		if(queryString == null || queryString.isEmpty()) queryString = "?";
		else queryString += "&";
		queryString += URL.encodeComponent(key) + "=" + URL.encodeComponent(value);
		return this;
	}

	
	public String build() {
		if(queryString == null || queryString.isEmpty()) return url;
		return url + queryString;
	}
	
	
	@Override
	public String toString() {
		return build();
	}
	

	
}
