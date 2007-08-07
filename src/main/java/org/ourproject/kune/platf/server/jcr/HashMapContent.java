package org.ourproject.kune.platf.server.jcr;

import java.util.HashMap;

public class HashMapContent implements ContentSource, ContentDestination {

    private final HashMap<String, String> data;

    public HashMapContent(final HashMap<String, String> data) {
	this.data = data;
    }

    public HashMapContent(final String... keys) {
	this(new HashMap<String, String>());
	for (String key : keys) {
	    data.put(key, null);
	}
    }

    public String get(final String key) {
	return data.get(key);
    }

    public Iterable<String> keys() {
	return data.keySet();
    }

    public void put(final String key, final String value) {
	data.put(key, value);
    }

}
