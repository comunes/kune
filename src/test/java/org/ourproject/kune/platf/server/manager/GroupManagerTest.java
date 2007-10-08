package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.users.UserManager;

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

    private User user;
    private License licenseDef;

    @Before
    public void insertData() throws SerializableException {
	openTransaction();
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	assertEquals(0, licenseFinder.getAll().size());
	user = userManager.createUser("userName", "the user name", "email", "userPassword");
	licenseDef = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
	licenseManager.persist(licenseDef);
	groupManager.createUserGroup(user);
    }

    @Test
    public void createdGroupShoudHaveValidSocialNetwork() throws SerializableException {
	final Group group = new Group("short", "longName", licenseDef, GroupType.PROJECT);
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
	final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", licenseDef,
		GroupType.PROJECT);
	groupManager.createGroup(group, user);
	final Group otherGroup = groupManager.findByShortName("ysei");

	assertEquals(group.getLongName(), otherGroup.getLongName());
	assertEquals(group.getShortName(), otherGroup.getShortName());
	closeTransaction();
    }

    @Test(expected = SerializableException.class)
    public void createGroupWithExistingShortName() throws SerializableException {
	final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", licenseDef,
		GroupType.PROJECT);
	groupManager.createGroup(group, user);

	final Group group2 = new Group("ysei", "Yellow Submarine Environmental Initiative 2", licenseDef,
		GroupType.PROJECT);
	groupManager.createGroup(group2, user);

	rollbackTransaction();
    }

    @Test(expected = SerializableException.class)
    public void createGroupWithExistingLongName() throws SerializableException {
	final Group group = new Group("ysei", "Yellow Submarine Environmental Initiative", licenseDef,
		GroupType.PROJECT);
	groupManager.createGroup(group, user);

	final Group group2 = new Group("ysei2", "Yellow Submarine Environmental Initiative", licenseDef,
		GroupType.PROJECT);
	group2.setDefaultLicense(licenseDef);
	groupManager.createGroup(group2, user);

	rollbackTransaction();
    }

    @After
    public void close() {
	if (getTransaction().isActive()) {
	    getTransaction().rollback();
	}

    }
}
