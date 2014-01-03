/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class KuneSeleniumDefaults.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneSeleniumDefaults {

  /** The Constant INJECTOR. */
  public static final Injector INJECTOR = Guice.createInjector(new SeleniumModule());

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(KuneSeleniumDefaults.class);

  /** The must close finally. */
  public static boolean mustCloseFinally = false;

  /** The base url. */
  private final String baseUrl;

  /** The chat. */
  protected final ChatPageObject chat;

  /** The entity header. */
  protected final EntityHeaderPageObject entityHeader;

  /** The group space. */
  protected GroupSpacePageObject groupSpace;

  /** The home space. */
  protected final HomeSpacePageObject homeSpace;

  /** The injector. */
  private final Injector injector;

  /** The login. */
  protected LoginPageObject login;

  /** The messages. */
  private final ResourceBundle messages;

  /** The new group. */
  protected NewGroupPageObject newGroup;

  /** The register. */
  protected RegisterPageObject register;

  /** The site. */
  protected final SitePageObject site;

  /** The subtitle popup. */
  private WebElement subtitlePopup;

  /** The user space. */
  protected UserSpacePageObject userSpace;

  /** The webdriver. */
  private final WebDriver webdriver;

  /**
   * Instantiates a new kune selenium defaults.
   */
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

  /**
   * Answer on next prompt.
   * 
   * @param answer
   *          the answer
   */
  public void answerOnNextPrompt(final String answer) {
    final Alert alert = webdriver.switchTo().alert();
    alert.sendKeys(answer);
    sleep(2000);
    alert.accept();
  }

  /**
   * Before methods.
   * 
   * @param context
   *          the context
   */
  @BeforeMethod
  public void beforeMethods(final ITestContext context) {
  }

  /**
   * Before suite.
   */
  @BeforeSuite
  public void beforeSuite() {
    resize();
    home();
    setPosition(100, 0);
    SeleniumUtils.initCursor(webdriver);
    SeleniumUtils.showCursor(webdriver);
    SeleniumUtils.showCursor(webdriver, login.getAnonMsg());
    sleep(1000);
    login.getAnonMsg().click();
  }

  /**
   * Browser back.
   */
  public void browserBack() {
    webdriver.navigate().back();
  }

  /**
   * Browser forward.
   */
  public void browserForward() {
    webdriver.navigate().forward();
  }

  /**
   * Close.
   */
  public void close() {
    webdriver.close();
  }

  /**
   * Close browser.
   */
  @AfterSuite
  public void closeBrowser() {
    // We try to only open one window for all our selenium tests
    if (mustCloseFinally) {
      close();
    }
  }

  /**
   * Creates the correct login.
   * 
   * @return the object[][]
   */
  @DataProvider(name = "correctlogin")
  public Object[][] createCorrectLogin() {
    // The default correct user/password used in tests
    return new Object[][] { { SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD } };
  }

  /**
   * Creates the correct register.
   * 
   * @return the object[][]
   */
  @DataProvider(name = "correctregister")
  public Object[][] createCorrectRegister() {
    // The default correct user/password used in tests
    return new Object[][] { { "jane", "Jane Doe", SeleniumConstants.USER_PASSWD, "jane@example.org" } };
  }

  /**
   * Creates the incorrect login.
   * 
   * @return the object[][]
   */
  @DataProvider(name = "incorrectlogin")
  public Object[][] createIncorrectLogin() {
    // Some pairs of user/passwd that must fail when try to login
    return new Object[][] { { "test1@localhost", "test1blabla" }, { "test1", "test1" },
        { "test1@localhost", "test" }, { "", "" } };
  }

  /**
   * Do screenshot.
   * 
   * @param filename
   *          the filename
   */
  public void doScreenshot(final String filename) {
    SeleniumUtils.doScreenshot(webdriver, filename);
  }

  /**
   * Gets the.
   * 
   * @param url
   *          the url
   */
  public void get(final String url) {
    webdriver.get(url);
  }

  /**
   * Gets the current history token.
   * 
   * @return the current history token
   */
  public String getCurrentHistoryToken() {
    final String currentUrl = webdriver.getCurrentUrl();
    LOG.info(String.format("Current url: %s", currentUrl));
    final String[] splitted = currentUrl.split("#");
    return splitted.length > 1 ? splitted[1] : "";
  }

  /**
   * Gets the page source.
   * 
   * @return the page source
   */
  public String getPageSource() {
    return webdriver.getPageSource();
  }

  /**
   * Goto token.
   * 
   * @param token
   *          the token
   */
  public void gotoToken(final StateToken token) {
    get(baseUrl + token);
  }

  /**
   * Goto token.
   * 
   * @param token
   *          the token
   */
  public void gotoToken(final String token) {
    get(baseUrl + token);
  }

  /**
   * Home.
   */
  public void home() {
    assert baseUrl != null;
    webdriver.get(baseUrl);
  }

  /**
   * New groups.
   * 
   * @return the object[][]
   */
  @DataProvider(name = "newGroups")
  public Object[][] newGroups() {
    return new Object[][] { { "grp1", "吗台湾", "吗台湾 吗台湾 吗台湾 吗台湾", "吗 台湾", GroupType.CLOSED },
        { "grp2", "Chomsky Fan Club", "Some chomsky fan club", "chomsky", GroupType.ORGANIZATION },
        { "grp3", "روبا", "روبا روبا روبا روبا", "روبا", GroupType.COMMUNITY },
        { "grp0", "Ecologist Group", "Melbourne eco feminist group", "eco feminism", GroupType.PROJECT } };
  }

  /**
   * Open.
   * 
   * @param url
   *          the url
   */
  public void open(final String url) {
    webdriver.get(url);
  }

  /**
   * Resize.
   */
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
   * Send keys but in a slow way (word by word).
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

  /**
   * Sets the position.
   * 
   * @param x
   *          the x
   * @param y
   *          the y
   */
  public void setPosition(final int x, final int y) {
    webdriver.manage().window().setPosition(new Point(x, y));
  }

  /**
   * Show cursor.
   * 
   * @param x
   *          the x
   * @param y
   *          the y
   */
  public void showCursor(final int x, final int y) {
    SeleniumUtils.showCursor(webdriver, x, y);
  }

  /**
   * Show msg.
   * 
   * @param msg
   *          the msg
   */
  public void showMsg(final String msg) {
    SeleniumUtils.showMsg(webdriver, "", msg);
  }

  /**
   * Show msg.
   * 
   * @param title
   *          the title
   * @param msg
   *          the msg
   */
  public void showMsg(final String title, final String msg) {
    SeleniumUtils.showMsg(webdriver, title, msg);
  }

  /**
   * Show title slide.
   * 
   * @param title
   *          the title
   */
  public void showTitleSlide(final String title) {
    showTitleSlide(title, "", getCurrentHistoryToken());
  }

  /**
   * Show title slide.
   * 
   * @param title
   *          the title
   * @param description
   *          the description
   */
  public void showTitleSlide(final String title, final String description) {
    showTitleSlide(title, description, getCurrentHistoryToken());
  }

  /**
   * Show title slide.
   * 
   * @param title
   *          the title
   * @param description
   *          the description
   * @param token
   *          the token
   */
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

  /**
   * Show tooltip.
   * 
   * @param element
   *          the element
   */
  public void showTooltip(final WebElement element) {
    SeleniumUtils.showTooltip(webdriver, element);
  }

  /**
   * Sleep.
   * 
   * @param milliseconds
   *          the milliseconds
   */
  public void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

  /**
   * T.
   * 
   * @param message
   *          the message
   * @return the string
   */
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
