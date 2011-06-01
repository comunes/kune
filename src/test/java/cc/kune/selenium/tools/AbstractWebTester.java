package cc.kune.selenium.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractWebTester {
  private final String baseUrl;
  private final WebDriver driver;

  // private final Selenium selenium;

  public AbstractWebTester(final WebDriver driver, final String baseUrl) {
    this.driver = driver;
    this.baseUrl = baseUrl;

    // selenium = new WebDriverBackedSelenium(driver, baseUrl);
    // selenium.setTimeout("5000");
  }

  public void close() {
    driver.close();
  }

  public WebElement getById(final String id) {
    return driver.findElement(By.id(UIObject.DEBUG_ID_PREFIX + id));
  }

  public void home() {
    assert baseUrl != null;
    // selenium.open(baseUrl);
    driver.get(baseUrl);
  }
  //
  // public boolean isElementPresent(final String id) {
  // return true;
  // // return selenium.isElementPresent(id);
  // }
  //
  // public boolean isTextPresent(final String text) {
  // return true;
  // // return selenium.isTextPresent(text);
  // }
  //
  // public void moveMouseAt(final Point point) {
  // // seenium.mouseMoveAt(String.valueOf(point.getX()),
  // // String.valueOf(point.getY()));
  // }

}