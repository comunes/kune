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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.sub.SubtitlesWidget;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.selenium.chat.ChatPageObject;
import cc.kune.selenium.general.EntityHeaderPageObject;
import cc.kune.selenium.general.SpacesPageObject;
import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.login.RegisterPageObject;
import cc.kune.selenium.tools.SeleniumConstants;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class KuneSeleniumDefaults {

  public static final Injector INJECTOR = Guice.createInjector(new SeleniumModule());

  private static final Log LOG = LogFactory.getLog(KuneSeleniumDefaults.class);
  public static boolean mustCloseFinally = true;
  private final String baseUrl;
  protected final ChatPageObject chat;
  protected final EntityHeaderPageObject entityHeader;
  private final Injector injector;
  protected LoginPageObject login;
  protected RegisterPageObject register;
  protected final SpacesPageObject spaces;
  private final WebDriver webdriver;

  public KuneSeleniumDefaults() {
    baseUrl = "http://kune.beta.iepala.es/?locale=en#";
    // baseUrl =
    // "http://127.0.0.1:8888/?locale=es&log_level=INFO&gwt.codesvr=127.0.0.1:9997#";
    injector = INJECTOR;
    webdriver = injector.getInstance(WebDriver.class);
    login = injector.getInstance(LoginPageObject.class);
    register = injector.getInstance(RegisterPageObject.class);
    entityHeader = injector.getInstance(EntityHeaderPageObject.class);
    spaces = injector.getInstance(SpacesPageObject.class);
    chat = injector.getInstance(ChatPageObject.class);
    final ElementLocatorFactory locator = injector.getInstance(ElementLocatorFactory.class);
    PageFactory.initElements(locator, login);
    PageFactory.initElements(locator, register);
    PageFactory.initElements(locator, entityHeader);
    PageFactory.initElements(locator, spaces);
    PageFactory.initElements(locator, chat);
  }

  @BeforeMethod
  public void beforeMethods(final ITestContext context) {
  }

  @BeforeSuite
  public void beforeSuite() {
    resize();
    LOG.info("Going home");
    home();
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
    return new Object[][] { { SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_LONGNAME,
        SeleniumConstants.USER_PASSWD, SeleniumConstants.USER_EMAIL } };
  }

  @DataProvider(name = "incorrectlogin")
  public Object[][] createIncorrectLogin() {
    // Some pairs of user/passwd that must fail when try to login
    return new Object[][] { { "test1@localhost", "test1blabla" }, { "test1", "test1" },
        { "test1@localhost", "test" }, { "", "" } };
  }

  public void get(final String url) {
    webdriver.get(url);
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
    login.getAnonMsg().click();
  }

  public void open(final String url) {
    webdriver.get(url);
  }

  public void resize() {
    final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    // Some others tested values:
    // 1024,769
    // 840,770
    // 806,707
    js.executeScript("window.resizeTo(806,707); window.moveTo(0,0);");
  }

  public void showMsg(final String msg) {
    SeleniumUtils.showMsg(webdriver, "", msg);
  }

  public void showMsg(final String title, final String msg) {
    SeleniumUtils.showMsg(webdriver, title, msg);
  }

  public void showTitleSlide(final String title) {
    showTitleSlide(title, "", "");
  }

  public void showTitleSlide(final String title, final String description) {
    showTitleSlide(title, description, "");
  }

  public void showTitleSlide(final String title, final String description, final String token) {
    gotoToken(TokenUtils.subtitle(title, description, token));
    sleep(3000);
    webdriver.findElement(By.id(SeleniumConstants.GWTDEV + SubtitlesWidget.SUBTITLE_MANAGER_ID)).click();
  }

  public void showTooltip(final WebElement element) {
    SeleniumUtils.showTooltip(webdriver, element);
  }

  public void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

}
