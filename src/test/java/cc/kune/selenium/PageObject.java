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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.testng.Assert;

import com.google.inject.Inject;

public abstract class PageObject {
  protected static final String GWTDEV = "gwt-debug-";
  private static final Log LOG = LogFactory.getLog(PageObject.class.getName());
  private static final long[] POLL_INTERVALS = { 10, 20, 30, 40, 50, 50, 50, 50, 100 };

  @Inject
  private WebDriver webdriver;

  protected RenderedWebElement findElement(final By by) {
    return (RenderedWebElement) getWebDriver().findElement(by);
  }

  private WebDriver getWebDriver() {
    return webdriver;
  }

  public boolean isPresent(final RenderedWebElement element) {
    // FIXME: find a better way to do this
    try {
      getWebDriver().findElement(By.id(element.getAttribute("id")));
      return true;
    } catch (final NoSuchElementException e) {
      return false;
    }
  }

  public void waitFor(final RenderedWebElement element) {
    final String id = element.getAttribute("id");
    LOG.info("WAIT FOR: " + id);
    waitFor(id, new Runnable() {
      @Override
      public void run() {
        Assert.assertTrue(element.isDisplayed());
      }
    });
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

  protected void waitFor(final WebElement element, final String text) {
    LOG.info("WAIT FOR: " + text);
    waitFor(text, new Runnable() {
      @Override
      public void run() {
        final String elText = element.getText();
        LOG.info("Element text: " + elText);
        Assert.assertTrue(elText.contains(text));
      }
    });
  }

  protected void waitForId(final String id) {
    LOG.info("WAIT FOR: " + id);
    waitFor(id, new Runnable() {
      @Override
      public void run() {
        Assert.assertTrue(((RenderedWebElement) getWebDriver().findElement(new ByIdOrName(id))).isDisplayed());
      }
    });
  }

  protected void waitForValue(final WebElement element, final String text) {
    LOG.info("WAIT FOR: " + text);
    waitFor(text, new Runnable() {
      @Override
      public void run() {
        final String elValue = element.getValue();
        LOG.info("Element value: " + elValue);
        Assert.assertTrue(elValue.contains(text));
      }
    });
  }
}
