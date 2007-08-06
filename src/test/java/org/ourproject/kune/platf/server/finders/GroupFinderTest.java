package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.domain.Group;

import com.google.inject.Inject;

public class GroupFinderTest extends PersistenceTest {
    @Inject
    Group groupFinder;

    @Before
    public void insertData() {
	openTransaction();
	persist(new Group("name1", "shortName1"));
	persist(new Group("name2", "shortName2"));
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
