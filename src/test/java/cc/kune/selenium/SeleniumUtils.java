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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import cc.kune.common.shared.utils.TextUtils;

public class SeleniumUtils {

  private static boolean fastSpeed = true;
  private static final Log LOG = LogFactory.getLog(SeleniumUtils.class);

  public static void doScreenshot(final WebDriver webdriver, final String filename) {
    final File scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(scrFile, new File(SeleniumConstants.SCREENSHOTS_DIR + "kune-" + filename
          + ".png"));
    } catch (final IOException e) {
      LOG.info("Cannot take the screen shot", e);
    }
  }

  public static void doubleClick(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.moveMouseTo(webdriver, element);
    sleep(300);
    SeleniumUtils.hightlight(element, webdriver);
    final Actions builder = new Actions(webdriver);
    final Action doubleClick = builder.doubleClick(element).build();
    doubleClick.perform();
  }

  public static void fastSpeed(final boolean fastSpeed) {
    SeleniumUtils.fastSpeed = fastSpeed;
  }

  public static void hideCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("khideCursor();");
  }

  public static void hightlight(final WebElement element, final WebDriver webdriver) {
    SeleniumUtils.showCursor(webdriver, element);
    sleep(300);
    if (TextUtils.notEmpty(element.getAttribute("id"))) {
      final String script = "window.jQuery('#" + element.getAttribute("id")
          + "').addClass('k-outline');" + "setTimeout('window.jQuery(\"#" + element.getAttribute("id")
          + "\").removeClass(\"k-outline\")', 1200);";
      // Antes 700
      // LOG.info("High: " + script);
      jsExec(webdriver).executeScript(script);
    }
  }

  public static void initCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("ksetCursor(100,100);");
    showCursor(webdriver);
  }

  private static JavascriptExecutor jsExec(final WebDriver webdriver) {
    return (JavascriptExecutor) webdriver;
  }

  public static void moveMouseTo(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.showCursor(webdriver, element);
    final Actions builder = new Actions(webdriver);
    final Action action = builder.moveToElement(element).build();
    action.perform();
  }

  public static void moveMouseTo(final WebDriver webdriver, final WebElement element, final int xOffset,
      final int yOffset) {
    showCursor(webdriver, element, xOffset, yOffset);
    final Actions actions = new Actions(webdriver);
    actions.moveToElement(element, xOffset, yOffset);
    final Action action = actions.build();
    action.perform();
  }

  public static void moveMouseToAndClick(final WebDriver webdriver, final WebElement element,
      final int xOffset, final int yOffset) {
    // showCursor(webdriver, element, xOffset, yOffset);
    final Actions actions = new Actions(webdriver);
    actions.moveToElement(element, xOffset, yOffset);
    actions.click();
    final Action action = actions.build();
    action.perform();
  }

  public static void showCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("kshowCursor();");
  }

  public static void showCursor(final WebDriver webdriver, final int x, final int y) {
    // LOG.info("Mover cursor to x: " + x + ", y: " + y);
    jsExec(webdriver).executeScript("kmove(" + (x + 15) + "," + (y + 3) + ");");
  }

  public static void showCursor(final WebDriver webdriver, final WebElement element) {
    final Point location = element.getLocation();
    showCursor(webdriver, location.getX(), location.getY());
  }

  public static void showCursor(final WebDriver webdriver, final WebElement element, final int xOffset,
      final int yOffset) {
    final Point location = element.getLocation();
    SeleniumUtils.showCursor(webdriver, location.getX() + xOffset, location.getY() + yOffset);
  }

  public static void showMsg(final WebDriver webdriver, final String header, final String msg) {
    final String opts = TextUtils.notEmpty(header) ? "header: '" + header + "'," : "";
    // sticky: true,
    final String script = "window.jQuery.jGrowl(\"" + msg + "\", { " + opts + " theme: 'k-jgrowl' } )";
    jsExec(webdriver).executeScript(script);
  }

  public static void showTooltip(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.moveMouseTo(webdriver, element);
    SeleniumUtils.hightlight(element, webdriver);
    sleep(2000);
  }

  public static void sleep(final int milliseconds) {
    try {
      Thread.sleep(fastSpeed ? milliseconds / 2 : milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }
}
