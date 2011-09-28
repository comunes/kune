package cc.kune.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import cc.kune.common.client.utils.TextUtils;

public class SeleniumUtils {

  public static void hightlight(final WebElement element, final WebDriver webdriver) {
    final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    final String script = "window.jQuery('#" + element.getAttribute("id") + "').addClass('k-outline');"
        + "setTimeout('window.jQuery(\"#" + element.getAttribute("id")
        + "\").removeClass(\"k-outline\")', 1200);";
    // Antes 700
    // LOG.info("High: " + script);
    js.executeScript(script);
  }

  public static void moveMouseTo(final WebDriver webdriver, final WebElement element) {
    final Actions actions = new Actions(webdriver);
    actions.moveToElement(element);
  }

  public static void showMsg(final WebDriver webdriver, final String header, final String msg) {
    final JavascriptExecutor js = (JavascriptExecutor) webdriver;
    final String opts = TextUtils.notEmpty(header) ? "header: '" + header + "'," : "";
    // sticky: true,
    final String script = "window.jQuery.jGrowl(\"" + msg + "\", { " + opts + " theme: 'k-jgrowl' } )";
    js.executeScript(script);
  }

  public static void showTooltip(final WebDriver webdriver, final WebElement element) {
    SeleniumUtils.moveMouseTo(webdriver, element);
    SeleniumUtils.hightlight(element, webdriver);
    sleep(2000);
  }

  public static void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }

}
