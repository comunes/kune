package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.services.ContentServerService;
import org.ourproject.kune.platf.server.services.SiteBarServerService;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class AddContentTest {
    @Inject
    ContentServerService contentService;
    @Inject
    SiteBarServerService loginService;
    @Inject
    DatabaseProperties properties;
    @Inject
    UserSession session;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

    @Test
    public void testAddContent() throws SerializableException {
	loginService.login(properties.getDefaultSiteShortName(), properties.getDefaultSiteAdminPassword());
	ContentDTO content = contentService.getContent(session.getHash(), new StateToken());
	assertEquals(1, content.getFolder().getContents().size());
	ContentDTO added = contentService.addContent(session.getHash(), content.getFolder().getId(),
		"New Content Title");
	assertNotNull(added);
	List contents = added.getFolder().getContents();
	assertEquals(2, contents.size());
    }
}
