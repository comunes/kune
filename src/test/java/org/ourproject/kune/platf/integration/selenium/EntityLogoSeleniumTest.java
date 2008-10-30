package org.ourproject.kune.platf.integration.selenium;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoPanel;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoSelectorPanel;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoView;

public class EntityLogoSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void testEntityLogoUpload() throws Exception {
        setMustStopFinally(false);
        openDefPage();
        signIn();
        setLogo("kune-logo-400px.png");
        setLogo("kune-logo-without-text.png");
    }

    private void setLogo(String filename) throws Exception, IOException {
        click(gid(EntityLogoPanel.PUT_YOUR_LOGO_LINK));
        waitForTextInside(EntityLogoSelectorPanel.DIALOG_ID, EntityLogoSelectorPanel.TITLE);
        File dir = new File(".");
        type(EntityLogoView.LOGO_FORM_FIELD, dir.getCanonicalPath() + File.separator + "img" + File.separator
                + filename);
        click(EntityLogoSelectorPanel.SUBID);
    }
}
