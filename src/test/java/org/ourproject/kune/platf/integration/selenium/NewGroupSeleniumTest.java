package org.ourproject.kune.platf.integration.selenium;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.site.msg.SiteToastMessagePanel;

public class NewGroupSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void newGroupBasic() throws Exception {
        setMustStopFinally(false);
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        openDefPage();
        signIn();
        selenium.refresh();
        open(SiteToken.newgroup);
        verifyLoggedUserShorName("admin");
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, NewGroupPanel.REGISTER_A_NEW_GROUP_TITLE);
    }

    @Test
    public void newGroupNotLogged() throws Exception {
        setMustStopFinally(false);
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        open(SiteToken.newgroup);
        waitForTextInside(SiteToastMessagePanel.MESSAGE, NewGroupPresenter.REGISTER_TO_CREATE_A_GROUP);
    }

    @Test
    public void newGroupWithExistingNicknameFails() {
        open(SiteToken.newgroup);

    }

}
