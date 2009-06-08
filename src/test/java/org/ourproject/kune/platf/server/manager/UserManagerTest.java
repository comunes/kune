package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.TimeZone;

import javax.persistence.EntityExistsException;

import org.apache.lucene.queryParser.ParseException;
import org.hibernate.validator.InvalidStateException;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.inject.Inject;

public class UserManagerTest extends PersistencePreLoadedDataTest {
    @Inject
    Group groupFinder;

    @Test
    public void emailCorrect() {
        user = new User("test1", "test1 name", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test(expected = InvalidStateException.class)
    public void emailEmpty() {
        user = new User("test1", "test1 name", "", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test(expected = InvalidStateException.class)
    public void emailIncorrect() {
        user = new User("test1", "test1 name", "falseEmail@", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test
    public void loginIncorrect() {
        final User result = userManager.login("test", "test");
        assertNull(result);
    }

    @Test
    public void loginWithEmailCorrect() {
        final User result = userManager.login(USER_EMAIL, USER_PASSWORD);
        assertNotNull(result.getId());
    }

    @Test
    public void loginWithNickCorrect() {
        final User result = userManager.login(USER_SHORT_NAME, USER_PASSWORD);
        assertNotNull(result.getId());
    }

    @Test(expected = InvalidStateException.class)
    public void passwdLengthIncorrect() {
        user = new User("test1", "test1 name", "test@example.com", "pass", english, gb, getTimeZone());
        persist(user);
    }

    @Test(expected = EntityExistsException.class)
    public void testUserExist() throws I18nNotFoundException {
        final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        persist(user1);
        final User user2 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        persist(user2);
    }

    @Test(expected = InvalidStateException.class)
    public void userNameLengthIncorrect() {
        user = new User("test1", "te", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test
    public void userSearch() throws Exception, ParseException {
        userManager.reIndex();
        final SearchResult<User> result = userManager.search(USER_SHORT_NAME);
        assertEquals(1, result.getSize());
        assertEquals(USER_SHORT_NAME, result.getList().get(0).getShortName());
        rollbackTransaction();
    }

    @Test(expected = InvalidStateException.class)
    public void userShortNameIncorrect() {
        user = new User("test1A", "test1 name", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    private TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

}
