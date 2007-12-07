package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager.SearchResult;
import org.ourproject.kune.platf.server.users.UserManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SearchManagerTest extends PersistenceTest {
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    @Inject
    License licenseFinder;
    @Inject
    SearchManager searchManager;
    @Inject
    UserManager userManager;
    @Inject
    GroupManager groupManager;
    @Inject
    LicenseManager licenseManager;

    private User user;
    private License defLicense;

    @Before
    public void insertData() throws SerializableException {
        openTransaction();
        assertEquals(0, userFinder.getAll().size());
        assertEquals(0, groupFinder.getAll().size());
        assertEquals(0, licenseFinder.getAll().size());
        user = userManager.createUser("username", "the user name", "email@example.com", "userPassword", "en", "GB");
        defLicense = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        licenseManager.persist(defLicense);
        groupManager.createUserGroup(user);
    }

    @Test
    public void globalSearch() throws SerializableException, ParseException {
        final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", defLicense,
                GroupType.PROJECT);
        groupManager.createGroup(group, user);
        groupManager.reIndex();
        List<Group> result = searchManager.search("ysei");
        assertEquals(1, result.size());
        assertEquals("ysei", result.get(0).getShortName());
        rollbackTransaction();
    }

    @Test
    public void globalSearchPagination() throws SerializableException, ParseException {
        for (int i = 1; i < 10; i++) {
            createTestGroup(i);
        }
        groupManager.reIndex();
        SearchResult result = groupManager.search("Yellow", 0, 5);
        assertEquals(5, result.getSize());
        SearchResult result2 = groupManager.search("Yellow", 5, 5);
        assertEquals(4, result2.getSize());
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
