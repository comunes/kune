package org.ourproject.kune.platf.integration.kuneservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.Group;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class KuneServiceTest extends IntegrationTest {

    @Inject
    KuneService service;

    @Inject
    UserSession session;

    @Inject
    Group groupFinder;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

    @Test
    public void testGetInitData() throws SerializableException {
	InitDataDTO initData = service.getInitData(null);
	assertNotNull(initData);
	assertValidLicenseDTOList(initData.getNotCCLicenses());
	assertValidLicenseDTOList(initData.getCCLicenses());
    }

    private void assertValidLicenseDTOList(final ArrayList licenseList) {
	assertTrue(licenseList.size() > 0);
	for (Object o : licenseList) {
	    assertNotNull(o);
	    assertEquals(LicenseDTO.class, o.getClass());
	}
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
    }

}
