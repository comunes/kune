package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.lucene.queryParser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager.SearchResult;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class GroupManagerTest extends PersistenceTest {
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    @Inject
    License licenseFinder;
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

    @Before
    public void insertData() throws SerializableException {
        openTransaction();
        assertEquals(0, userFinder.getAll().size());
        assertEquals(0, groupFinder.getAll().size());
        assertEquals(0, licenseFinder.getAll().size());
        I18nLanguage english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        I18nCountry gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western",
                ",");
        countryManager.persist(gb);
        user = userManager.createUser("username", "the user name", "email@example.com", "userPassword", "en", "GB");
        defLicense = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        licenseManager.persist(defLicense);
        groupManager.createUserGroup(user);
    }

    @Test
    public void createdGroupShoudHaveValidSocialNetwork() throws SerializableException {
        final Group group = new Group("short", "longName", defLicense, GroupType.PROJECT);
        groupManager.createGroup(group, user);
        final SocialNetwork socialNetwork = group.getSocialNetwork();
        final AccessLists lists = socialNetwork.getAccessLists();
        assertTrue(lists.getAdmins().includes(user.getUserGroup()));
        assertTrue(lists.getEditors().isEmpty());
        assertTrue(lists.getViewers().isEmpty());
        closeTransaction();
    }

    @Test
    public void createGroup() throws SerializableException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);
        final Group otherGroup = groupManager.findByShortName("ysei");

        assertEquals(group.getLongName(), otherGroup.getLongName());
        assertEquals(group.getShortName(), otherGroup.getShortName());
        closeTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createGroupWithExistingShortName() throws SerializableException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);

        final Group group2 = new Group("ysei", "Yellow Submarine Environmental Initiative 2", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group2, user);

        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createGroupWithExistingLongName() throws SerializableException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);

        final Group group2 = new Group("ysei2", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        group2.setDefaultLicense(defLicense);
        groupManager.createGroup(group2, user);

        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserWithExistingShortName() throws SerializableException {
        User user2 = userManager.createUser("username", "the user name 2", "email2@example.com", "userPassword", "en",
                "GB");
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserWithExistingLongName() throws SerializableException {
        User user2 = userManager.createUser("username2", "the user name", "email2@example.com", "userPassword", "en",
                "GB");
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test(expected = EmailAddressInUseException.class)
    public void createUserWithExistingEmail() throws SerializableException {
        User user2 = userManager.createUser("username2", "the user name 2", "email@example.com", "userPassword", "en",
                "GB");
        groupManager.createUserGroup(user2);
        rollbackTransaction();
    }

    @Test
    public void createGroupAndSearch() throws SerializableException, ParseException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);
        groupManager.reIndex();
        SearchResult result = groupManager.search("ysei");
        assertEquals(1, result.getSize());
        assertEquals("ysei", ((Group) result.getList().get(0)).getShortName());
        rollbackTransaction();
    }

    @Test
    public void groupSearchPagination() throws SerializableException, ParseException {
        for (int i = 1; i < 10; i++) {
            createTestGroup(i);
        }
        groupManager.reIndex();
        SearchResult result = groupManager.search("Yellow", 0, 5);
        assertEquals(9, result.getSize());
        assertEquals(5, result.getList().size());
        SearchResult result2 = groupManager.search("Yellow", 5, 5);
        assertEquals(9, result2.getSize());
        assertEquals(4, result2.getList().size());
        rollbackTransaction();
    }

    private void createTestGroup(final int number) throws SerializableException {
        Group g = new Group("ysei" + number, "Yellow Submarine Environmental Initiative " + number, defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(g, user);
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }

    }
}
