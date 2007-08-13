package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRights;

public class AccessRightsManagerTest {
    private AccessRightsManagerDefault accessRightsManager;

    @Before
    public void createSession() {
	accessRightsManager = new AccessRightsManagerDefault();
    }

    @Test
    public void getUserAccessRights() {
	User user = TestDomainHelper.createUser(1);
	Group group1 = TestDomainHelper.createGroup(1);
	Group group2 = TestDomainHelper.createGroup(2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(user.getUserGroup(), group1, group2);

	AccessRights response = accessRightsManager.get(user, accessLists);
	assertTrue(response.isAdministrable());
	assertTrue(response.isAdministrable());
	assertTrue(response.isAdministrable());
    }
}
