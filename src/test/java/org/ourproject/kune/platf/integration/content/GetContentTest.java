package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class GetContentTest {
    @Inject
    UserSession session;
    private IntegrationTestHelper helper;
    @Inject
    DatabaseProperties properties;
    @Inject
    ContentService service;

    @Before
    public void create() {
	this.helper = new IntegrationTestHelper(this);
    }

    @Test
    public void contentWithLoggedUserIsEditable() throws SerializableException {
	SiteBarService loginService = helper.getService(SiteBarService.class);
	loginService.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	ContentDTO response = service.getContent(null, new StateToken());
	assertNotNull(response.getContentRights());
	assertTrue(response.getContentRights().isEditable);
	// assertTrue(response.getAccessLists().getAdmin().size() == 1);
    }

    @Test
    public void notLoggedUserShouldNotEditDefaultDoc() throws ContentNotFoundException {
	ContentDTO content = service.getContent(null, new StateToken());
	assertFalse(content.getContentRights().isAdministrable);
	assertFalse(content.getContentRights().isEditable);
	assertTrue(content.getContentRights().isVisible);
	assertFalse(content.getFolderRights().isAdministrable);
	assertFalse(content.getFolderRights().isEditable);
	assertTrue(content.getFolderRights().isVisible);
    }

    @Test
    public void defaultCountentShouldExist() throws ContentNotFoundException {
	ContentDTO content = service.getContent(null, new StateToken());
	assertNotNull(content);
	assertNotNull(content.getGroup());
	assertNotNull(content.getFolder());
	assertNotNull(content.getFolder().getId());
	assertNotNull(content.getToolName());
	assertNotNull(content.getDocumentId());
    }

}
