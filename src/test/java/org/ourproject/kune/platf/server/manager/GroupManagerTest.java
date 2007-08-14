package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
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
    public void insertData() {
	openTransaction();
	assertEquals(0, userFinder.getAll().size());
	assertEquals(0, groupFinder.getAll().size());
	user = new User("the user name", "userName", "email", "userPassword");
	userManager.createUser(user);
    }

    @Test
    public void createdGroupShoudHaveValidSocialNetwork() {
	SocialNetwork socialNetwork = user.getUserGroup().getSocialNetwork();
	assertEquals(1, socialNetwork.getAdmins().size());
	assertTrue(socialNetwork.getAdmins().contains(user.getUserGroup()));
	assertEquals(0, socialNetwork.getCollaborators().size());
    }

    @Test(expected = SerializableException.class)
    public void createGroupWithExistingShortName() throws SerializableException {
	Group group = new Group("ysei", "Yellow Submarine Environmental Initiative");
	Group group2 = new Group("ysei", "Yellow Submarine Environmental Initiative");
	groupManager.create(user, group);
	groupManager.create(user, group2);
    }

    @After
    public void close() {
	closeTransaction();
    }
}
