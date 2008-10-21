package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class ContentManagerTest extends PersistenceTest {

    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    @Inject
    License licenseFinder;
    @Inject
    ContentManager contentManager;
    @Inject
    GroupManager groupManager;
    @Inject
    UserManager userManager;
    @Inject
    LicenseManager licenseManager;
    @Inject
    I18nLanguageManager languageManager;
    @Inject
    I18nCountryManager countryManager;
    private User user;
    private License defLicense;

    public ContentManagerTest() {
        // Testing with mysql because utf-8 normally fails with mysql and not in
        // memory
        super("test_db", "kune.properties");
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

    @Before
    public void insertData() throws Exception {
        openTransaction();
        assertEquals(0, userFinder.getAll().size());
        assertEquals(0, groupFinder.getAll().size());
        assertEquals(0, licenseFinder.getAll().size());
        final I18nLanguage english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        final I18nCountry gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom",
                "western", ",");
        countryManager.persist(gb);
        user = userManager.createUser("username", "the user name", "email@example.com", "userPassword", "en", "GB",
                                      TimeZone.getDefault().getID());
        defLicense = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        licenseManager.persist(defLicense);
        groupManager.createUserGroup(user, true);
    }

    @Test
    public void testBasicMimePersist() {
        final String mimetype = "application/pdf";
        createContentWithMime(mimetype);
    }

    @Test
    public void testBasicMimePersistWithoutSubtype() {
        final String mimetype = "application";
        createContentWithMime(mimetype);
    }

    /**
     * This normally fails with mysql (not configured for utf-8), see the
     * INSTALL mysql section
     */
    @Test
    public void testUTF8Persist() {
        final Container container = Mockito.mock(Container.class);
        final Content cnt = contentManager.createContent("汉语/漢語", "汉语/漢語", user, container);
        final Content newCnt = contentManager.find(cnt.getId());
        assertEquals("汉语/漢語", newCnt.getTitle());
    }

    private void createContentWithMime(final String mimetype) {
        final Container container = Mockito.mock(Container.class);
        final Content cnt = contentManager.createContent("title", "body", user, container);
        cnt.setMimeType(new BasicMimeType(mimetype));
        persist(cnt);
        final Content newCnt = contentManager.find(cnt.getId());
        assertEquals(mimetype, newCnt.getMimeType().toString());
    }
}
