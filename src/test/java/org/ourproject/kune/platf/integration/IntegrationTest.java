package org.ourproject.kune.platf.integration;

import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.rpc.UserService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

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

    protected String doLogin() throws DefaultException {
        return userService.login(getSiteAdminShortName(), properties.getAdminPassword()).getUserHash();
    }

    protected String doLoginWithDummyUser() throws DefaultException {
        return userService.login("dummy", properties.getAdminPassword()).getUserHash();
    }

    protected void doLogout() throws DefaultException {
        userService.logout(getHash());
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
