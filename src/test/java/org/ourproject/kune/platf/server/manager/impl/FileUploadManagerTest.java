package org.ourproject.kune.platf.server.manager.impl;

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

    // @Test
    public void testMimeTypePersist() throws Exception {
	// doLogin();
	// final FileItem fileItem = Mockito.mock(FileItem.class);
	// file = File.createTempFile("test", ".txt");
	// final Content cnt = fileUploadManager.createFile(super.getHash(),
	// getDefaultContent().getContainer()
	// .getStateToken(), "somefilename", fileItem);
	// final StateDTO state = contentService.getContent(getHash(),
	// cnt.getStateToken());
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExp() throws Exception {
	fileUploadManager.createUploadedFile("otherhash", null, null, null);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testUserMustBeAuth() throws Exception {
	fileUploadManager.createUploadedFile(null, null, null, null);
    }
}
