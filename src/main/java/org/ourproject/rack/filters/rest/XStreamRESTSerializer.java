package org.ourproject.rack.filters.rest;

import java.util.HashMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class XStreamRESTSerializer implements RESTSerializer {
	private final HashMap<String, XStream> serializers;

	public XStreamRESTSerializer() {
		serializers = new HashMap<String, XStream>(2);
		serializers.put(RESTMethod.FORMAT_JSON, new XStream(new JettisonMappedXmlDriver()));
		serializers.put(RESTMethod.FORMAT_XML, new XStream());
		
	}
	
	public String serialize(Object target, String format) {
		return serializers.get(format).toXML(target);
	}

}
