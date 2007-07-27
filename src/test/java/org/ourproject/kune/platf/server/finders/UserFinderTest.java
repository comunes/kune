package org.ourproject.kune.platf.server.finders;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.domain.User;
import static junit.framework.Assert.*;

import com.google.inject.Inject;

public class UserFinderTest extends PersistenceTest {
    @Inject User finder;

    @Before
    public void addData() {
        persist(new User("one@here.com", "password1"));
        persist(new User("two@here.com", "password1"));
    }
    
    @Test public void findAll() {
         List<User> all = finder.getAll();
         assertEquals(2, all.size());
    }
    
    @Test public void findByEmail () {
        User user = finder.getByEmail("one@here.com");
        assertNotNull(user);
    }

}
