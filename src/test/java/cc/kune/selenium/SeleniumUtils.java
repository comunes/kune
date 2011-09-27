package cc.kune.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SeleniumUtils {

  public static void hightlight(final WebElement element, final WebDriver webdriver) {
    final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    final String script = "window.jQuery('#" + element.getAttribute("id") + "').addClass('k-outline');"
        + "setTimeout('window.jQuery(\"#" + element.getAttribute("id")
        + "\").removeClass(\"k-outline\")', 700);";
    // LOG.info("High: " + script);
    js.executeScript(script);
  }

  public static void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }

}
