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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import cc.kune.core.client.i18n.I18nUtils;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.sub.SubtitlesWidget;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.selenium.chat.ChatPageObject;
import cc.kune.selenium.general.EntityHeaderPageObject;
import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.login.RegisterPageObject;
import cc.kune.selenium.spaces.GroupSpacePageObject;
import cc.kune.selenium.spaces.HomeSpacePageObject;
import cc.kune.selenium.spaces.NewGroupPageObject;
import cc.kune.selenium.spaces.SitePageObject;
import cc.kune.selenium.spaces.UserSpacePageObject;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class KuneSeleniumDefaults {

  public static final Injector INJECTOR = Guice.createInjector(new SeleniumModule());

  private static final Log LOG = LogFactory.getLog(KuneSeleniumDefaults.class);
  public static boolean mustCloseFinally = false;
  private final String baseUrl;
  protected final ChatPageObject chat;
  protected final EntityHeaderPageObject entityHeader;
  protected GroupSpacePageObject groupSpace;
  protected final HomeSpacePageObject homeSpace;
  private final Injector injector;
  protected LoginPageObject login;
  private final ResourceBundle messages;
  protected NewGroupPageObject newGroup;
  protected RegisterPageObject register;
  protected final SitePageObject site;
  private WebElement subtitlePopup;
  protected UserSpacePageObject userSpace;
  private final WebDriver webdriver;

  public KuneSeleniumDefaults() {
    baseUrl = "http://" + SeleniumConf.SITE.getDomain() + "/" + SeleniumConf.SITE.getParams() + "#";
    injector = INJECTOR;
    final ElementLocatorFactory locator = injector.getInstance(ElementLocatorFactory.class);
    webdriver = injector.getInstance(WebDriver.class);

    login = injector.getInstance(LoginPageObject.class);
    register = injector.getInstance(RegisterPageObject.class);
    entityHeader = injector.getInstance(EntityHeaderPageObject.class);
    site = injector.getInstance(SitePageObject.class);
    chat = injector.getInstance(ChatPageObject.class);
    homeSpace = injector.getInstance(HomeSpacePageObject.class);
    userSpace = injector.getInstance(UserSpacePageObject.class);
    groupSpace = injector.getInstance(GroupSpacePageObject.class);
    newGroup = injector.getInstance(NewGroupPageObject.class);

    PageFactory.initElements(locator, login);
    PageFactory.initElements(locator, register);
    PageFactory.initElements(locator, entityHeader);
    PageFactory.initElements(locator, site);
    PageFactory.initElements(locator, chat);
    PageFactory.initElements(locator, homeSpace);
    PageFactory.initElements(locator, userSpace);
    PageFactory.initElements(locator, groupSpace);
    PageFactory.initElements(locator, newGroup);

    messages = injector.getInstance(ResourceBundle.class);
  }

  public void answerOnNextPrompt(final String answer) {
    final Alert alert = webdriver.switchTo().alert();
    alert.sendKeys(answer);
    alert.accept();
  }

  @BeforeMethod
  public void beforeMethods(final ITestContext context) {
  }

  @BeforeSuite
  public void beforeSuite() {
    resize();
    home();
    SeleniumUtils.initCursor(webdriver);
    SeleniumUtils.showCursor(webdriver);
    SeleniumUtils.showCursor(webdriver, login.getAnonMsg());
    sleep(1000);
    login.getAnonMsg().click();
  }

  public void browserBack() {
    webdriver.navigate().back();
  }

  public void browserForward() {
    webdriver.navigate().forward();
  }

  public void close() {
    webdriver.close();
  }

  @AfterSuite
  public void closeBrowser() {
    // We try to only open one window for all our selenium tests
    if (mustCloseFinally) {
      close();
    }
  }

  @DataProvider(name = "correctlogin")
  public Object[][] createCorrectLogin() {
    // The default correct user/password used in tests
    return new Object[][] { { SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD } };
  }

  @DataProvider(name = "correctregister")
  public Object[][] createCorrectRegister() {
    // The default correct user/password used in tests
    return new Object[][] { { "jane", "Jane Doe", SeleniumConstants.USER_PASSWD, "jane@example.org" } };
  }

  @DataProvider(name = "incorrectlogin")
  public Object[][] createIncorrectLogin() {
    // Some pairs of user/passwd that must fail when try to login
    return new Object[][] { { "test1@localhost", "test1blabla" }, { "test1", "test1" },
        { "test1@localhost", "test" }, { "", "" } };
  }

  public void doScreenshot(final String filename) {
    SeleniumUtils.doScreenshot(webdriver, filename);
  }

  public void get(final String url) {
    webdriver.get(url);
  }

  public String getCurrentHistoryToken() {
    final String currentUrl = webdriver.getCurrentUrl();
    LOG.info(String.format("Current url: %s", currentUrl));
    final String[] splitted = currentUrl.split("#");
    return splitted.length > 1 ? splitted[1] : "";
  }

  public String getPageSource() {
    return webdriver.getPageSource();
  }

  public void gotoToken(final StateToken token) {
    get(baseUrl + token);
  }

  public void gotoToken(final String token) {
    get(baseUrl + token);
  }

  public void home() {
    assert baseUrl != null;
    webdriver.get(baseUrl);
  }

  @DataProvider(name = "newGroups")
  public Object[][] newGroups() {
    return new Object[][] { { "grp1", "吗台湾", "吗台湾 吗台湾 吗台湾 吗台湾", "吗 台湾", GroupType.CLOSED },
        { "grp2", "Chomsky Fan Club", "Some chomsky fan club", "chomsky", GroupType.ORGANIZATION },
        { "grp3", "روبا", "روبا روبا روبا روبا", "روبا", GroupType.COMMUNITY },
        { "grp0", "Ecologist Group", "Melbourne eco feminist group", "eco feminism", GroupType.PROJECT } };
  }

  public void open(final String url) {
    webdriver.get(url);
  }

  public void resize() {
    // Some others tested values:
    // 1024,769
    // 840,770
    // 806,707
    webdriver.manage().window().setPosition(new Point(0, 0));
    webdriver.manage().window().setSize(new Dimension(806, 707));
    // Before we were using:
    // final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    // js.executeScript("window.resizeTo(806,707); window.moveTo(0,0);");
  }

  /**
   * Send keys but in a slow way (word by word)
   * 
   * @param element
   *          the element
   * @param strings
   *          the strings
   */
  public void sendKeys(final WebElement element, final String... strings) {
    for (final String s : strings) {
      final String[] splitted = s.split(" ");
      for (int i = 0; i < splitted.length; i++) {
        element.sendKeys(splitted[i]);
        if (i < splitted.length - 1) {
          element.sendKeys(" ");
        }
      }
    }
  }

  public void showCursor(final int x, final int y) {
    SeleniumUtils.showCursor(webdriver, x, y);
  }

  public void showMsg(final String msg) {
    SeleniumUtils.showMsg(webdriver, "", msg);
  }

  public void showMsg(final String title, final String msg) {
    SeleniumUtils.showMsg(webdriver, title, msg);
  }

  public void showTitleSlide(final String title) {
    showTitleSlide(title, "", getCurrentHistoryToken());
  }

  public void showTitleSlide(final String title, final String description) {
    showTitleSlide(title, description, getCurrentHistoryToken());
  }

  public void showTitleSlide(final String title, final String description, final String token) {
    gotoToken(TokenUtils.subtitle(title, description, token));
    sleep(4000);
    if (subtitlePopup == null) {
      subtitlePopup = webdriver.findElement(By.id(SeleniumConstants.GWTDEV
          + SubtitlesWidget.SUBTITLE_MANAGER_ID));
    }
    subtitlePopup.click();
    // SeleniumUtils.moveMouseToAndClick(webdriver, subtitlePopup, 100, 100);
  }

  public void showTooltip(final WebElement element) {
    SeleniumUtils.showTooltip(webdriver, element);
  }

  public void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

  public String t(final String message) {
    final String methodName = I18nUtils.convertMethodName(message);
    try {
      return messages.getString(methodName);
    } catch (final MissingResourceException e) {
      LOG.info(methodName + " = " + message);
      return message;
    }
  }

}
