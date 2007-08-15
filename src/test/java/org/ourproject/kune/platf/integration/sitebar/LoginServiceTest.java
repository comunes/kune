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
    public void testLogin() throws SerializableException {
	assertNull(session.getUser());
	loginService.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	assertNotNull(session.getUser());
	// TODO: esto es para ti, vicente
	// service.login(properties.getDefaultSiteAdminEmail(),
	// properties.getAdminPassword());
    }
}
