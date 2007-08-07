package org.ourproject.kune.platf.server.finders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class UserFinderTest extends PersistenceTest {
    @Inject
    User finder;

    @Before
    public void addData() {
	openTransaction();
	persist(new User("the name1", "shortName1", "one@here.com", "password1"));
	persist(new User("the name2", "shortName2", "two@here.com", "password1"));
    }

    @Test
    public void findAll() {
	List<User> all = finder.getAll();
	assertEquals(2, all.size());
    }

    @Test
    public void findByEmail() {
	User user = finder.getByEmail("one@here.com");
	assertNotNull(user);
    }

    @After
    public void close() {
	closeTransaction();
    }

}
