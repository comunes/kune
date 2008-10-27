package org.ourproject.kune.platf.integration.selenium;

import org.ourproject.kune.workspace.client.signin.SignInForm;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLinkPanel;
import org.ourproject.kune.workspace.client.title.EntityTitlePanel;

public class KuneSeleniumTestHelper extends SeleniumTestHelper {

    protected void ifLoggedSigOut() {
        if (selenium.getText(gid(SiteSignOutLinkPanel.SITE_SIGN_OUT)).indexOf("admin") > 0) {
            signOut();
        }
    }

    protected void openDefPage() {
        open("/kune/?locale=en");
    }

    protected void openDefPageAndSignOutIfLogged() throws Exception {
        openDefPage();
        waitForTextInside(gid(EntityTitlePanel.ENTITY_TITLE_RIGHT_TITLE), "Welcome to kune");
        ifLoggedSigOut();
    }

    protected void signIn() {
        signIn("admin", "easyeasy");
    }

    protected void signIn(String nick, String passwd) {
        selenium.click(gid(SiteSignInLinkPanel.SITE_SIGN_IN));
        selenium.type(SignInForm.NICKOREMAIL_FIELD, "admin");
        selenium.type(SignInForm.PASSWORD_FIELD, "easyeasy");
        selenium.click(SignInPanel.SIGN_IN_BUTTON_ID);
    }

    protected void signOut() {
        selenium.click("gwt-debug-k-ssolp-lb");
    }

    protected void verifyLoggedUserShorName(String userShortName) throws Exception {
        waitForTextInside(gid(SiteSignOutLinkPanel.SITE_SIGN_OUT), userShortName);
    }

}
