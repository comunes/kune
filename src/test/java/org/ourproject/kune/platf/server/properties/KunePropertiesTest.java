package org.ourproject.kune.platf.server.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KunePropertiesTest {

    @Test
    public void testLoading() {
	KunePropertiesDefault properties = new KunePropertiesDefault("test.file.properties");
	assertEquals("value", properties.get("key"));
    }

}
