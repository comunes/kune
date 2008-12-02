package org.ourproject.kune.platf.integration.selenium;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoPanel;

public class ContentAddSeleniumTest extends KuneSeleniumTestHelper {
    @Test
    public void wikiEditByAny() throws Exception {
        String shortname = "g" + genPrefix();
        String longName = "testing" + genPrefix();
        newGroupRegistrationDefLicense(shortname, longName, "some description", "tag1 tag2");
        waitForTextInside(gid(EntityLogoPanel.LOGO_NAME), longName);
        open(new StateToken(shortname, ""));
        signOut();
        wait(120000);
    }
}
