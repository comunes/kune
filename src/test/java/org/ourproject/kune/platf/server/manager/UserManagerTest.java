package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.finders.PersistenceTest;

import com.google.inject.Inject;

public class UserManagerTest extends PersistenceTest {
    @Inject
    UserManager userManager;
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    private User user;

    @Before
    public void insertData() {
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	user = new User("the user name", "userName", "email", "userPassword");
	userManager.createUser(user);
    }

    @Test
    public void testUserRemoval() {
    }

    @Test
    public void testUserCreation() {
	assertNotNull(user.getId());
	assertNotNull(user.getUserGroup());
	assertNotNull(user.getUserGroup().getId());
	assertEquals(user.getShortName(), user.getUserGroup().getShortName());
	assertEquals(1, userFinder.getAll().size());
	assertEquals(1, groupFinder.getAll().size());
    }
}
