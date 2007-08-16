package org.ourproject.kune.platf.integration.content;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public abstract class ContentServiceIntegrationTest {
    @Inject
    UserSession session;
    @Inject
    protected ContentService contentService;
    @Inject
    SiteBarService loginService;
    @Inject
    DatabaseProperties properties;

    protected ContentDTO getDefaultContent() throws SerializableException, ContentNotFoundException {
	ContentDTO content = contentService.getContent(session.getHash(), new StateToken());
	return content;
    }

    protected void doLogin() throws SerializableException {
	loginService.login(getDefGroupName(), properties.getDefaultSiteAdminPassword());
    }

    protected String getDefGroupName() {
	return properties.getDefaultSiteShortName();
    }

    public String getHash() {
	return session.getHash();
    }

}
