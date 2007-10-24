package org.ourproject.kune.platf.integration;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public abstract class IntegrationTest {

    @Inject
    protected UserSession session;
    @Inject
    SiteBarService loginService;
    @Inject
    DatabaseProperties properties;

    protected void doLogin() throws SerializableException {
        loginService.login(getSiteAdminShortName(), properties.getAdminPassword());
    }

    protected String getSiteAdminShortName() {
        return properties.getAdminShortName();
    }

    protected String getDefSiteGroupName() {
        return properties.getDefaultSiteShortName();
    }

    public String getHash() {
        return session.getHash();
    }

}
