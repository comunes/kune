package org.ourproject.kune.platf.server.init;

import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;

import com.google.inject.Inject;

public class DatabaseInitializerTest extends PersistenceTest {
    @Inject
    DatabaseInitializer initializer;

    @Test
    public void allWorks() {
	initializer.start();
    }
}
