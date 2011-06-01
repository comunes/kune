package cc.kune.selenium;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.tools.GenericWebTester;
import cc.kune.selenium.tools.SeleniumConstants;
import cc.kune.selenium.tools.SeleniumModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class KuneSeleniumDefaults {
  private static final Log LOG = LogFactory.getLog(KuneSeleniumDefaults.class);
  public static boolean mustCloseFinally = true;

  private final Injector injector;

  protected LoginPageObject login;
  protected GenericWebTester webtester;

  public KuneSeleniumDefaults() {
    injector = Guice.createInjector(new SeleniumModule());
    webtester = injector.getInstance(GenericWebTester.class);
    login = injector.getInstance(LoginPageObject.class);
    final ElementLocatorFactory locator = injector.getInstance(ElementLocatorFactory.class);
    PageFactory.initElements(locator, login);
  }

  @AfterSuite
  public void closeBrowser() {
    // We try to only open one window for all our selenium tests
    if (mustCloseFinally) {
      webtester.close();
    }
  }

  @DataProvider(name = "correctlogin")
  public Object[][] createCorrectLogin() {
    // The default correct user/password used in tests
    return new Object[][] { { SeleniumConstants.USERNAME, SeleniumConstants.PASSWD }, };
  }

  // public void moveMouseAt(final Point point) {
  // webtester.moveMouseAt(point);
  // }

  @DataProvider(name = "incorrectlogin")
  public Object[][] createIncorrectLogin() {
    // Some pairs of user/passwd that must fail when try to login
    return new Object[][] { { "test1@localhost", "test1blabla" }, { "test1", "test1" },
        { "test1@localhost", "test" }, { "", "" } };
  }

  @BeforeMethod
  public void setupSeleniumModule(final ITestContext context) {

    // if (!Suco.getComponents().hasProvider(WebDriver.class)) {
    // Suco.install(new SeleniumModule());
    // }
    if (webtester == null) {
      // final ChromeDriver driver = new ChromeDriver();
      // // final HtmlUnitDriver driver = new HtmlUnitDriver(true);
      //
      // // final ProfilesIni allProfiles = new ProfilesIni();
      // // final FirefoxProfile profile =
      // // allProfiles.getProfile(SeleniumConstants.FIREFOX_PROFILE_NAME);
      // // final FirefoxDriver driver = new FirefoxDriver(profile);
      //
      // locator = new AjaxElementLocatorFactory(driver,
      // SeleniumConstants.TIMEOUT);
      // webtester = new GenericWebTester(driver,
      // "http://kune.beta.iepala.es/ws/?locale=en#");
      // // webtester = Suco.get(GenericWebTester.class);
      // // login = Suco.get(LoginPageObject.class);
      // login = new LoginPageObject();
      // PageFactory.initElements(locator, login);
      // driver.get("http://kune.beta.iepala.es/ws/?locale=en#");
      // roster = Suco.get(RosterPageObject.class);
      // openChat = Suco.get(OpenChatPageObject.class);
      // search = Suco.get(SearchPageObject.class);
      // chat = Suco.get(ChatPageObject.class);
      // editBuddy = Suco.get(EditBuddyPageObject.class);
      // groupChat = Suco.get(GroupChatPageObject.class);
      // userPage = Suco.get(UserPageObject.class);
      // otherVCardPage = Suco.get(OtherVCardPageObject.class);
      // copyToClipboard = Suco.get(CopyToClipboardPageObject.class);
      // openGroupChat = Suco.get(OpenGroupChatPageObject.class);
    }
    LOG.info("Going home");
    webtester.home();
  }

  public void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }
}
