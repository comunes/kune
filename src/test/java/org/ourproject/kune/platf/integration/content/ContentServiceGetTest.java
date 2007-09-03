package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.SerializableException;

public class ContentServiceGetTest extends ContentServiceIntegrationTest {

    @Before
    public void create() {
	new IntegrationTestHelper(this);
    }

    @Test(expected = GroupNotFoundException.class)
    public void unknownContent() throws ContentNotFoundException, AccessViolationException, GroupNotFoundException {
	StateDTO content = contentService.getContent(null, new StateToken("kune.docs"));
	assertNotNull(content);
	assertNotNull(content.getGroup());
	assertNotNull(content.getFolder());
	assertNotNull(content.getFolder().getId());
	assertNotNull(content.getToolName());
	assertNotNull(content.getDocumentId());
    }

    @Test
    public void contentWithLoggedUserIsEditable() throws SerializableException {
	doLogin();
	StateDTO response = contentService.getContent(null, new StateToken());
	assertNotNull(response.getContentRights());
	assertTrue(response.getContentRights().isEditable);
	// assertTrue(response.getAccessLists().getAdmin().size() == 1);
    }

    @Test
    public void notLoggedUserShouldNotEditDefaultDoc() throws ContentNotFoundException, AccessViolationException,
	    GroupNotFoundException {
	StateDTO content = contentService.getContent(null, new StateToken());
	assertFalse(content.getContentRights().isAdministrable);
	assertFalse(content.getContentRights().isEditable);
	assertTrue(content.getContentRights().isVisible);
	assertFalse(content.getFolderRights().isAdministrable);
	assertFalse(content.getFolderRights().isEditable);
	assertTrue(content.getFolderRights().isVisible);
    }

    @Test
    public void defaultCountentShouldExist() throws ContentNotFoundException, AccessViolationException,
	    GroupNotFoundException {
	StateDTO content = contentService.getContent(null, new StateToken());
	assertNotNull(content);
	assertNotNull(content.getGroup());
	assertNotNull(content.getFolder());
	assertNotNull(content.getFolder().getId());
	assertNotNull(content.getToolName());
	assertNotNull(content.getDocumentId());
    }

}
