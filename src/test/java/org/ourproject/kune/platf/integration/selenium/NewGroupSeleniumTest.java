package org.ourproject.kune.platf.integration.selenium;

import static org.junit.Assert.assertFalse;

import org.junit.Ignore;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.site.msg.SiteToastMessagePanel;

public class NewGroupSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void newGroupBasic() throws Exception {
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        signInAndNewGroup();
        click(NewGroupPanel.CANCEL_BUTTON);
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        open(SiteToken.newgroup);
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, NewGroupPanel.REGISTER_A_NEW_GROUP_TITLE);
    }

    @Test
    public void newGroupNotLogged() throws Exception {
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        open(SiteToken.newgroup);
        waitForTextInside(SiteToastMessagePanel.MESSAGE, NewGroupPresenter.REGISTER_TO_CREATE_A_GROUP);
    }

    @Ignore
    public void newGroupWithExistingNicknameFails() throws Exception {
        setMustStopFinally(false);
        signInAndNewGroup();
        fillNewGroup1stPage("admin", "some long name" + genPrefix(), "some public description", "tag1 tag2 tag3",
                GroupType.ORGANIZATION);
        click(NewGroupPanel.NEXT_BUTTON);
        click(NewGroupPanel.FINISH_BUTTON);
        waitForTextInside(NewGroupPanel.ERROR_MSG_BAR, NewGroupPresenter.NAME_IN_ALREADY_IN_USE);
    }

    private void fillNewGroup1stPage(String shortname, String longName, String description, String tags,
            GroupType groupType) throws Exception {
        type(NewGroupPanel.SHORTNAME_FIELD, shortname);
        type(NewGroupPanel.LONGNAME_FIELD, longName);
        type(NewGroupPanel.PUBLICDESC_FIELD, description);
        type(NewGroupPanel.TAGS_FIELD, tags);
        switch (groupType) {
        case COMMUNITY:
            click(NewGroupPanel.COMM_GROUP_TYPE_ID);
            break;
        case ORGANIZATION:
            click(NewGroupPanel.ORG_GROUP_TYPE_ID);
            break;
        case PROJECT:
            click(NewGroupPanel.PROJ_GROUP_TYPE_ID);
            break;
        default:
            fail("Invalid group type");
            break;
        }
    }

    private void signInAndNewGroup() throws Exception {
        openDefPage();
        signIn();
        open(SiteToken.newgroup);
        verifyLoggedUserShorName("admin");
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, NewGroupPanel.REGISTER_A_NEW_GROUP_TITLE);
    }

}
