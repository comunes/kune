package org.ourproject.kune.platf.server.properties;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KunePropertiesDefault implements KuneProperties {
    private Properties properties;

    @Inject
    public KunePropertiesDefault(@PropertiesFileName
    final String fileName) {
	try {
	    properties = new Properties();
	    InputStream input = getInputStream(fileName);
	    properties.load(input);
	} catch (IOException e) {
	    String msg = MessageFormat.format("Couldn't open property file {0}", fileName);
	    throw new RuntimeException(msg, e);
	}

    }

    public String get(final String key) {
	return properties.getProperty(key);
    }

    private InputStream getInputStream(final String fileName) {
	InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	if (input == null) {
	    String msg = MessageFormat.format("Properties file: ''{0}'' not found", fileName);
	    throw new RuntimeException(msg);
	}
	return input;
    }
}
