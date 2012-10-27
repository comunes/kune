/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class CustomWebDriverEventListener implements WebDriverEventListener {

  @Override
  public void afterChangeValueOf(final WebElement element, final WebDriver driver) {
    // SeleniumUtils.showCursor(driver);
    sleep(400);
  }

  @Override
  public void afterClickOn(final WebElement element, final WebDriver driver) {
    // SeleniumUtils.showCursor(driver);
    slow();
  }

  @Override
  public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
  }

  @Override
  public void afterNavigateBack(final WebDriver driver) {
    slow();
  }

  @Override
  public void afterNavigateForward(final WebDriver driver) {
    slow();
  }

  @Override
  public void afterNavigateTo(final String url, final WebDriver driver) {
    sleep(500);
  }

  @Override
  public void afterScript(final String script, final WebDriver driver) {
  }

  @Override
  public void beforeChangeValueOf(final WebElement element, final WebDriver driver) {
    SeleniumUtils.hightlight(element, driver);
    sleep(150);
    // SeleniumUtils.hideCursor(driver);
  }

  @Override
  public void beforeClickOn(final WebElement element, final WebDriver driver) {
    SeleniumUtils.hightlight(element, driver);
    slow();
    // SeleniumUtils.hideCursor(driver);
  }

  @Override
  public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
  }

  @Override
  public void beforeNavigateBack(final WebDriver driver) {
  }

  @Override
  public void beforeNavigateForward(final WebDriver driver) {
  }

  @Override
  public void beforeNavigateTo(final String url, final WebDriver driver) {
  }

  @Override
  public void beforeScript(final String script, final WebDriver driver) {
  }

  @Override
  public void onException(final Throwable throwable, final WebDriver driver) {
    if (driver instanceof ChromeDriver) {
      // ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
    }
  }

  private void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

  private void slow() {
    sleep(700);
  }
}
