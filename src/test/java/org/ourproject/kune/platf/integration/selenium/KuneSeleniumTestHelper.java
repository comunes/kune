package org.ourproject.kune.platf.integration.selenium;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.WorkspaceMessages;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.signin.RegisterForm;
import org.ourproject.kune.workspace.client.signin.RegisterPanel;
import org.ourproject.kune.workspace.client.signin.SignInForm;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserMenuPanel;
import org.ourproject.kune.workspace.client.title.EntityTitlePanel;

import com.thoughtworks.selenium.SeleniumException;

public class KuneSeleniumTestHelper extends SeleniumTestHelper {

    protected static final String KUNE_BASE_URL = "/kune/?locale=en#";

    private static File dirCaptures;

    @BeforeClass
    public static void beforeKuneClass() {
        dirCaptures = new File("img/captures/");
        if (!dirCaptures.exists()) {
            dirCaptures.mkdir();
        }
    }

    private boolean mustCapture;

    @After
    public void after() throws IOException {
        if (mustCapture) {
            selenium.captureEntirePageScreenshot(File.createTempFile("kune", "capture.png", dirCaptures).getAbsolutePath());
        }
    }

    @Before
    public void before() {
        mustCapture = true;
        try {
            selenium.deleteAllVisibleCookies();
            selenium.refresh();
            selenium.windowMaximize();
        } catch (final UnsupportedOperationException e) {
            System.err.println("Seems that selenium server is not running; run before: 'mvn selenium:start-server' ");
        }
    }

    public void setMustCapture(boolean mustCapture) {
        this.mustCapture = mustCapture;
    }

    protected void fillNewGroup1stPage(String shortname, String longName, String description, String tags,
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

    protected long genPrefix() {
        long prefix = new Date().getTime();
        return prefix;
    }

    protected void ifLoggedSigOut() {
        if (selenium.getText(gid(SiteSignOutLinkPanel.SITE_SIGN_OUT)).indexOf("admin") > 0) {
            signOut();
        }
    }

    protected void newGroupRegistrationDefLicense(String shortname, String longName, String description, String tags)
            throws Exception {
        GroupType organization = GroupType.ORGANIZATION;
        signInAndNewGroup();
        fillNewGroup1stPage(shortname, longName, description, tags, organization);
        click(NewGroupPanel.NEXT_BUTTON);
        click(NewGroupPanel.FINISH_BUTTON);
    }

    protected void open(SiteToken token) {
        open(KUNE_BASE_URL + token.toString());
    }

    protected void open(StateToken token) {
        open(KUNE_BASE_URL + token.toString());
    }

    @Override
    protected void open(String url) {
        try {
            selenium.setTimeout("0");
            super.open(url);
            selenium.setTimeout("30");
        } catch (SeleniumException e) {
            // TODO Auto-generated method stub
        }
    }

    protected void openDefPage() throws Exception {
        open("/kune/?locale=en#site");
        waitForTextInside(gid(EntityTitlePanel.ENTITY_TITLE_RIGHT_TITLE), "Welcome to kune");
    }

    protected void register(String shortName, String longName, String passwd, String passwdDup, String email,
            String country, String language, String tz, boolean wantHomepage) {
        click(gid(SiteSignInLinkPanel.SITE_SIGN_IN));
        click(gid(SignInPanel.CREATE_ONE));
        type(RegisterForm.NICK_FIELD, shortName);
        type(RegisterForm.LONGNAME_FIELD, longName);
        type(RegisterForm.PASSWORD_FIELD, passwd);
        type(RegisterForm.PASSWORD_FIELD_DUP, passwdDup);
        type(RegisterForm.EMAIL_FIELD, email);
        type(RegisterForm.LANG_FIELD, language);
        type(RegisterForm.COUNTRY_FIELD, country);
        type(RegisterForm.TIMEZONE_FIELD, tz);
        // div[6]/div[1]/div/img
        click("//div[6]/div[1]/div/img");
        click("//div[text()='" + language + "']");
        click("//div[7]/div[1]/div/img");
        click("//div[text()='" + country + "']");
        click("//div[8]/div[1]/div/img");
        click("//div[text()='" + tz + "']");
        // "xpath=//div\[contains(@style,'visible')\]/div\[@class='x-combo-list-inner']/div[text()='"ItemTextValue"'\]";
        type("//div[@id='k-regp-p']/div/div/div/div/div/div/div/div/form/div[6]/div/div/input[2]", language);
        type("//div[@id='k-regp-p']/div/div/div/div/div/div/div/div/form/div[7]/div/div/input[2]", country);
        type("//div[@id='k-regp-p']/div/div/div/div/div/div/div/div/form/div[8]/div/div/input[2]", tz);

        if (wantHomepage) {
            click(RegisterForm.WANNAPERSONALHOMEPAGE_ID);
        } else {
            click(RegisterForm.NOPERSONALHOMEPAGE_ID);
        }
        click(RegisterPanel.REGISTER_BUTTON_ID);
    }

    protected String registerValidUser(boolean wantHomepage) {
        String shortName = "u" + genPrefix();
        register(shortName, "some name " + genPrefix(), "somepasswd", "somepasswd", genPrefix() + "@example.com",
                "Andorra", "English", "MET", wantHomepage);
        return shortName;
    }

    protected void signIn() {
        signIn("admin", "easyeasy");
    }

    protected void signIn(String nick, String passwd) {
        click(gid(SiteSignInLinkPanel.SITE_SIGN_IN));
        type(SignInForm.NICKOREMAIL_FIELD, nick);
        type(SignInForm.PASSWORD_FIELD, passwd);
        click(SignInPanel.SIGN_IN_BUTTON_ID);
    }

    protected void signInAndNewGroup() throws Exception {
        openDefPage();
        signIn();
        verifyLoggedUserShorName("admin");
        open(SiteToken.newgroup);
        waitForTextInside(NewGroupPanel.NEWGROUP_WIZARD, WorkspaceMessages.REGISTER_A_NEW_GROUP_TITLE);
    }

    protected void signOut() {
        click("gwt-debug-k-ssolp-lb");
    }

    protected void verifyLoggedUserShorName(String userShortName) throws Exception {
        waitForTextInside(gid(SiteUserMenuPanel.LOGGED_USER_MENU), userShortName);
    }
}
