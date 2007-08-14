package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.Group;

import com.google.inject.Inject;

public class GroupFinderTest extends PersistenceTest {
    @Inject
    Group groupFinder;

    @Before
    public void insertData() {
	openTransaction();
	persist(new Group("shortName1", "name1"));
	persist(new Group("shortName2", "name2"));
    }

    @Test
    public void testGetAll() {
	assertEquals(2, groupFinder.getAll().size());
    }

    @After
    public void close() {
	closeTransaction();
    }
}
