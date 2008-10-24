package org.ourproject.kune.platf.server.manager.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.integration.content.ContentServiceIntegrationTest;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoView;

import com.google.inject.Inject;

public class EntityLogoUploadManagerTest extends ContentServiceIntegrationTest {

    private static final String TEST_FILE = "src/test/java/org/ourproject/kune/platf/server/manager/file/orig.png";
    @Inject
    EntityLogoUploadManager manager;

    @Before
    public void create() {
        new IntegrationTestHelper(this);
    }

    @Test
    public void testCreateLogo() throws Exception {
        manager.createUploadedFile(super.getDefaultContent().getStateToken(), "image/png", new File(TEST_FILE));
        StateContainerDTO defaultContent = super.getDefaultContent();
        assertTrue(defaultContent.getGroup().hasLogo());
    }

    @Test
    public void testErrorResponse() {
        JSONObject expected = JSONObject.fromObject("{\"success\":false,\"errors\":[{\"id\":\""
                + EntityLogoView.LOGO_FORM_FIELD + "\",\"msg\":\"Some message\"}]}");
        assertEquals(expected, manager.createJsonResponse(false, "Some message"));
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExp() throws Exception {
        manager.createUploadedFile("otherhash", null, null, null);
    }

    @Test
    public void testSuccessResponse() {
        JSONObject expected = JSONObject.fromObject("{\"success\":true,\"errors\":[{}]}");
        assertEquals(expected, manager.createJsonResponse(true, null));
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testUserMustBeAuth() throws Exception {
        manager.createUploadedFile(null, null, null, null);
    }
}
