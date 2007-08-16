package org.ourproject.kune.platf.server.access;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.access.RightsServiceDefault;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class AccessRightsManagerTest {
    private RightsServiceDefault accessRightsManager;
    private Group group1;
    private Group group2;
    private Group userGroup;

    @Before
    public void init() {
	accessRightsManager = new RightsServiceDefault();
	userGroup = TestDomainHelper.createGroup(3);
	group1 = TestDomainHelper.createGroup(1);
	group2 = TestDomainHelper.createGroup(2);
    }

    @Test
    public void userCanBeLoggedOut() {
	AccessLists accessLists = TestDomainHelper.createAccessLists(userGroup, group1, group2);
	AccessRights response = accessRightsManager.get(null, accessLists);
	assertTrue(response.isVisible());
	assertFalse(response.isEditable());
	assertFalse(response.isAdministrable());
    }

    @Test
    public void checkUserAccessRightsTrue() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, userGroup);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(userGroup, userGroup, userGroup, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(userGroup, group1, group2);

	AccessRights response = accessRightsManager.get(userGroup, accessLists);
	assertTrue(response.isAdministrable());
	assertTrue(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, userGroup);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, userGroup, userGroup, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(userGroup, accessLists);
	assertFalse(response.isAdministrable());
	assertTrue(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsAndEditFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, userGroup);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, userGroup, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(userGroup, accessLists);
	assertFalse(response.isAdministrable());
	assertFalse(response.isEditable());
	assertTrue(response.isVisible());
    }

    @Test
    public void checkUserAccessRightsAdminsAndEditAndViewFalse() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, userGroup);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group1, group1, group1, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, group2);

	AccessRights response = accessRightsManager.get(userGroup, accessLists);
	assertFalse(response.isAdministrable());
	assertFalse(response.isEditable());
	assertFalse(response.isVisible());
    }
}
