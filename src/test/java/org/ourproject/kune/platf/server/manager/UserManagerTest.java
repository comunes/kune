package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class UserManagerTest extends PersistenceTest {
    private static final String USER_SHORT_NAME = "userShortName";
    private static final String USER_LONG_NAME = "the user long name";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_EMAIL = "useremail@example.com";

    @Inject
    UserManager userManager;
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    private User user;

    @Before
    public void insertData() throws SerializableException {
	openTransaction();
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	user = new User(USER_SHORT_NAME, USER_LONG_NAME, USER_EMAIL, USER_PASSWORD);
	persist(user);
    }

    @Test
    public void loginWithNickCorrect() {
	User result = userManager.login(USER_SHORT_NAME, USER_PASSWORD);
	assertNotNull(result.getId());
    }

    @Test
    public void loginWithEmailCorrect() {
	User result = userManager.login(USER_EMAIL, USER_PASSWORD);
	assertNotNull(result.getId());
    }

    @Test
    public void loginIncorrect() {
	User result = userManager.login("test", "test");
	assertNull(result);
    }

    @After
    public void close() {
	closeTransaction();
    }
}
