package org.ourproject.kune.platf.integration.selenium;

import org.junit.Test;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.signin.SignInPresenter;

public class SignInSeleniumTest extends KuneSeleniumTestHelper {

    @Test
    public void testSignInAndOut() throws Exception {
        openDefPageAndSignOutIfLogged();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
    }

    @Test
    public void testSignInFailed() throws Exception {
        openDefPageAndSignOutIfLogged();
        signIn("something", "wrong");
        waitForTextInside(gid(SignInPanel.ERROR_MSG), SignInPresenter.INCORRECT_NICKNAME_EMAIL_OR_PASSWORD);
        signOut();
    }

    @Test
    public void testSignInOutTwice() throws Exception {
        openDefPageAndSignOutIfLogged();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
        signIn();
        verifyLoggedUserShorName("admin");
        signOut();
    }
}
