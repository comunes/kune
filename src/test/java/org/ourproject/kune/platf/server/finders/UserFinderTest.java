package org.ourproject.kune.platf.server.finders;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;

import com.google.inject.Inject;

public class UserFinderTest extends PersistenceTest {
    @Inject
    User finder;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nCountryManager countryManager;

    @Before
    public void initData() {
        openTransaction();
        I18nLanguage english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        I18nCountry gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western",
                ",");
        countryManager.persist(gb);
        persist(new User("shortname1", "the name1", "one@here.com", "password1", english, gb));
        persist(new User("shortname2", "the name2", "two@here.com", "password1", english, gb));
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
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

}
