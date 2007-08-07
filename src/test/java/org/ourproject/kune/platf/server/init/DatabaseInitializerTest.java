package org.ourproject.kune.platf.server.init;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;

import com.google.inject.Inject;

public class DatabaseInitializerTest extends PersistenceTest {
    @Inject
    DatabaseInitializer initializer;

    @Before
    public void open() {
	openTransaction();
    }

    @Test
    public void allWorks() {
	initializer.start();
    }

    @After
    public void close() {
	closeTransaction();
    }
}
