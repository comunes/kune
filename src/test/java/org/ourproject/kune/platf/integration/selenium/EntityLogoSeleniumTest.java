package org.ourproject.kune.platf.integration.selenium;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.ourproject.kune.workspace.client.entitylogo.EntityHeaderView;
import org.ourproject.kune.workspace.client.options.EntityOptionsPanel;
import org.ourproject.kune.workspace.client.options.logo.EntityOptionsLogoPanel;

public class EntityLogoSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void testEntityLogoUpload() throws Exception {
        openDefPage();
        signIn();
        setLogo("kune-logo-400px.png");
        setLogo("kune-logo-without-text.png");
    }

    private void setLogo(String filename) throws Exception, IOException {
        click(gid(EntityOptionsPanel.GROUP_OPTIONS_ICON));
        // waitForTextInside(EntityOptionsLogoPanel.PANEL_ID,
        // PlatfMessages.ENT_OPTIONS_GROUP_TITLE);
        click(EntityOptionsLogoPanel.SET_LOGO_ID);
        File dir = new File(".");
        type(EntityHeaderView.LOGO_FORM_FIELD, dir.getCanonicalPath() + File.separator + "img" + File.separator
                + filename);
        click(EntityOptionsLogoPanel.SET_LOGO_ID);
    }
}
