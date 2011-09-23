/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.selenium;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.tools.GenericWebDriver;
import cc.kune.selenium.tools.SeleniumConstants;
import cc.kune.selenium.tools.SeleniumModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class KuneSeleniumDefaults {
  private static final Log LOG = LogFactory.getLog(KuneSeleniumDefaults.class);
  public static boolean mustCloseFinally = true;
  protected EventFiringWebDriver eventFiring;
  private final Injector injector;
  protected LoginPageObject login;
  protected GenericWebDriver webdriver;

  public KuneSeleniumDefaults() {
    injector = Guice.createInjector(new SeleniumModule());
    webdriver = injector.getInstance(GenericWebDriver.class);
    login = injector.getInstance(LoginPageObject.class);
    eventFiring = injector.getInstance(EventFiringWebDriver.class);
    eventFiring.register(new WebDriverEventListener() {

      @Override
      public void afterChangeValueOf(final WebElement element, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void afterClickOn(final WebElement element, final WebDriver driver) {
        sleep(500);
      }

      @Override
      public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void afterNavigateBack(final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void afterNavigateForward(final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void afterNavigateTo(final String url, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void afterScript(final String script, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeChangeValueOf(final WebElement element, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeClickOn(final WebElement element, final WebDriver driver) {
        sleep(500);
      }

      @Override
      public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeNavigateBack(final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeNavigateForward(final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeNavigateTo(final String url, final WebDriver driver) {
        // TODO Auto-generated method stub
        sleep(500);
      }

      @Override
      public void beforeScript(final String script, final WebDriver driver) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onException(final Throwable throwable, final WebDriver driver) {
        // TODO Auto-generated method stub

      }
    });
    final ElementLocatorFactory locator = injector.getInstance(ElementLocatorFactory.class);
    PageFactory.initElements(locator, login);
  }

  @AfterSuite
  public void closeBrowser() {
    // We try to only open one window for all our selenium tests
    if (mustCloseFinally) {
      webdriver.close();
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
    if (webdriver == null) {
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

    webdriver.home();
  }

  public void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }
}
