package org.ourproject.kune.platf.integration;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;

import com.google.inject.Inject;

public abstract class IntegrationTest {

    @Inject
    protected UserSession session;
    @Inject
    UserService userService;
    @Inject
    DatabaseProperties properties;

    public String getHash() {
	return session.getHash();
    }

    protected String doLogin() throws Exception {
	return userService.login(getSiteAdminShortName(), properties.getAdminPassword()).getUserHash();
    }

    protected String getDefLicense() {
	return properties.getDefaultLicense();
    }

    protected String getDefSiteGroupName() {
	return properties.getDefaultSiteShortName();
    }

    protected String getSiteAdminShortName() {
	return properties.getAdminShortName();
    }

}
