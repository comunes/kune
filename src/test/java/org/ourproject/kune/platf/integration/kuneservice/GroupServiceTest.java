package org.ourproject.kune.platf.integration.kuneservice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class GroupServiceTest extends IntegrationTest {

    @Inject
    GroupService service;

    @Inject
    UserSession session;

    @Inject
    Group groupFinder;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
    }

    @Test(expected = SerializableException.class)
    public void createGroupNotLogged() throws SerializableException {
        GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
                GroupDTO.PROJECT);
        service.createNewGroup(session.getHash(), group);
    }

    @Test
    public void createGroupUserLogged() throws SerializableException {
        doLogin();

        GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
                GroupDTO.PROJECT);

        LicenseDTO license = new LicenseDTO();
        license.setShortName("by-sa");
        group.setDefaultLicense(license);
        service.createNewGroup(session.getHash(), group);

        Group groupCreated = groupFinder.findByShortName("ysei");
        assertEquals(groupCreated.getShortName(), group.getShortName());
        assertEquals(groupCreated.getLongName(), group.getLongName());
        assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
        assertEquals(groupCreated.getAdmissionType(), AdmissionType.Moderated);
        assertEquals(groupCreated.getType(), GroupType.PROJECT);
    }

    @Test
    public void createCommunity() throws SerializableException {
        doLogin();

        GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
                GroupDTO.COMMUNITY);

        LicenseDTO license = new LicenseDTO();
        license.setShortName("by-sa");
        group.setDefaultLicense(license);
        service.createNewGroup(session.getHash(), group);

        Group groupCreated = groupFinder.findByShortName("ysei");
        assertEquals(groupCreated.getShortName(), group.getShortName());
        assertEquals(groupCreated.getLongName(), group.getLongName());
        assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
        assertEquals(groupCreated.getAdmissionType(), AdmissionType.Open);
        assertEquals(groupCreated.getType(), GroupType.COMMUNITY);
    }

    @Test
    public void createOrganization() throws SerializableException {
        doLogin();

        GroupDTO group = new GroupDTO("ysei", "Yellow Submarine Environmental Initiative", "Public Desc",
                GroupDTO.ORGANIZATION);

        LicenseDTO license = new LicenseDTO();
        license.setShortName("by-sa");
        group.setDefaultLicense(license);
        service.createNewGroup(session.getHash(), group);

        Group groupCreated = groupFinder.findByShortName("ysei");
        assertEquals(groupCreated.getShortName(), group.getShortName());
        assertEquals(groupCreated.getLongName(), group.getLongName());
        assertEquals(groupCreated.getPublicDesc(), group.getPublicDesc());
        assertEquals(groupCreated.getAdmissionType(), AdmissionType.Moderated);
        assertEquals(groupCreated.getType(), GroupType.ORGANIZATION);
    }

}
