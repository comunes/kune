package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRights;

public class AccessRightsManagerTest {
    private AccessRightsManagerDefault accessRightsManager;
    private User user;
    private Group group1;
    private Group group2;
    private Group group3;

    @Before
    public void init() {
	accessRightsManager = new AccessRightsManagerDefault();
	user = TestDomainHelper.createUser(1);
	group1 = TestDomainHelper.createGroup(1);
	group2 = TestDomainHelper.createGroup(2);
	group3 = user.getUserGroup();
    }

    @Test
    public void checkUserAccessRightsTrue() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, group3);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group3, group3, group3, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group3, group1, group2);

	AccessRights response = accessRightsManager.get(user, accessLists);
	assertTrue(response.isAdministrable());
	assertTrue(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, group3);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group3, group3, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(user, accessLists);
	assertFalse(response.isAdministrable());
	assertTrue(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsAndEditFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, group3);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, group3, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(user, accessLists);
	assertFalse(response.isAdministrable());
	assertFalse(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsAndEditAndViewFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, group3);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, group1, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(user, accessLists);
	assertFalse(response.isAdministrable());
	assertFalse(response.isEditable());
	assertFalse(response.isVisible());
    }
}
