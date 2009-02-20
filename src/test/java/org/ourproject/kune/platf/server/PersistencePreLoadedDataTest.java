package org.ourproject.kune.platf.server;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;

import com.google.inject.Inject;

public abstract class PersistencePreLoadedDataTest extends PersistenceTest {
    protected static final String USER_SHORT_NAME = "user-shortname";
    protected static final String USER_LONG_NAME = "the user long name";
    protected static final String USER_PASSWORD = "userPassword";
    protected static final String USER_EMAIL = "useremail@example.com";

    @Inject
    protected User userFinder;
    @Inject
    protected Group groupFinder;
    @Inject
    protected License licenseFinder;
    @Inject
    protected ContentManager contentManager;
    @Inject
    protected ContainerManager containerManager;
    @Inject
    protected GroupManager groupManager;
    @Inject
    protected UserManager userManager;
    @Inject
    protected LicenseManager licenseManager;
    @Inject
    protected I18nLanguageManager languageManager;
    @Inject
    protected I18nCountryManager countryManager;

    protected User user;
    protected License defLicense;
    protected I18nLanguage english;
    protected I18nCountry gb;
    protected Content content;
    protected Container container;

    public PersistencePreLoadedDataTest() {
        // test: use memory
        // test_db: use mysql
        super("test", "kune.properties");
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

    @Before
    public void preLoadData() throws Exception {
        openTransaction();
        assertEquals(0, userFinder.getAll().size());
        assertEquals(0, groupFinder.getAll().size());
        assertEquals(0, licenseFinder.getAll().size());
        english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western", ",");
        countryManager.persist(gb);
        user = userManager.createUser(USER_SHORT_NAME, USER_LONG_NAME, USER_EMAIL, USER_PASSWORD, "en", "GB",
                TimeZone.getDefault().getID());
        defLicense = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        licenseManager.persist(defLicense);
        groupManager.createUserGroup(user, true);
        content = new Content();
        content.setLanguage(english);
        contentManager.persist(content);
        container = new Container();
        container.setTypeId(DocumentServerTool.TYPE_FOLDER);
        containerManager.persist(container);
    }
}
