package org.ourproject.kune.platf.integration.kuneservice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;

import com.google.inject.Inject;

public class GroupServiceTest extends IntegrationTest {

    @Inject
    GroupService service;

    @Inject
    UserSession session;

    @Inject
    Group groupFinder;

    @Test
    public void createCommunity() throws Exception {
	doLogin();

	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.COMMUNITY);

	final LicenseDTO license = new LicenseDTO();
	license.setShortName("by-sa");
	group.setDefaultLicense(license);
	service.createNewGroup(session.getHash(), group);

	final Group groupCreated = groupFinder.findByShortName("ysei");
	assertEquals(groupCreated.getShortName(), group.getShortName());
	assertEquals(groupCreated.getLongName(), group.getLongName());
	assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
	assertEquals(groupCreated.getAdmissionType(), AdmissionType.Open);
	assertEquals(groupCreated.getType(), GroupType.COMMUNITY);
    }

    @Test(expected = Exception.class)
    public void createGroupNotLogged() throws Exception {
	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.PROJECT);
	service.createNewGroup(session.getHash(), group);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void createGroupNullUserHash() throws Exception {
	doLogin();
	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.PROJECT);
	service.createNewGroup(null, group);
    }

    @Test
    public void createGroupUserLogged() throws Exception {
	doLogin();

	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.PROJECT);

	final LicenseDTO license = new LicenseDTO();
	license.setShortName("by-sa");
	group.setDefaultLicense(license);
	service.createNewGroup(session.getHash(), group);

	final Group groupCreated = groupFinder.findByShortName("ysei");
	assertEquals(groupCreated.getShortName(), group.getShortName());
	assertEquals(groupCreated.getLongName(), group.getLongName());
	assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
	assertEquals(groupCreated.getAdmissionType(), AdmissionType.Moderated);
	assertEquals(groupCreated.getType(), GroupType.PROJECT);
    }

    @Test
    public void createOrganization() throws Exception {
	doLogin();

	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.ORGANIZATION);

	final LicenseDTO license = new LicenseDTO();
	license.setShortName("by-sa");
	group.setDefaultLicense(license);
	service.createNewGroup(session.getHash(), group);

	final Group groupCreated = groupFinder.findByShortName("ysei");
	assertEquals(groupCreated.getShortName(), group.getShortName());
	assertEquals(groupCreated.getLongName(), group.getLongName());
	assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
	assertEquals(groupCreated.getAdmissionType(), AdmissionType.Moderated);
	assertEquals(groupCreated.getType(), GroupType.ORGANIZATION);
    }

    @Test
    public void createOrphanedProject() throws Exception {
	doLogin();

	final GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
		GroupDTO.ORPHANED_PROJECT);

	final LicenseDTO license = new LicenseDTO();
	license.setShortName("by-sa");
	group.setDefaultLicense(license);
	service.createNewGroup(session.getHash(), group);

	final Group groupCreated = groupFinder.findByShortName("ysei");
	assertEquals(groupCreated.getShortName(), group.getShortName());
	assertEquals(groupCreated.getLongName(), group.getLongName());
	assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
	assertEquals(groupCreated.getAdmissionType(), AdmissionType.Open);
	assertEquals(groupCreated.getType(), GroupType.ORPHANED_PROJECT);
	assertEquals(0, groupCreated.getSocialNetwork().getAccessLists().getAdmins().getList().size());
	assertEquals(0, groupCreated.getSocialNetwork().getAccessLists().getEditors().getList().size());
    }

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

}
