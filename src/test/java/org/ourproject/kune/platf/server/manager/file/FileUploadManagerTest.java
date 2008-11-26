package org.ourproject.kune.platf.server.manager.file;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.integration.content.ContentServiceIntegrationTest;

import com.google.inject.Inject;

public class FileUploadManagerTest extends ContentServiceIntegrationTest {

    @Inject
    FileUploadManager fileUploadManager;

    @Before
    public void create() {
        new IntegrationTestHelper(this);
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExp() throws Exception {
        fileUploadManager.createUploadedFile("otherhash", null, null, null, null);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testUserMustBeAuth() throws Exception {
        fileUploadManager.createUploadedFile(null, null, null, null, null);
    }
}
