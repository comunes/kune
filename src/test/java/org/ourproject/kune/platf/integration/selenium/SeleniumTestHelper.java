package org.ourproject.kune.platf.integration.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.ourproject.kune.workspace.client.signin.SignInForm;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLinkPanel;
import org.ourproject.kune.workspace.client.title.EntityTitlePanel;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumTestHelper {

    private static final String GWT_DEBUG = "gwt-debug-";

    private static DefaultSelenium selenium;

    @AfterClass
    public static void afterClass() throws Exception {
        selenium.stop();
    }

    /**
     * 
     * If you get and null in ./content/recorder.js line 74 running test, this
     * happens when you compile gwt with PRETTY instead of OBF. See:
     * 
     * <pre>
     * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/5d6a9c448a82b916/af62e5877237b107?lnk=raot
     * </pre>
     * 
     * <pre>
     * http://code.google.com/p/google-web-toolkit/issues/detail?id=2861
     * </pre>
     * 
     * @param url
     * @return
     * @throws Exception
     */
    @BeforeClass
    public static void beforeClass() {
        // ff3 hangs: http://jira.openqa.org/browse/SRC-225
        // as a workarount use ff2:
        //
        // return new DefaultSelenium("localhost", 4441,
        // "*firefox /usr/lib/firefox-3.0.3/firefox", url);

        // this is a problem... platform dependence ...
        selenium = new DefaultSelenium("localhost", 4441, "*firefox /usr/lib/firefox/firefox-2-bin",
                "http://localhost:8080/");
        selenium.start();
    }

    protected void fail(String message) throws Exception {
        throw new Exception(message);
    }

    protected String gid(String id) {
        return GWT_DEBUG + id;
    }

    protected void ifLoggedSigOut() {
        if (selenium.getText(gid(SiteSignOutLinkPanel.SITE_SIGN_OUT)).indexOf("admin") > 0) {
            signOut();
        }
    }

    protected void openDefPage() {
        open("/kune/?locale=en");
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

    protected void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void waitForTextInside(String id, String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                if (selenium.getText(id).indexOf(text) < 0) {
                    break;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    protected void waitForTextRegExp(String id, String text) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) {
                fail("timeout");
            }
            try {
                if (selenium.getText(id).matches(text)) {
                    break;
                }
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    private void open(String url) {
        try {
            selenium.open(url);
        } catch (final UnsupportedOperationException e) {
            System.err.println("Seems that selenium server is not running; run before: 'mvn selenium:start-server' ");
        }
    }

    protected void openDefPageAndSignOutIfLogged() throws Exception {
        openDefPage();
        waitForTextInside(gid(EntityTitlePanel.ENTITY_TITLE_RIGHT_TITLE), "Welcome to kune");
        ifLoggedSigOut();
    }

    protected void verifyLoggedUserShorName(String userShortName) throws Exception {
        waitForTextInside(gid(SiteSignOutLinkPanel.SITE_SIGN_OUT), userShortName);
    }
}
