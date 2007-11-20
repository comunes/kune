package org.ourproject.kune.platf.integration;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.UserService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public abstract class IntegrationTest {

    @Inject
    protected UserSession session;
    @Inject
    UserService userService;
    @Inject
    DatabaseProperties properties;

    protected void doLogin() throws SerializableException {
        userService.login(getSiteAdminShortName(), properties.getAdminPassword());
    }

    protected String getSiteAdminShortName() {
        return properties.getAdminShortName();
    }

    protected String getDefSiteGroupName() {
        return properties.getDefaultSiteShortName();
    }

    protected String getDefLicense() {
        return properties.getDefaultLicense();
    }

    public String getHash() {
        return session.getHash();
    }

}
