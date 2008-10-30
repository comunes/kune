package org.ourproject.kune.platf.integration.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.site.SiteToken;

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
        openDefPage();
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        assertFalse(selenium.isTextPresent(NewGroupPresenter.REGISTER_TO_CREATE_A_GROUP));
        open(SiteToken.newgroup);
        wait(1000);
        assertTrue(selenium.isTextPresent(NewGroupPresenter.REGISTER_TO_CREATE_A_GROUP));
    }

    @Test
    public void newGroupWithExistingNicknameFails() throws Exception {
        signInAndNewGroup();
        fillNewGroup1stPage("admin", "some long name" + genPrefix(), "some public description", "tag1 tag2 tag3",
                GroupType.ORGANIZATION);
        click(NewGroupPanel.NEXT_BUTTON);
        click(NewGroupPanel.FINISH_BUTTON);
        waitForTextInside(gid(NewGroupPanel.ERROR_MSG_BAR), NewGroupPresenter.NAME_IN_ALREADY_IN_USE);
    }

    @Test
    public void newGroupWithNonOccidentalChars() throws Exception {
        setMustCapture(true);
        String longName = "漢語 中文 华语 汉语" + genPrefix();
        newGroupRegistrationDefLicense("g" + genPrefix(), longName,
                "吗 台湾 六种辅音韵尾 中国政府要求在中国出售的软件必须使用编码 过 国标 名词的复数形式只在代词及多音节", "漢語 中文 华语 汉语");
        waitForTextInside(gid(EntityLogoPanel.LOGO_NAME), longName);
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

    private void newGroupRegistrationDefLicense(String shortname, String longName, String description, String tags)
            throws Exception {
        GroupType organization = GroupType.ORGANIZATION;
        signInAndNewGroup();
        fillNewGroup1stPage(shortname, longName, description, tags, organization);
        click(NewGroupPanel.NEXT_BUTTON);
        click(NewGroupPanel.FINISH_BUTTON);
    }

    private void signInAndNewGroup() throws Exception {
        openDefPage();
        signIn();
        open(SiteToken.newgroup);
        verifyLoggedUserShorName("admin");
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, NewGroupPanel.REGISTER_A_NEW_GROUP_TITLE);
    }
}
