package org.ourproject.kune.platf.integration.sitebar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SiteBarServiceTest {
    @Inject
    UserSession session;
    @Inject
    DatabaseProperties properties;
    @Inject
    SiteBarService siteBarService;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
    }

    @Test
    public void testSiteNameLogin() throws SerializableException {
        assertNull(session.getUser().getId());
        siteBarService.login(properties.getAdminShortName(), properties.getAdminPassword());
        assertNotNull(session.getUser().getId());
    }

    @Test
    public void testSiteEmailLogin() throws SerializableException {
        assertNull(session.getUser().getId());
        siteBarService.login(properties.getAdminEmail(), properties.getAdminPassword());
        assertNotNull(session.getUser().getId());
    }

    @Test(expected = GroupNameInUseException.class)
    public void createUserExistingNameFails() throws SerializableException {
        assertNull(session.getUser().getId());
        siteBarService.createUser(properties.getAdminShortName(), "test", "example@example.com", "123456",
                new LicenseDTO());
    }

    @Test(expected = EmailAddressInUseException.class)
    public void createUserExistingEmailFails() throws SerializableException {
        assertNull(session.getUser().getId());
        siteBarService.createUser("test", "test", properties.getAdminEmail(), "123456", new LicenseDTO());
    }
}
