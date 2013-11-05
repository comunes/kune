/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;

public abstract class PageObject {
  // private static final Log LOG =
  // LogFactory.getLog(PageObject.class.getName());
  private static final long[] POLL_INTERVALS = { 10, 20, 30, 40, 50, 50, 50, 50, 100 };

  @Inject
  private WebDriver webdriver;

  protected void clearField(final WebElement elem) {
    elem.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
  }

  public WebElement findElement(final By by) {
    return webdriver.findElement(by);
  }

  public List<WebElement> findElements(final By by) {
    return webdriver.findElements(by);
  }

  public WebElement getById(final String id) {
    return webdriver.findElement(By.id(UIObject.DEBUG_ID_PREFIX + id));
  }

  public String getCurrentUrl() {
    return webdriver.getCurrentUrl();
  }

  public String getTitle() {
    return webdriver.getTitle();
  }

  protected WebDriver getWebDriver() {
    return webdriver;
  }

  public String getWindowHandle() {
    return webdriver.getWindowHandle();
  }

  public Set<String> getWindowHandles() {
    return webdriver.getWindowHandles();
  }

  public void hightlight(final WebElement element) {
    SeleniumUtils.hightlight(element, webdriver);
    // final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    // final String script = "window.jQuery('#" + element.getAttribute("id") +
    // "').addClass('k-outline');"
    // + "setTimeout('window.jQuery(\"#" + element.getAttribute("id")
    // + "\").removeClass(\"k-outline\")', 700);";
    // // LOG.info("High: " + script);
    // js.executeScript(script);
  }

  public boolean isElementPresent(final String id) {
    final Wait<WebDriver> wait = new WebDriverWait(webdriver, 5);
    final WebElement element = wait.until(visibilityOfElementLocated(By.id(id)));
    return element != null;
  }

  public boolean isPresent(final WebElement element) {
    // FIXME: find a better way to do this
    try {
      getWebDriver().findElement(By.id(element.getAttribute("id")));
      return true;
    } catch (final NoSuchElementException e) {
      return false;
    }
  }

  public boolean isTextPresent(final String text) {
    return webdriver.findElement(By.tagName("body")).getText().contains(text);
  }

  public Options manage() {
    return webdriver.manage();
  }

  public Navigation navigate() {
    return webdriver.navigate();
  }

  public void quit() {
    webdriver.quit();
  }

  public void showTooltip(final WebElement element) {
    SeleniumUtils.showTooltip(webdriver, element);
  }

  public void sleep(final int milliseconds) {
    SeleniumUtils.sleep(milliseconds);
  }

  public TargetLocator switchTo() {
    return webdriver.switchTo();
  }

  public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
    return new ExpectedCondition<WebElement>() {
      @Override
      public WebElement apply(final WebDriver driver) {
        final WebElement toReturn = driver.findElement(locator);
        if (toReturn.isDisplayed()) {
          return toReturn;
        }
        return null;
      }
    };
  }

  /**
   * Thanks to:
   * http://groups.google.com/group/webdriver/browse_frm/thread/6e705242
   * cc6d75ed/f5f8dca438397254?lnk=gst#f5f8dca438397254
   * 
   * @param waitForWhat
   * @param runnable
   */
  protected void waitFor(final String waitForWhat, final Runnable runnable) {
    int i = 0;
    boolean success = false;
    final long timeout = System.currentTimeMillis() + 9000;
    while (i < POLL_INTERVALS.length && !success) {
      try {
        runnable.run();
        success = true;
      } catch (final Throwable e) {
        if (++i == POLL_INTERVALS.length) {
          if (System.currentTimeMillis() > timeout) {
            throw new RuntimeException("Timeout while waiting for " + waitForWhat, e); // NOPMD
                                                                                       // by
                                                                                       // vjrj
                                                                                       // on
                                                                                       // 16/05/11
                                                                                       // 13:05
          } else {
            --i;
          }
        }
        try {
          Thread.sleep(POLL_INTERVALS[i]);
        } catch (final InterruptedException e2) {
          Thread.currentThread().interrupt();
          throw new RuntimeException("Got interrupted while waiting for " + waitForWhat, e2); // NOPMD
                                                                                              // by
                                                                                              // vjrj
                                                                                              // on
                                                                                              // 16/05/11
                                                                                              // 13:08
        }
      }
    }
  }

  public void waitFor(final WebElement element) {
    final String id = element.getAttribute("id");
    // LOG.info("WAIT FOR: " + id);
    waitFor(id, new Runnable() {
      @Override
      public void run() {
        Assert.assertTrue(element.isDisplayed());
      }
    });
  }

  protected void waitFor(final WebElement element, final String text) {
    // LOG.info("WAIT FOR: " + text);
    waitFor(text, new Runnable() {
      @Override
      public void run() {
        final String elText = element.getText();
        // LOG.info("Element text: " + elText);
        Assert.assertTrue(elText.contains(text));
      }
    });
  }

  protected void waitForId(final String id) {
    // LOG.info("WAIT FOR: " + id);
    waitFor(id, new Runnable() {
      @Override
      public void run() {
        Assert.assertTrue(getWebDriver().findElement(new ByIdOrName(id)).isDisplayed());
      }
    });
  }

  protected void waitForValue(final WebElement element, final String text) {
    // LOG.info("WAIT FOR: " + text);
    waitFor(text, new Runnable() {
      @Override
      public void run() {
        final String elValue = element.getText();
        // LOG.info("Element value: " + elValue);
        Assert.assertTrue(elValue.contains(text));
      }
    });
  }
}
