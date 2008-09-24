package org.ourproject.kune.platf.server.manager.impl;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
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
    public void testNoAuth() throws Exception {
	fileUploadManager.createFile("otherhash", null, null, null);
    }
}
