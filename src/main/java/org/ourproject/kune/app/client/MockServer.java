package org.ourproject.kune.app.client;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceMocked;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceMocked;
import org.ourproject.kune.platf.client.rpc.MockedService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceMocked;

public class MockServer {
    public static final boolean GWT = false;
    public static final boolean TEST = true;

    public static void start(final boolean isTest) {
	MockedService.isTest = isTest;
	KuneService.App.setMock(new KuneServiceMocked());
	ContentService.App.setMock(new ContentServiceMocked());
	SiteBarService.App.setMock(new SiteBarServiceMocked());
    }

}
