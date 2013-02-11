package com.onetwopoll.gwt.framework;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;

public class JSONModel extends JavaScriptObject{

	protected JSONModel() {}

	public final static native JavaScriptObject evalJson(String json) /*-{
	try{
		// TODO throw error on invalid json
		if (/^[\],:{}\s]*$/.test(json.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
			return eval('(' + json + ')');
		}
	}
	catch(e)
	{
		// TODO log this somewhere
	}
		return {};
	}-*/;

	public final static native JsArray<? extends JavaScriptObject> evalJsonAsArray(String json) /*-{
	try {
		// TODO throw error on invalid json
		if (/^[\],:{}\s]*$/.test(json.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
		// the parens are required because '{' and '}' are ambiguous in JS and either enclose an object data or a code block
			return eval('(' + json + ')');
		}
	}
	catch(e) 
	{
		// TODO log this somewhere
	}
	return [];
  }-*/;
	
	
	public final String toJSONString() {
		JSONObject o = new JSONObject(this);
		return o.toString();
	}
	
	
	
}
