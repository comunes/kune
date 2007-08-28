package org.ourproject.kune.platf.integration.sitebar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class LoginServiceTest {
    @Inject
    UserSession session;
    @Inject
    DatabaseProperties properties;
    @Inject
    SiteBarService loginService;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

    @Test
    public void testDefSiteNameLogin() throws SerializableException {
	assertNull(session.getUser());
	loginService.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	assertNotNull(session.getUser());
    }

    @Test
    public void testDefSiteEmailLogin() throws SerializableException {
	assertNull(session.getUser());
	loginService.login(properties.getDefaultSiteAdminEmail(), properties.getDefaultSiteAdminPassword());
	assertNotNull(session.getUser());
    }

    @Test
    public void testSiteNameLogin() throws SerializableException {
	assertNull(session.getUser());
	loginService.login(properties.getAdminShortName(), properties.getAdminPassword());
	assertNotNull(session.getUser());
    }

    @Test
    public void testSiteEmailLogin() throws SerializableException {
	assertNull(session.getUser());
	loginService.login(properties.getAdminEmail(), properties.getAdminPassword());
	assertNotNull(session.getUser());

    }

}
