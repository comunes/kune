package org.ourproject.kune.platf.server.access;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class AccessRightsServiceTest {
    private AccessRightsServiceDefault accessRightsManager;
    private Group group1;
    private Group group2;
    private Group group3;

    @Before
    public void init() {
	accessRightsManager = new AccessRightsServiceDefault();
	group1 = TestDomainHelper.createGroup(1);
	group2 = TestDomainHelper.createGroup(2);
	group3 = TestDomainHelper.createGroup(3);
    }

    @Test
    public void checkUserAccesRightsViewNullEqualToTrue() {
	AccessLists accessLists = TestDomainHelper.createAccessLists(group1, group1, null);

	AccessRights response = accessRightsManager.get(group3, accessLists);
	assertTrue(response.isVisible());
    }

    @Test
    public void userCanBeLoggedOut() {
	AccessLists accessLists = TestDomainHelper.createAccessLists(group3, group1, group2);
	AccessRights response = accessRightsManager.get((Group) null, accessLists);
	assertTrue(response.isVisible());
	assertFalse(response.isEditable());
	assertFalse(response.isAdministrable());
    }

    @Test
    public void checkUserAccessRightsTrue() {
	SocialNetwork socialNetwork = TestDomainHelper.createSocialNetwork(group2, group2, group2, group3);
	SocialNetwork socialNetwork2 = TestDomainHelper.createSocialNetwork(group3, group3, group3, group1);
	group1.setSocialNetwork(socialNetwork);
	group2.setSocialNetwork(socialNetwork2);
	AccessLists accessLists = TestDomainHelper.createAccessLists(group3, group1, group2);

	AccessRights response = accessRightsManager.get(group3, accessLists);
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

	AccessRights response = accessRightsManager.get(group3, accessLists);
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

	AccessRights response = accessRightsManager.get(group3, accessLists);
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

	AccessRights response = accessRightsManager.get(group3, accessLists);
	assertFalse(response.isAdministrable());
	assertFalse(response.isEditable());
	assertFalse(response.isVisible());
    }

}
