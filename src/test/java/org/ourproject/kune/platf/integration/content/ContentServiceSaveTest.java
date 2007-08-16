package org.ourproject.kune.platf.integration.content;

import org.junit.Before;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

public class ContentServiceSaveTest {
    ContentService service;
    SiteBarService loginService;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

}
