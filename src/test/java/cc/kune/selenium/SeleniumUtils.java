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

// TODO: Auto-generated Javadoc
/**
 * The Class SeleniumUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SeleniumUtils {

  /** The fast speed. */
  private static boolean fastSpeed = true;

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(SeleniumUtils.class);

  /**
   * Do screenshot.
   * 
   * @param webdriver
   *          the webdriver
   * @param filename
   *          the filename
   */
  public static void doScreenshot(final WebDriver webdriver, final String filename) {
    final File scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(scrFile, new File(SeleniumConstants.SCREENSHOTS_DIR + "kune-" + filename
          + "-sele.png"));
    } catch (final IOException e) {
      LOG.info("Cannot take the screen shot", e);
    }
  }

  /**
   * Double click.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   */
  public static void doubleClick(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.moveMouseTo(webdriver, element);
    sleep(300);
    SeleniumUtils.hightlight(element, webdriver);
    final Actions builder = new Actions(webdriver);
    final Action doubleClick = builder.doubleClick(element).build();
    doubleClick.perform();
  }

  /**
   * Fast speed.
   * 
   * @param fastSpeed
   *          the fast speed
   */
  public static void fastSpeed(final boolean fastSpeed) {
    SeleniumUtils.fastSpeed = fastSpeed;
  }

  /**
   * Hide cursor.
   * 
   * @param webdriver
   *          the webdriver
   */
  public static void hideCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("khideCursor();");
  }

  /**
   * Hightlight.
   * 
   * @param element
   *          the element
   * @param webdriver
   *          the webdriver
   */
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

  /**
   * Inits the cursor.
   * 
   * @param webdriver
   *          the webdriver
   */
  public static void initCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("ksetCursor(100,100);");
    showCursor(webdriver);
  }

  /**
   * Js exec.
   * 
   * @param webdriver
   *          the webdriver
   * @return the javascript executor
   */
  public static JavascriptExecutor jsExec(final WebDriver webdriver) {
    return (JavascriptExecutor) webdriver;
  }

  /**
   * Move mouse to.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   */
  public static void moveMouseTo(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.showCursor(webdriver, element);
    final Actions builder = new Actions(webdriver);
    final Action action = builder.moveToElement(element).build();
    action.perform();
  }

  /**
   * Move mouse to.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   * @param xOffset
   *          the x offset
   * @param yOffset
   *          the y offset
   */
  public static void moveMouseTo(final WebDriver webdriver, final WebElement element, final int xOffset,
      final int yOffset) {
    showCursor(webdriver, element, xOffset, yOffset);
    final Actions actions = new Actions(webdriver);
    actions.moveToElement(element, xOffset, yOffset);
    final Action action = actions.build();
    action.perform();
  }

  /**
   * Move mouse to and click.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   * @param xOffset
   *          the x offset
   * @param yOffset
   *          the y offset
   */
  public static void moveMouseToAndClick(final WebDriver webdriver, final WebElement element,
      final int xOffset, final int yOffset) {
    // showCursor(webdriver, element, xOffset, yOffset);
    final Actions actions = new Actions(webdriver);
    actions.moveToElement(element, xOffset, yOffset);
    actions.click();
    final Action action = actions.build();
    action.perform();
  }

  /**
   * Show cursor.
   * 
   * @param webdriver
   *          the webdriver
   */
  public static void showCursor(final WebDriver webdriver) {
    jsExec(webdriver).executeScript("kshowCursor();");
  }

  /**
   * Show cursor.
   * 
   * @param webdriver
   *          the webdriver
   * @param x
   *          the x
   * @param y
   *          the y
   */
  public static void showCursor(final WebDriver webdriver, final int x, final int y) {
    // LOG.info("Mover cursor to x: " + x + ", y: " + y);
    jsExec(webdriver).executeScript("kmove(" + (x + 15) + "," + (y + 3) + ");");
  }

  /**
   * Show cursor.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   */
  public static void showCursor(final WebDriver webdriver, final WebElement element) {
    final Point location = element.getLocation();
    showCursor(webdriver, location.getX(), location.getY());
  }

  /**
   * Show cursor.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   * @param xOffset
   *          the x offset
   * @param yOffset
   *          the y offset
   */
  public static void showCursor(final WebDriver webdriver, final WebElement element, final int xOffset,
      final int yOffset) {
    final Point location = element.getLocation();
    SeleniumUtils.showCursor(webdriver, location.getX() + xOffset, location.getY() + yOffset);
  }

  /**
   * Show msg.
   * 
   * @param webdriver
   *          the webdriver
   * @param header
   *          the header
   * @param msg
   *          the msg
   */
  public static void showMsg(final WebDriver webdriver, final String header, final String msg) {
    final String opts = TextUtils.notEmpty(header) ? "header: '" + header + "'," : "";
    // sticky: true,
    final String script = "window.jQuery.jGrowl(\"" + msg + "\", { " + opts + " theme: 'k-jgrowl' } )";
    jsExec(webdriver).executeScript(script);
  }

  /**
   * Show tooltip.
   * 
   * @param webdriver
   *          the webdriver
   * @param element
   *          the element
   */
  public static void showTooltip(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.moveMouseTo(webdriver, element);
    SeleniumUtils.hightlight(element, webdriver);
    sleep(2000);
  }

  /**
   * Sleep.
   * 
   * @param milliseconds
   *          the milliseconds
   */
  public static void sleep(final int milliseconds) {
    try {
      Thread.sleep(fastSpeed ? milliseconds / 2 : milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }
}
