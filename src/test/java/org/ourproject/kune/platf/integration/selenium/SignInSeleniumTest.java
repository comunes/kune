package org.ourproject.kune.platf.integration.selenium;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePagePanel;
import org.ourproject.kune.workspace.client.signin.RegisterPanel;
import org.ourproject.kune.workspace.client.signin.RegisterPresenter;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.signin.SignInPresenter;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserMenuPanel;

public class SignInSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void registerAdminNameMustFail() throws Exception {
        openDefPage();
        register("u" + genPrefix(), "Site Administrator", "somepasswd", "somepasswd", "some" + genPrefix()
                + "@example.com", "Andorra", "Afrikaans", "MIT", true);
        waitForTextInside(gid(RegisterPanel.ERRMSG), RegisterPresenter.NAME_IN_USE);
        click(RegisterPanel.CANCEL_BUTTON_ID);
    }

    @Test
    public void registerAdminNicknameMustFail() throws Exception {
        openDefPage();
        register("admin", "some name" + genPrefix(), "somepasswd", "somepasswd", "some" + genPrefix() + "@example.com",
                "Andorra", "Afrikaans", "MIT", true);
        waitForTextInside(gid(RegisterPanel.ERRMSG), RegisterPresenter.NAME_IN_USE);
        click(RegisterPanel.CANCEL_BUTTON_ID);
    }

    @Test
    public void registerSomeUser() throws Exception {
        openDefPage();
        registerValidUser(true);
    }

    @Ignore
    public void registerSomeUserWithouHomepage() throws Exception {
        openDefPage();
        registerValidUser(false);
        click(RegisterPanel.WELCOME_OK_BUTTON);
        click("//div[@id='" + gid(SiteUserMenuPanel.LOGGED_USER_MENU) + "']/div");
        click(linkId(SiteUserMenuPanel.YOUR_HOMEPAGE));
        waitForTextInside(gid(NoHomePagePanel.NO_HOME_PAGE_LABEL), NoHomePagePanel.USER_DON_T_HAVE_A_HOMEPAGE);
        wait(20000);
    }

    @Test
    public void testSignInAndOut() throws Exception {
        openDefPage();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
    }

    @Test
    public void testSignInAndOutRemovingCookie() throws Exception {
        openDefPage();
        signIn();
        assertNotNull(selenium.getCookieByName(Site.USERHASH));
        verifyLoggedUserShorName("admin");
        signOut();
    }

    @Test
    public void testSignInFailed() throws Exception {
        openDefPage();
        signIn("something", "wrong");
        waitForTextInside(gid(SignInPanel.ERROR_MSG), SignInPresenter.INCORRECT_NICKNAME_EMAIL_OR_PASSWORD);
        signOut();
    }

    @Test
    public void testSignInOutTwice() throws Exception {
        openDefPage();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
    }

    @Test
    public void testSignInRemember() throws Exception {
        openDefPage();
        signIn();
        verifyLoggedUserShorName("admin");
        selenium.refresh();
        verifyLoggedUserShorName("admin");
        signOut();
    }

    private long genPrefix() {
        long prefix = new Date().getTime();
        return prefix;
    }

    private String registerValidUser(boolean wantHomepage) {
        String shortName = "u" + genPrefix();
        register(shortName, "some name " + genPrefix(), "somepasswd", "somepasswd", genPrefix() + "@example.com",
                "Andorra", "English", "MET", wantHomepage);
        return shortName;
    }
}
