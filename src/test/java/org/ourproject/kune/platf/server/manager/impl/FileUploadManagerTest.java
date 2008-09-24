package org.ourproject.kune.platf.server.manager.impl;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

import com.google.inject.Inject;

public class FileUploadManagerTest extends IntegrationTest {

    @Inject
    FileUploadManager fileUploadManager;

    @Before
    public void create() {
	new IntegrationTestHelper(this);
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExp() throws Exception {
	fileUploadManager.createFile("otherhash", null, null, null);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testUserMustBeAuth() throws Exception {
	fileUploadManager.createFile(null, null, null, null);
    }
}
