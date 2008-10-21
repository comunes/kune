package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class ContentServiceGetTest extends ContentServiceIntegrationTest {

    @Test
    public void contentWithLoggedUserIsEditable() throws Exception {
        final String userHash = doLogin();
        final StateDTO response = contentService.getContent(userHash, new StateToken());
        assertNotNull(response.getContentRights());
        assertTrue(response.getContentRights().isEditable());
        // assertTrue(response.getAccessLists().getAdmin().size() == 1);
    }

    @Before
    public void create() {
        new IntegrationTestHelper(this);
    }

    @Test
    public void defaultCountentShouldExist() throws Exception {
        final StateDTO content = contentService.getContent(null, new StateToken());
        assertNotNull(content);
        assertNotNull(content.getGroup());
        assertNotNull(content.getContainer());
        assertNotNull(content.getContainer().getId());
        assertNotNull(content.getToolName());
        assertNotNull(content.getDocumentId());
        assertNotNull(content.getRateByUsers());
        assertNotNull(content.getRate());
    }

    @Test
    public void noContentNotLogged() throws Exception {
        final StateDTO response = contentService.getContent(null, new StateToken());
        assertNotNull(response);
    }

    @Test(expected = ContentNotFoundException.class)
    public void nonExistentContent() throws Exception {
        contentService.getContent(null, new StateToken("foo foo foo"));
    }

    @Test(expected = ContentNotFoundException.class)
    public void nonExistentContent2() throws Exception {
        contentService.getContent(null, new StateToken("site.foofoo"));
    }

    @Test(expected = ContentNotFoundException.class)
    public void nonExistentContent3() throws Exception {
        contentService.getContent(null, new StateToken("site.docs.foofoo"));
    }

    @Test(expected = ContentNotFoundException.class)
    public void nonExistentContent4() throws Exception {
        final StateDTO stateDTO = getDefaultContent();
        contentService.getContent(null, stateDTO.getStateToken().clone().setDocument("dadaas"));
    }

    @Test(expected = ContentNotFoundException.class)
    public void nonExistentContent5() throws Exception {
        contentService.getContent(null, new StateToken("comm3.docs.19"));
    }

    @Test
    public void notLoggedUserShouldNotEditDefaultDoc() throws Exception {
        final StateDTO content = contentService.getContent(null, new StateToken());
        assertFalse(content.getContentRights().isAdministrable());
        assertFalse(content.getContentRights().isEditable());
        assertTrue(content.getContentRights().isVisible());
        assertFalse(content.getContainerRights().isAdministrable());
        assertFalse(content.getContainerRights().isEditable());
        assertTrue(content.getContainerRights().isVisible());
    }

    @Test
    public void unknownContent() throws Exception {
        final StateDTO content = contentService.getContent(null, new StateToken("site.docs"));
        assertNotNull(content);
        assertNotNull(content.getGroup());
        assertNotNull(content.getContainer());
        assertNotNull(content.getContainer().getId());
        assertNotNull(content.getToolName());
    }
}
