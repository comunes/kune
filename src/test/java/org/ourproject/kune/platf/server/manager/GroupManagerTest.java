package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class GroupManagerTest extends PersistenceTest {

    @Inject
    UserManager userManager;
    @Inject
    User userFinder;
    @Inject
    Group groupFinder;
    @Inject
    GroupManager groupManager;

    private User user;

    @Before
    public void insertData() throws SerializableException {
	openTransaction();
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	user = new User("the user name", "userName", "email", "userPassword");
	userManager.createUser(user);
    }

    @Test
    public void createdGroupShoudHaveValidSocialNetwork() throws SerializableException {
	Group group = new Group("short", "longName");
	groupManager.createGroup(group, user);
	SocialNetwork socialNetwork = group.getSocialNetwork();
	AccessLists lists = socialNetwork.getAccessList();
	assertTrue(lists.getAdmins().contains(user.getUserGroup()));
	assertTrue(lists.getEditors().isEmpty());
	assertTrue(lists.getViewers().isEmpty());
	closeTransaction();
    }

    @Test
    public void createGroup() throws SerializableException {
	Group group = groupManager.createGroup("ysei", "Yellow Submarine Environmental Initiative", user);
	Group otherGroup = groupManager.findByShortName("ysei");

	assertEquals(group.getLongName(), otherGroup.getLongName());
	assertEquals(group.getShortName(), otherGroup.getShortName());
	closeTransaction();
    }

    @Test(expected = SerializableException.class)
    public void createGroupWithExistingShortName() throws SerializableException {
	groupManager.createGroup("ysei", "Yellow Submarine Environmental Initiative", user);
	groupManager.createGroup("ysei", "Yellow Submarine Environmental Initiative 2", user);
    }

    @Test(expected = SerializableException.class)
    public void createGroupWithExistingLongName() throws SerializableException {
	groupManager.createGroup("ysei", "Yellow Submarine Environmental Initiative", user);
	groupManager.createGroup("ysei2", "Yellow Submarine Environmental Initiative", user);
	rollbackTransaction();
    }

    @After
    public void close() {
	if (getTransaction().isActive()) {
	    getTransaction().rollback();
	}

    }
}
