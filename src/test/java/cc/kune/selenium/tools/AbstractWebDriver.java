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
package cc.kune.selenium.tools;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.ui.UIObject;
import com.thoughtworks.selenium.Selenium;

public abstract class AbstractWebDriver implements WebDriver {
  private final String baseUrl;
  private final WebDriver driver;
  private final Selenium selenium;

  public AbstractWebDriver(final WebDriver driver, final String baseUrl) {
    this.driver = driver;
    this.baseUrl = baseUrl;
    selenium = new WebDriverBackedSelenium(driver, baseUrl);
    selenium.setSpeed("500");
    // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    // selenium.setTimeout("5000");
  }

  @Override
  public void close() {
    driver.close();
  }

  @Override
  public WebElement findElement(final By by) {
    return driver.findElement(by);
  }

  @Override
  public List<WebElement> findElements(final By by) {
    return driver.findElements(by);
  }

  @Override
  public void get(final String url) {
    driver.get(url);
  }

  public WebElement getById(final String id) {
    return driver.findElement(By.id(UIObject.DEBUG_ID_PREFIX + id));
  }

  @Override
  public String getCurrentUrl() {
    return driver.getCurrentUrl();
  }

  @Override
  public String getPageSource() {
    return driver.getPageSource();
  }

  @Override
  public String getTitle() {
    return driver.getTitle();
  }

  @Override
  public String getWindowHandle() {
    return driver.getWindowHandle();
  }

  @Override
  public Set<String> getWindowHandles() {
    return driver.getWindowHandles();
  }

  public void gotoToken(final StateToken token) {
    get(baseUrl + token);
  }

  public void gotoToken(final String token) {
    get(baseUrl + token);
  }

  public void home() {
    assert baseUrl != null;
    driver.get(baseUrl);
  }

  public boolean isElementPresent(final String id) {
    // http://stackoverflow.com/questions/5922930/iselementpresent-in-selenium-2-0
    return selenium.isElementPresent(id);
  }

  public boolean isTextPresent(final String text) {
    return selenium.isTextPresent(text);
  }

  @Override
  public Options manage() {
    return driver.manage();
  }

  public void moveMouseAt(final Point point) {
    selenium.mouseMoveAt(String.valueOf(point.getX()), String.valueOf(point.getY()));
  }

  @Override
  public Navigation navigate() {
    return driver.navigate();
  }

  public void open(final String url) {
    get(url);
  }

  @Override
  public void quit() {
    driver.quit();
  }

  @Override
  public TargetLocator switchTo() {
    return driver.switchTo();
  }

}