package cc.kune.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Assert;

public class CustomWebDriverEventListener implements WebDriverEventListener {

  @Override
  public void afterChangeValueOf(final WebElement element, final WebDriver driver) {
    sleep(200);
  }

  @Override
  public void afterClickOn(final WebElement element, final WebDriver driver) {
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
    slow();
  }

  @Override
  public void afterScript(final String script, final WebDriver driver) {
  }

  @Override
  public void beforeChangeValueOf(final WebElement element, final WebDriver driver) {
  }

  @Override
  public void beforeClickOn(final WebElement element, final WebDriver driver) {
    slow();
  }

  @Override
  public void beforeFindBy(final By by, final WebElement element, final WebDriver driver) {
  }

  @Override
  public void beforeNavigateBack(final WebDriver driver) {
    ;
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
  }

  public void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }

  private void slow() {
    sleep(500);
  }
}