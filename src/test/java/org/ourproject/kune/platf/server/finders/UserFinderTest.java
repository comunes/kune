package org.ourproject.kune.platf.server.finders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class UserFinderTest extends PersistencePreLoadedDataTest {
    @Inject
    User finder;

    @Test
    public void findAll() {
        List<User> all = finder.getAll();
        assertEquals(3, all.size());
    }

    @Test
    public void findByEmail() {
        User user = finder.getByEmail("one@here.com");
        assertNotNull(user);
    }

    @Before
    public void initData() {
        persist(new User("shortname1", "the name1", "one@here.com", "password1", english, gb, TimeZone.getDefault()));
        persist(new User("shortname2", "the name2", "two@here.com", "password1", english, gb, TimeZone.getDefault()));
    }

}
