package org.ourproject.kune.platf.integration.selenium;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.workspace.client.options.GroupOptionsPresenter;
import org.ourproject.kune.workspace.client.options.logo.GroupOptionsLogoPanel;

public class EntityLogoSeleniumTest extends KuneSeleniumTestHelper {

    @Ignore
    public void testEntityLogoUpload() throws Exception {
        openDefPage();
        signIn();
        setLogo("kune-logo-400px.png");
        setLogo("kune-logo-without-text.png");
    }

    private void setLogo(final String filename) throws Exception, IOException {
        click(gid(GroupOptionsPresenter.GROUP_OPTIONS_ICON));
        // waitForTextInside(EntityOptionsLogoPanel.PANEL_ID,
        // PlatfMessages.ENT_OPTIONS_GROUP_TITLE);
        click(GroupOptionsLogoPanel.BUTTON_ID);
        final File dir = new File(".");
        type(FileConstants.GROUP_LOGO_FIELD, dir.getCanonicalPath() + File.separator + "img" + File.separator
                + filename);
        click(GroupOptionsLogoPanel.BUTTON_ID);
    }
}
