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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving customWebDriverEvent events. The class
 * that is interested in processing a customWebDriverEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addCustomWebDriverEventListener<code> method. When
 * the customWebDriverEvent event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see CustomWebDriverEventEvent
 */
public class CustomWebDriverEventListener implements WebDriverEventListener {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterChangeValueOf
   * (org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterChangeValueOf(final WebElement element, final WebDriver driver) {
    // SeleniumUtils.showCursor(driver);
    sleep(400);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterClickOn(
   * org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterClickOn(final WebElement element, final WebDriver driver) {
    // SeleniumUtils.showCursor(driver);
    slow();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterFindBy(org
   * .openqa.selenium.By, org.openqa.selenium.WebElement,
   * org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterFindBy(final By by, final WebElement element, final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateBack
   * (org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterNavigateBack(final WebDriver driver) {
    slow();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateForward
   * (org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterNavigateForward(final WebDriver driver) {
    slow();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateTo
   * (java.lang.String, org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterNavigateTo(final String url, final WebDriver driver) {
    sleep(500);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#afterScript(java
   * .lang.String, org.openqa.selenium.WebDriver)
   */
  @Override
  public void afterScript(final String script, final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeChangeValueOf
   * (org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeChangeValueOf(final WebElement element, final WebDriver driver) {
    SeleniumUtils.hightlight(element, driver);
    sleep(150);
    // SeleniumUtils.hideCursor(driver);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeClickOn
   * (org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeClickOn(final WebElement element, final WebDriver driver) {
    SeleniumUtils.hightlight(element, driver);
    slow();
    // SeleniumUtils.hideCursor(driver);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeFindBy(
   * org.openqa.selenium.By, org.openqa.selenium.WebElement,
   * org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateBack
   * (org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeNavigateBack(final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateForward
   * (org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeNavigateForward(final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateTo
   * (java.lang.String, org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeNavigateTo(final String url, final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#beforeScript(
   * java.lang.String, org.openqa.selenium.WebDriver)
   */
  @Override
  public void beforeScript(final String script, final WebDriver driver) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.openqa.selenium.support.events.WebDriverEventListener#onException(java
   * .lang.Throwable, org.openqa.selenium.WebDriver)
   */
  @Override
  public void onException(final Throwable throwable, final WebDriver driver) {
    if (driver instanceof ChromeDriver) {
      // ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
    }
  }

  /**
   * Sleep.
   * 
   * @param milliseconds
   *          the milliseconds
   */
  private void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

  /**
   * Slow.
   */
  private void slow() {
    sleep(700);
  }
}
