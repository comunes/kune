package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.TimeZone;

import javax.persistence.EntityExistsException;

import org.apache.lucene.queryParser.ParseException;
import org.hibernate.validator.InvalidStateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.inject.Inject;

public class UserManagerTest extends PersistenceTest {
    private static final String USER_SHORT_NAME = "user-shortname";
    private static final String USER_LONG_NAME = "the user long name";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_EMAIL = "useremail@example.com";

    @Inject
    UserManager userManager;
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nCountryManager countryManager;

    private User user;
    private I18nCountry gb;
    private I18nLanguage english;

    @After
    public void close() {
	if (getTransaction().isActive()) {
	    getTransaction().rollback();
	}
    }

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

    @Before
    public void insertData() throws Exception {
	openTransaction();
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	english = new I18nLanguage(new Long(1819), "English", "English", "en");
	languageManager.persist(english);
	gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western", ",");
	countryManager.persist(gb);
	user = new User(USER_SHORT_NAME, USER_LONG_NAME, USER_EMAIL, USER_PASSWORD, english, gb, getTimeZone());
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
