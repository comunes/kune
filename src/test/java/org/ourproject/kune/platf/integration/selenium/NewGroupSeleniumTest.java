package org.ourproject.kune.platf.integration.selenium;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.workspace.client.WorkspaceMessages;
import org.ourproject.kune.workspace.client.entitylogo.EntityTextLogo;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.site.SiteToken;

public class NewGroupSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void newGroupBasic() throws Exception {
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        signInAndNewGroup();
        click(NewGroupPanel.CANCEL_BUTTON);
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        open(SiteToken.newgroup);
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, WorkspaceMessages.REGISTER_A_NEW_GROUP_TITLE);
    }

    @Test
    public void newGroupNotLogged() throws Exception {
        openDefPage();
        assertFalse(selenium.isTextPresent(NewGroupPanel.NEWGROUP_WIZARD));
        assertFalse(selenium.isTextPresent(WorkspaceMessages.REGISTER_TO_CREATE_A_GROUP));
        open(SiteToken.newgroup);
        wait(1000);
        assertTrue(selenium.isTextPresent(WorkspaceMessages.REGISTER_TO_CREATE_A_GROUP));
    }

    @Test
    public void newGroupWithExistingNicknameFails() throws Exception {
        signInAndNewGroup();
        fillNewGroup1stPage("admin", "some long name" + genPrefix(), "some public description", "tag1 tag2 tag3",
                GroupType.ORGANIZATION);
        click(NewGroupPanel.NEXT_BUTTON);
        click(NewGroupPanel.FINISH_BUTTON);
        waitForTextInside(gid(NewGroupPanel.ERROR_MSG_BAR), WorkspaceMessages.NAME_IN_ALREADY_IN_USE);
    }

    @Test
    public void newGroupWithNonOccidentalChars() throws Exception {
        String longName = "漢語 中文 华语 汉语" + genPrefix();
        newGroupRegistrationDefLicense("g" + genPrefix(), longName,
                "吗 台湾 六种辅音韵尾 中国政府要求在中国出售的软件必须使用编码 过 国标 名词的复数形式只在代词及多音节", "漢語 中文 华语 汉语");
        waitForTextInside(gid(EntityTextLogo.LOGO_NAME), longName);
    }
}
