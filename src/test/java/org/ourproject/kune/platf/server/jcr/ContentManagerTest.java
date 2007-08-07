package org.ourproject.kune.platf.server.jcr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.jcr.ItemNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class ContentManagerTest {
    @Inject
    ContentManager manager;
    @Inject
    ContentService service;
    private HashMapContent values;

    @Before
    public void init() {
	Guice.createInjector(new JCRModule(), new Module() {
	    public void configure(final Binder binder) {
		binder.bind(JcrConfiguration.class).to(JcrTestConfiguration.class);
	    }
	}).injectMembers(this);
	service.start();
	values = new HashMapContent();
	values.put("name", "theName");
	values.put("content", "theContent");
    }

    @After
    public void close() {
	service.stop();
    }

    @Test
    public void testPersistOnParent() {
	String uuid = manager.persist("/kune/group/documents", "theName", values);
	assertNotNull(uuid);
    }

    @Test
    public void testPersist() throws ItemNotFoundException {
	String uuid = manager.persist("/pages", "theData", values);
	assertNotNull(uuid);
	assertTrue(uuid.length() > 1);

	HashMapContent result = (HashMapContent) manager.get(uuid, new HashMapContent("name", "content"));
	assertEquals(values.get("name"), result.get("name"));
	assertEquals(values.get("content"), result.get("content"));
    }
}
